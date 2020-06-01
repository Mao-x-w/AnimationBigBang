package com.weknowall.app_common.sharedPrefrence;

/**
 * User: laomao
 * Date: 2017-06-14
 * Time: 17-50
 */

public class GeneralSharePreference extends SharePreferencePlus {

    private static final String NAME = "general";

    public static final String KEY_SAVA_USER ="sava_user";

    public GeneralSharePreference() {
        super(NAME);
    }

    private static GeneralSharePreference sPreference;

    public static GeneralSharePreference getInstance() {
        return sPreference == null ? new GeneralSharePreference() : sPreference;
    }

    public void saveValue(String key, String value) {
        editStringValue(key, value);
    }

    public String getValue(String key) {
        return getStringValue(key, "");
    }

    public void saveIntValue(String key, int value){
        editIntValue(key,value);
    }

    public int getIntValue(String key){
        return getIntValue(key,0);
    }

    public void clearValue(String key) {
        editStringValue(key, "");
    }

}
