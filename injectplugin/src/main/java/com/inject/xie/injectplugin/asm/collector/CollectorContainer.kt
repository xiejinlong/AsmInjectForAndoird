package com.inject.xie.injectplugin.asm.collector

import java.util.concurrent.ConcurrentHashMap

object CollectorContainer {
    var injectMap =
        ConcurrentHashMap<String, InjectMethodContainer>()

    fun put(sourceAbsPath: String?, method: InjectMethod) {
        sourceAbsPath?:return
        synchronized(injectMap) {
            var methodContainer = injectMap[sourceAbsPath]
            if (methodContainer == null) {
                methodContainer = InjectMethodContainer()
                methodContainer.addInjectMethod(method)
                injectMap[sourceAbsPath] = methodContainer
            } else {
                methodContainer.addInjectMethod(method)
            }
        }
    }

    fun getMethodFromSource(source: String?): InjectMethodContainer? {
        synchronized(injectMap) {
            return injectMap[source]
        }
    }
}