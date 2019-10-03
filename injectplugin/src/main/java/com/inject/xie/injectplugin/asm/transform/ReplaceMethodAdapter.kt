package com.inject.xie.injectplugin.asm.transform

import com.inject.xie.annotation.Replace
import com.inject.xie.injectplugin.asm.collector.CollectorContainer
import com.inject.xie.injectplugin.uitls.LogUtil
import com.inject.xie.injectplugin.uitls.TypeUtil
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ASM5
import org.objectweb.asm.Opcodes.INVOKESTATIC

class ReplaceMethodAdapter(var methodData: MethodData, mv: MethodVisitor)
    :MethodVisitor(ASM5, mv) {

    fun targetHandle(): String {
        return TypeUtil.getDesc(Replace::class.java)
    }

//    override fun visitCode() {
//        val sourceMethod = CollectorContainer.getMethodFromSource(methodData.generateAbsName())
//        if ((sourceMethod?.injectMethods?.size?: 0)  == 0) {
//            super.visitCode()
//            return
//        }
//        sourceMethod?.injectMethods?.forEach {
//            if (targetHandle() == it.annotationDesc) {
//                try {
//                    visitMethodInsn(INVOKESTATIC, it.className,
//                        it.name, "()V", false)
//                } catch (e: Exception) {
//                    super.visitCode()
//                    LogUtil.debug("invoke try catch method exception, ${e.message}")
//                }
//            } else {
//                super.visitCode()
//            }
//        }
//    }

    override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, desc: String?, itf: Boolean) {
        val sourceMethod = CollectorContainer.getMethodFromSource(methodData.generateAbsName())
        if ((sourceMethod?.injectMethods?.size?: 0)  == 0) {
            mv.visitMethodInsn(opcode, owner, name, desc, itf)
            return
        }
        sourceMethod?.injectMethods?.forEach {
            if (targetHandle() == it.annotationDesc) {
                try {
                    mv.visitMethodInsn(INVOKESTATIC, it.className,
                        it.name, "()V", false)
                } catch (e: Exception) {
                    mv.visitMethodInsn(opcode, owner, name, desc, itf)
                    LogUtil.debug("invoke try catch method exception, ${e.message}")
                }
            } else {
                mv.visitMethodInsn(opcode, owner, name, desc, itf)
            }
        }
    }
}