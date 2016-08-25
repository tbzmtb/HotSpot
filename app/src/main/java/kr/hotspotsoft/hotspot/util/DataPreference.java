package kr.hotspotsoft.hotspot.util;

import android.content.SharedPreferences;

public class DataPreference {

    public static SharedPreferences PREF = null;
    public final static String TAG = "DataPreference";

    public static void setLoginId(String value) {
        if (PREF != null) {
            SharedPreferences.Editor editor = PREF.edit();
            editor.putString(Config.PREF_LOGIN_KEY, value);
            editor.commit();
        } else {
            Logger.d(TAG, "PREF == " + PREF);
        }
    }

    public static void setLoginNickName(String value) {
        if (PREF != null) {
            SharedPreferences.Editor editor = PREF.edit();
            editor.putString(Config.PREF_LOGIN_NICK_NAME_KEY, value);
            editor.commit();
        } else {
            Logger.d(TAG, "PREF == " + PREF);
        }
    }

    public static void setTumbNailImage(String value) {
        if (PREF != null) {
            SharedPreferences.Editor editor = PREF.edit();
            editor.putString(Config.PREF_THUMBNAIL_IMAGE_KEY, value);
            editor.commit();
        } else {
            Logger.d(TAG, "PREF == " + PREF);
        }
    }

    public static String getLoginId() {
        return PREF != null ? PREF.getString(Config.PREF_LOGIN_KEY, null) : null;
    }

    public static String getLoginNickName() {
        return PREF != null ? PREF.getString(Config.PREF_LOGIN_NICK_NAME_KEY, null) : null;
    }

    public static String getThumbNailImage() {
        return PREF != null ? PREF.getString(Config.PREF_THUMBNAIL_IMAGE_KEY, null) : null;
    }

}
