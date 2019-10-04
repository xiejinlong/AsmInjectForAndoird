package com.inject.xie.injectplugin.asm.collector

import com.inject.xie.annotation.*
import com.inject.xie.injectplugin.uitls.TypeUtil

class CollectorAnnotationUtil  {
    companion object {
        fun shouldCollectorSource(desc: String?): Boolean {
            return when(desc) {
                TypeUtil.getDesc(Around::class.java),
                    TypeUtil.getDesc(TryCatch::class.java),
                    TypeUtil.getDesc(Timer::class.java),
                    TypeUtil.getDesc(Replace::class.java) -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}