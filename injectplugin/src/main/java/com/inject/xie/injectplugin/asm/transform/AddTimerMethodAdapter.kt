package com.inject.xie.injectplugin.asm.transform

import com.inject.xie.annotation.Timer
import com.inject.xie.injectplugin.asm.collector.InjectMethod
import com.inject.xie.injectplugin.uitls.TypeUtil
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class AddTimerMethodAdapter(method: MethodData, mv: MethodVisitor) :
    BaseMethodAdapter(method, mv) {


    override fun targetHandle(): String {
        return TypeUtil.getDesc(Timer::class.java)
    }

    override fun innerMethodExit(opcode: Int, method: InjectMethod) {
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        mv.visitVarInsn(LLOAD, 1)
        mv.visitInsn(LSUB)
        mv.visitVarInsn(LSTORE, 3)
        mv.visitMethodInsn(Opcodes.ASM5, method.className, method.name, method.desc)
    }

    override fun innerMethodEnter(method: InjectMethod) {
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitVarInsn(LSTORE, 1)
    }


}