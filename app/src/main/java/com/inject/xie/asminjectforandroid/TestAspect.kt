package com.inject.xie.asminjectforandroid

import com.inject.xie.annotation.Around
import com.inject.xie.annotation.Catch
import com.inject.xie.annotation.Inject

@Inject
class TestAspect {


    @Catch(target = ["3333", "444444"])
    fun test() {

    }
}