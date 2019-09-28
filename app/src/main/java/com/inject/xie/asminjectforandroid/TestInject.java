package com.inject.xie.asminjectforandroid;

import com.inject.xie.annotation.Catch;
import com.inject.xie.annotation.Inject;

@Inject
public class TestInject {


    @Catch(target = {"111", "222222"}, after = true)
    public void test() {

    }
}
