package kr.hotspotsoft.hotspot.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.pwittchen.reactivenetwork.library.Connectivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import kr.hotspotsoft.hotspot.R;
import kr.hotspotsoft.hotspot.util.DataPreference;
import kr.hotspotsoft.hotspot.util.Logger;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by tbzm on 16. 5. 4.
 */
public class IntroActivity extends Activity {

    private Handler mHandler;
    private int mDelay = 2 * 1000;
    private static final String TAG = "IntroActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        DataPreference.PREF = PreferenceManager.getDefaultSharedPreferences(this);
        mHandler = new Handler();
        mHandler.postDelayed(mRun, mDelay);
    }

    final Runnable mRun = new Runnable() {
        @Override
        public void run() {
            ReactiveNetwork.observeNetworkConnectivity(IntroActivity.this)
                    .subscribeOn(Schedulers.io())
//                    .filter(Connectivity.hasState(NetworkInfo.State.CONNECTED))
//                    .filter(Connectivity.hasType(ConnectivityManager.TYPE_WIFI))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Connectivity>() {
                        @Override
                        public void call(Connectivity connectivity) {
                            if (connectivity.getState() == NetworkInfo.State.CONNECTED) {
                                Logger.d(TAG, "network CONNECTED");
                                Intent i = new Intent(IntroActivity.this, HotSpotMainCategoryActivity.class);
                                startActivity(i);
                                finish();
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            } else {
                                try {
                                    new MaterialDialog.Builder(IntroActivity.this)
                                            .title(R.string.network_popup_title)
                                            .content(R.string.network_popup_content)
                                            .cancelable(false)
                                            .positiveText(R.string.network_popup_longer_positive)
                                            .negativeText(R.string.network_popup_negative)
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    mHandler.postDelayed(mRun, 200);
                                                }
                                            })
                                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    finish();
                                                }
                                            })
                                            .show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });


        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mHandler.removeCallbacks(mRun);
    }
}
