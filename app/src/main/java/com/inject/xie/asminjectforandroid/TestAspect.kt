package com.inject.xie.asminjectforandroid

import com.inject.xie.annotation.TryCatch
import com.inject.xie.annotation.Inject

@Inject
class TestAspect {


    @TryCatch(target = ["3333", "444444"])
    fun test() {

    }
}