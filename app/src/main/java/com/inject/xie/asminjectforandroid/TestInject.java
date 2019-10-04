package com.inject.xie.asminjectforandroid;

import android.util.Log;
import com.inject.xie.annotation.*;

@Inject
public class TestInject {


    @TryCatch(target = {"com/inject/xie/asminjectforandroid/MainActivity.testTryCatch()V", "View.onMeasure()"}, after = true)
    public static void test(Exception throwable) {
        Log.e("XJL", "throwable is + " + throwable.getMessage());

    }

    @Replace(target = {"com/inject/xie/asminjectforandroid/MainActivity.testReplace()V", "View.onMeasure()"})
    public static void testReplace() {
        Log.e("XJL", "run replace succeed ->");
    }


    @Around(target = {"com/inject/xie/asminjectforandroid/MainActivity.testInject()V"}, after = true)
    public static void testInject() {
        Log.e("XJL", "run inject after ->");
    }

    @Around(target = {"com/inject/xie/asminjectforandroid/MainActivity.testInject()V"}, after = false)
    public static void testInject1() {
        Log.e("XJL", "run inject before ->");
    }

    @Timer(target = {"com/inject/xie/asminjectforandroid/MainActivity.testTimer()V", "View.onMeasure()"})
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
