package com.inject.xie.injectplugin.asm.collector

import java.util.concurrent.ConcurrentHashMap

object CollectorContainer {
    var injectMap =
        ConcurrentHashMap<InjectAnnotation, InjectMethodContainer>()

    fun put(annotation: InjectAnnotation, method: InjectMethod) {
        synchronized(injectMap) {
            var methodContainer = injectMap[annotation]
            if (methodContainer == null) {
                methodContainer = InjectMethodContainer()
                methodContainer.addInjectMethod(method)
                injectMap[annotation] = methodContainer
            } else {
                methodContainer.addInjectMethod(method)
            }
        }

    }
}