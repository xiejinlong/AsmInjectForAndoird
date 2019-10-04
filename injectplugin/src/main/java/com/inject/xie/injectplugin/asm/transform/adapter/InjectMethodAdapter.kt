package com.inject.xie.injectplugin.asm.transform.adapter

import com.inject.xie.annotation.Around
import com.inject.xie.injectplugin.asm.collector.InjectMethod
import com.inject.xie.injectplugin.asm.transform.MethodData
import com.inject.xie.injectplugin.uitls.LogUtil
import com.inject.xie.injectplugin.uitls.TypeUtil
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class InjectMethodAdapter(methodData: MethodData, mv: MethodVisitor)
    : BaseMethodAdapter(methodData, mv) {
    override fun targetHandle(): String {
        return TypeUtil.getDesc(Around::class.java)
    }

    override fun innerMethodExit(opcode: Int, method: InjectMethod) {
        if (method.after) {
            try {
                LogUtil.debug("InjectMethodAdapter, innerMethodExit")
                mv.visitMethodInsn(
                    Opcodes.INVOKESTATIC, method.className,
                    method.name, "()V", false)
            } catch (e: Exception) {
                LogUtil.debug("invoke try catch method exception, ${e.message}")
            }
        }
    }

    override fun innerMethodEnter(method: InjectMethod) {
        if (!method.after) {
            try {
                LogUtil.debug("InjectMethodAdapter, innerMethodEnter")
                mv.visitMethodInsn(
                    Opcodes.INVOKESTATIC, method.className,
                    method.name, "()V", false)
            } catch (e: Exception) {
                LogUtil.debug("invoke try catch method exception, ${e.message}")
            }
        }
    }

}