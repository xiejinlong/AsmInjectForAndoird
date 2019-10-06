package com.inject.xie.injectplugin.asm.transform

import com.inject.xie.injectplugin.asm.transform.adapter.AddTimerMethodAdapter
import com.inject.xie.injectplugin.asm.transform.adapter.AddTryCatchMethodAdapter
import com.inject.xie.injectplugin.asm.transform.adapter.InjectMethodAdapter
import com.inject.xie.injectplugin.asm.transform.adapter.ReplaceMethodAdapter
import org.objectweb.asm.MethodVisitor

class MethodVisitorChain {
    companion object {
        fun handleVisitor(methodData: MethodData, mv: MethodVisitor): MethodVisitor {
            val tryCatch =
                AddTryCatchMethodAdapter(methodData, mv)
            val addTimer = AddTimerMethodAdapter(methodData, tryCatch)

            val replace = ReplaceMethodAdapter(methodData, addTimer)
            val inject = InjectMethodAdapter(methodData, replace)

            return inject
        }
    }

}