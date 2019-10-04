package com.inject.xie.asminjectforandroid

import android.app.Activity
import android.os.Bundle
import android.util.Log
import java.util.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testTimer()
        testTryCatch()
        testReplace()
        testInject()
    }

    fun testReplace() {
        Log.e("XJl", "test replace")
    }

    fun testInject() {
        Log.e("XJl", "test inject")
    }

    fun testTryCatch() {
        Log.e("XJl", "test try catch")
        val a = 1 / 0
        val timer: Timer? = null
        timer.toString()
    }

    fun testTimer() {
        Log.e("XJl", "test timer...")
        Thread.sleep(500)


    }
}
