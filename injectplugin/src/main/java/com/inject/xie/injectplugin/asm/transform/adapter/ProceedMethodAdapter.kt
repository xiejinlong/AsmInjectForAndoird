package com.inject.xie.injectplugin.asm.transform.adapter

import com.inject.xie.annotation.Proceed
import com.inject.xie.injectplugin.asm.collector.InjectMethod
import com.inject.xie.injectplugin.asm.transform.MethodData
import com.inject.xie.injectplugin.uitls.TypeUtil
import org.objectweb.asm.MethodVisitor

class ProceedMethodAdapter(method: MethodData, mv: MethodVisitor) :
    BaseMethodAdapter(method, mv) {

    override fun targetHandle(): String {
        return TypeUtil.getDesc(Proceed::class.java)
    }

    override fun innerMethodExit(opcode: Int, method: InjectMethod) {

    }

    override fun innerMethodEnter(method: InjectMethod) {

    }


}