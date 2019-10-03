package com.inject.xie.injectplugin.asm.transform

class MethodData {
    var onwerName: String? = null
    var methodAccess: Int = 0
    var methodName: String? = null
    var methodDes: String? = null

    fun generateAbsName(): String {
        return "$onwerName.$methodName$methodDes"
    }
}