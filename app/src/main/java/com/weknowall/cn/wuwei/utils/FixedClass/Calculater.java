package com.weknowall.cn.wuwei.utils.FixedClass;

import com.weknowall.cn.wuwei.utils.hotFix.Replace;

public class Calculater {

    @Replace(clasName = "com.weknowall.cn.wuwei.utils.calculate.Calculater",methodName = "calculate")
    public int calculate(){
        int a=1;
        int b=10;
        return b/a;
    }
}
