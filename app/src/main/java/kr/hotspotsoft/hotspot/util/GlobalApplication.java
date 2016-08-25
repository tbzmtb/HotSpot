package kr.hotspotsoft.hotspot.util;

import android.app.Activity;
import android.app.Application;

import com.kakao.auth.KakaoSDK;

import kr.hotspotsoft.hotspot.adapter.KakaoSDKAdapter;

/**
 * Created by sunyungkim on 16. 8. 18..
 */
public class GlobalApplication extends Application {
    private final static String TAG = "GlobalApplication";
    private static GlobalApplication mInstance;
    private static volatile Activity currentActivity = null;

    public static Activity getCurrentActivity() {
        Logger.d(TAG, "++ currentActivity : " + (currentActivity != null ? currentActivity.getClass().getSimpleName() : ""));
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }

    /**
     * singleton
     *
     * @return singleton
     */
    public static GlobalApplication getGlobalApplicationContext() {
        if (mInstance == null)
            throw new IllegalStateException("this application does not inherit GlobalApplication");
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Logger.d(TAG, "onCreate call");
        KakaoSDK.init(new KakaoSDKAdapter());
    }
}
