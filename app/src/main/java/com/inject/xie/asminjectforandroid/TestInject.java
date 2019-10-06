package com.inject.xie.asminjectforandroid;

import android.util.Log;
import com.inject.xie.annotation.*;
import com.inject.xie.info.SourceProceedPoint;

@Inject
public class TestInject {


    @TryCatch(target = {"com/inject/xie/asminjectforandroid/MainActivity|testTryCatch()|void"}, after = true)
    public static void test(Exception throwable) {
        Log.e("XJL", "throwable is + " + throwable.getMessage());

    }


//    @TryCatch(target = {"com.inject.xie.asminjectforandroid.MainActivity|testAnnotationValue(org.json.JSONObject, java.lang.String)|java.lang.String"}, extend = true)
//    public static void testAnnotationConfig(Exception throwable) {
//        Log.e("XJL", "TestInject testAnnotationConfig ..");
//    }

    @Replace(target = {"com/inject/xie/asminjectforandroid/MainActivity|testReplace()|void"})
    public static void testReplace() {
        Log.e("XJL", "run replace succeed ->");
    }

    @Proceed(target = {"com/inject/xie/asminjectforandroid/MainActivity|testProceed()|void"})
    public static void testProceed(SourceProceedPoint proceedPoint) {
        Log.e("XJL", "run proceed before ->");
        proceedPoint.proceed();
        Log.e("XJL", "run proceed after ->");
    }

    @Around(target = {"com/inject/xie/asminjectforandroid/MainActivity|testInject()|void"}, after = true)
    public static void testInject() {
        Log.e("XJL", "run inject after ->");
    }

    @Around(target = {"com/inject/xie/asminjectforandroid/MainActivity|testInject()|void"}, after = false)
    public static void testInject1() {
        Log.e("XJL", "run inject before ->");
    }

    @Timer(target = {"com/inject/xie/asminjectforandroid/MainActivity|testTimer()|void"}, extend = true)
    public static void handle(long time) {
        Log.e("XJL", "cost time is ->" + time);
    }

    public static void test1() {
            long time = System.currentTimeMillis();
            String b = "";
            long a = 1;
            long costTime = System.currentTimeMillis() - time;


    }

}
