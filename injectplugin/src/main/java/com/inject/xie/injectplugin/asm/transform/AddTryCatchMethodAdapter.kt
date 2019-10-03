package com.inject.xie.injectplugin.asm.transform

import com.inject.xie.annotation.TryCatch
import com.inject.xie.injectplugin.asm.collector.InjectMethod
import com.inject.xie.injectplugin.uitls.LogUtil
import com.inject.xie.injectplugin.uitls.TypeUtil
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor

class AddTryCatchMethodAdapter(methodData: MethodData, mv: MethodVisitor):
    BaseMethodAdapter(methodData, mv) {

    var label1: Label? = null
    var label2: Label? = null
    var label3: Label? = null

    override fun targetHandle(): String {
        return TypeUtil.getDesc(TryCatch::class.java)
    }

    override fun innerMethodExit(opcode: Int, method: InjectMethod) {
        mv.visitLabel(label1)
        label3 = Label()
        mv.visitJumpInsn(GOTO, label3)
        mv.visitLabel(label2)
        mv.visitVarInsn(ASTORE, 1)
        try {
            mv.visitVarInsn(ALOAD, 1)
            mv.visitMethodInsn(INVOKESTATIC, method.className,
                method.name, "(Ljava/lang/Exception;)V", false);
        } catch (e: Exception) {
            LogUtil.debug("invoke try catch method exception, ${e.message}")
        }

        mv.visitLabel(label3)
    }

    override fun innerMethodEnter(method: InjectMethod) {
        val label0 = Label()
        label1 = Label()
        label2 = Label()
        mv.visitTryCatchBlock(label0, label1, label2, "java/lang/Exception")
        mv.visitLabel(label0)
    }

}