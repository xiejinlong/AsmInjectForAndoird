package com.inject.xie.injectplugin.asm.transform.adapter

import com.inject.xie.annotation.Timer
import com.inject.xie.injectplugin.asm.collector.InjectMethod
import com.inject.xie.injectplugin.asm.transform.MethodData
import com.inject.xie.injectplugin.uitls.LogUtil
import com.inject.xie.injectplugin.uitls.TypeUtil
import org.objectweb.asm.MethodVisitor

class AddTimerMethodAdapter(method: MethodData, mv: MethodVisitor) :
    BaseMethodAdapter(method, mv) {

    override fun targetHandle(): String {
        return TypeUtil.getDesc(Timer::class.java)
    }

    override fun innerMethodExit(opcode: Int, method: InjectMethod) {
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        mv.visitVarInsn(LLOAD, 0)
        mv.visitInsn(LSUB)
        try {
            mv.visitMethodInsn(INVOKESTATIC, method.className,
                method.name, "(J)V", false)
        } catch (e: Exception) {
            LogUtil.debug("invoke try catch method exception, ${e.message}")
        }
    }

    override fun innerMethodEnter(method: InjectMethod) {
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitVarInsn(LSTORE, 0)
    }


}