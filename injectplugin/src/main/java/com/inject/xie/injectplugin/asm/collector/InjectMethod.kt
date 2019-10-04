package com.inject.xie.injectplugin.asm.collector

class InjectMethod {
    //是否插入在方法之后，默认在方法前
    var after: Boolean = false
    //父类是否需要同样的插桩
    var extend: Boolean = false
    var exceptionDesc: String? = null
    var annotationDesc: String? = null
    var className: String? = null
    var access: Int? = null
    var name: String? = null
    var desc: String? = null
    var signature: String? = null
    var exceptions: Array<out String>? = null
}