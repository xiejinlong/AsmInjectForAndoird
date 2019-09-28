package com.inject.xie.injectplugin.asm.collector

class CatchAnnotation {

    var target: String? = null
    var after: Boolean = false
    var exceptionDesc: String? = null

    companion object {
        const val NAME = "catch"
        const val TAGET = "target"
        const val AFTER = "after"
        const val EXCEPTION_DESC = "exceptionDesc"
    }

}