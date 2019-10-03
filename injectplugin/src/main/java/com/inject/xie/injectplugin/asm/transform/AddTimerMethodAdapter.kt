package com.inject.xie.injectplugin.asm.transform

import com.inject.xie.annotation.Timer
import com.inject.xie.injectplugin.uitls.TypeUtil
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

class AddTimerMethodAdapter(var owner: String?, access: Int,
                            name: String?, desc: String?, mv: MethodVisitor) :
    AdviceAdapter(Opcodes.ASM4, mv, access, name, desc) {

    private var isTimer = false

    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor {
        if (TypeUtil.getDesc(Timer::class.java) == desc) {
            isTimer = true
        }
        return super.visitAnnotation(desc, visible)
    }

    override fun onMethodExit(opcode: Int) {
        if (isTimer) {
            mv.visitFieldInsn(GETSTATIC, owner, "timer", "J");
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System",
                "currentTimeMillis", "()J")
            mv.visitInsn(LADD)
            mv.visitFieldInsn(PUTSTATIC, owner, "timer", "J");
        }

    }

    override fun onMethodEnter() {
        if (isTimer) {
            mv.visitFieldInsn(GETSTATIC, owner, "timer", "J"); mv.visitMethodInsn(INVOKESTATIC, "java/lang/System",
                "currentTimeMillis", "()J")
            mv.visitInsn(LSUB)
            mv.visitFieldInsn(PUTSTATIC, owner, "timer", "J")
        }
    }
}