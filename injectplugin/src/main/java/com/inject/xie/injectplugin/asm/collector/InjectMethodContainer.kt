package com.inject.xie.injectplugin.asm.collector

import java.util.concurrent.CopyOnWriteArraySet

class InjectMethodContainer {
    var injectMethods: CopyOnWriteArraySet<InjectMethod> = CopyOnWriteArraySet()

    fun addInjectMethod(injectMethod: InjectMethod) {
        injectMethods.add(injectMethod)
    }

    fun removeInjectMethod(injectMethod: InjectMethod) {
        injectMethods.remove(injectMethod)
    }
}