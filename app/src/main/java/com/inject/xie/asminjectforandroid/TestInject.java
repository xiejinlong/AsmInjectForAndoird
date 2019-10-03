package com.inject.xie.asminjectforandroid;

import android.util.Log;
import com.inject.xie.annotation.Timer;
import com.inject.xie.annotation.TryCatch;
import com.inject.xie.annotation.Inject;

@Inject
public class TestInject {


//    @TryCatch(target = {"com/inject/xie/asminjectforandroid/MainActivity.test()V", "View.onMeasure()"}, after = true)
//    public static void test(Exception throwable) {
//        Log.e("XJL", "throwable is + " + throwable.getMessage());
//
//    }

    @Timer(target = {"com/inject/xie/asminjectforandroid/MainActivity.test()V", "View.onMeasure()"})
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
