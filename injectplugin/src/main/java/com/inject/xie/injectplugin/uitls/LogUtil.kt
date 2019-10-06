package com.inject.xie.injectplugin.uitls

import org.slf4j.LoggerFactory

class LogUtil {



    companion object {
        var isDebug = true
        const val TAG = "Inject-Plugin"
        val logger = LoggerFactory.getLogger("Inject-Plugin")

        @JvmStatic
        fun debug(message: String?) {
            if (isDebug) {
                println("$TAG: $message")
                logger.debug(message)
            }

        }

        @JvmStatic
        fun warn(message: String?) {
            if (isDebug) {
                println("$TAG: $message")
                logger.warn(message)
            }
        }

        @JvmStatic
        fun error(message: String?) {
            if (isDebug) {
                println("$TAG: $message")
                logger.error(message)
            }
        }

        @JvmStatic
        fun info(message: String?) {
            if (isDebug) {
                println("$TAG: $message")
                logger.info(message)
            }
        }
    }
}