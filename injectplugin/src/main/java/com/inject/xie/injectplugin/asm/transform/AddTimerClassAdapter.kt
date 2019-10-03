package com.inject.xie.injectplugin.asm.transform

import com.inject.xie.injectplugin.uitls.LogUtil
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*


class AddTimerClassAdapter(cv: ClassVisitor): ClassVisitor(ASM5, cv) {

    private var owner: String? = null
    private var isInterface: Boolean = false
    private var isInjectClass = true
    private var shouldInject = false



    override fun visit(p0: Int, p1: Int, p2: String?, p3: String?, p4: String?, p5: Array<out String>?) {
        this.owner = p2
        this.isInterface = p1 and ACC_INTERFACE != 0
        LogUtil.debug("class name is -> $p2")
        this.shouldInject = "com/inject/xie/asminjectforandroid/MainActivity" == p2 ||
                    p2 == "com/inject/xie/asminjectforandroid/TestAspect"
        super.visit(p0, p1, p2, p3, p4,p5)
    }

    override fun visitMethod(p0: Int, p1: String?, p2: String?, p3: String?,
                             p4: Array<out String>?): MethodVisitor {
       var methodVisitor = cv.visitMethod(p0, p1, p2, p3, p4)
        if (!isInterface && methodVisitor != null && p1 != "<init>" && shouldInject) {
            LogUtil.debug("owner is -> $owner, now AddTimerMethodAdapter2...")
            methodVisitor = AddTimerMethodAdapter2(owner, methodVisitor)
        }
        return methodVisitor
    }


    override fun visitEnd() {
        if (!isInterface && shouldInject) {
            cv.visitField(
                ACC_PUBLIC + ACC_STATIC, "timer",
                "J", null, null
            )?.visitEnd()
        }
        cv.visitEnd()
    }
}