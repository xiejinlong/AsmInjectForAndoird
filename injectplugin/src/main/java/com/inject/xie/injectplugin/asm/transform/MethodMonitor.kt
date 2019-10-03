package com.inject.xie.injectplugin.asm.transform

class MethodMonitor {
    companion object {
        var startTime:Long = 0


        fun methodStart() {
            startTime = System.currentTimeMillis()
        }
    }

}