package com.inject.xie.asminjectforandroid

import android.app.Activity
import android.os.Bundle
import android.util.Log
import java.util.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test()
    }

    fun test() {
        val startTime = System.currentTimeMillis()
        Log.e("XJl", "1111")
        val costTime = System.currentTimeMillis() - startTime
        Log.e("costTime", "$costTime")
        val a = 1 / 0
        val timer: Timer? = null
        timer.toString()

    }
}
