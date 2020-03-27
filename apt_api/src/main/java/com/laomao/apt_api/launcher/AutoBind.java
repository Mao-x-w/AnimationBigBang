package com.laomao.apt_api.launcher;

import com.laomao.apt_api.template.IBindHelper;

/**
 * User: laomao
 * Date: 2019-12-02
 * Time: 17-38
 */
public class AutoBind {
    private static AutoBind instance = null;

    public AutoBind() {
    }

    public static AutoBind getInstance() {
        if(instance == null) {
            synchronized (AutoBind.class) {
                if (instance == null) {
                    instance = new AutoBind();
                }
            }
        }
        return instance;
    }

    public void inject(Object target) {
        String className = target.getClass().getCanonicalName();
        String helperName = className + "Helper";
        try {
            IBindHelper helper = (IBindHelper) (Class.forName(helperName).getConstructor().newInstance());
            helper.inject(target);
        }   catch (Exception e) {
            e.printStackTrace();
        }
    }
}

