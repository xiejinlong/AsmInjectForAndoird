package com.inject.xie.injectplugin.asm.transform.adapter

import com.inject.xie.annotation.Replace
import com.inject.xie.injectplugin.asm.collector.CollectorContainer
import com.inject.xie.injectplugin.asm.transform.MethodData
import com.inject.xie.injectplugin.uitls.LogUtil
import com.inject.xie.injectplugin.uitls.TypeUtil
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ASM5
import org.objectweb.asm.Opcodes.INVOKESTATIC

class ReplaceMethodAdapter(var methodData: MethodData, mv: MethodVisitor)
    :MethodVisitor(ASM5, mv) {

    private fun targetHandle(): String {
        return TypeUtil.getDesc(Replace::class.java)
    }

    override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, desc: String?, itf: Boolean) {
        val sourceMethod = CollectorContainer.getMethodFromSource(methodData.generateAbsName())
        if ((sourceMethod?.injectMethods?.size?: 0)  == 0) {
            mv.visitMethodInsn(opcode, owner, name, desc, itf)
            return
        }
        var hasReplace = false
        sourceMethod?.injectMethods?.forEach {
            if (targetHandle() == it.annotationDesc) {
                try {
                    hasReplace = true
                    mv.visitMethodInsn(INVOKESTATIC, it.className,
                        it.name, "()V", false)
                } catch (e: Exception) {
                    hasReplace = false
                    LogUtil.debug("invoke try catch method exception, ${e.message}")
                }
            }
        }
        if (!hasReplace) {
            mv.visitMethodInsn(opcode, owner, name, desc, itf)
        }
    }
}