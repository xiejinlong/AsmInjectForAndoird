package com.inject.xie.injectplugin.asm.collector

import com.inject.xie.annotation.Around
import com.inject.xie.annotation.Catch
import com.inject.xie.injectplugin.uitls.TypeUtil

class CollectorAnnotationUtil  {
    companion object {
        fun shouldCollectorSource(desc: String?): Boolean {
            return when(desc) {
                TypeUtil.getDesc(Around::class.java),
                    TypeUtil.getDesc(Catch::class.java) ->  {
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}