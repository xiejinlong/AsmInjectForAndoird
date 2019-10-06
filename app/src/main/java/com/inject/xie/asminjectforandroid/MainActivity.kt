package com.inject.xie.asminjectforandroid

import android.os.Bundle
import android.util.Log
import org.json.JSONObject
import java.util.*

class MainActivity : BaseTestActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testTimer()
        testTryCatch()
        testReplace()
        testInject()
        testProceed()

        val json = JSONObject()
        json.put("key", "test json")
        Log.e("XJL", "testAnnotationValue result is -> ${testAnnotationValue(json, "")}" )
    }

    fun testAnnotationValue(json: JSONObject, a: String): String {
        return json.toString()
    }

    fun testProceed() {
        Log.e("XJL", "test proceed...")
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

    override fun testTimer() {
        Log.e("XJl", "test timer...")
        Thread.sleep(500)
    }
}
