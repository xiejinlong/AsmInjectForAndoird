package com.inject.xie.injectplugin.uitls

import org.objectweb.asm.Type


class TypeUtil {
    companion object {
        fun getDesc(clazz: Class<*>): String {
            return Type.getDescriptor(clazz)
        }

    }
}