package com.inject.xie.injectplugin.asm.collector

class InjectMethod {
    var annotationDesc: String? = null
    var className: String? = null
    var access: Int? = null
    var name: String? = null
    var desc: String? = null
    var signature: String? = null
    var exceptions: Array<out String>? = null
}