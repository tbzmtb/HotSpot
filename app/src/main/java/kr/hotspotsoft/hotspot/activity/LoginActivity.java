package kr.hotspotsoft.hotspot.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import java.security.MessageDigest;

import kr.hotspotsoft.hotspot.R;
import kr.hotspotsoft.hotspot.util.DataPreference;
import kr.hotspotsoft.hotspot.util.Logger;

/**
 * Created by hp on 2016-01-26.
 */
public class LoginActivity extends AppCompatActivity {
    private final String TAG = getClass().getName();
    private RelativeLayout mKakaoLoginBtn;
    private RelativeLayout mKakaoLogoutBtn;
    private SessionCallback mKakaocallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Logger.d(TAG, "onCreate call");
        // 헤쉬키를 가져온다
        getAppKeyHash();

        mKakaoLoginBtn = (RelativeLayout) findViewById(R.id.com_kakao_login);
        mKakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d(TAG, "onClick call");
                startKakaoLogin();
            }
        });
        mKakaoLogoutBtn = (RelativeLayout) findViewById(R.id.com_kakao_logout);
        mKakaoLogoutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                UserManagement.requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Logger.d(TAG, "logout complete");
                        DataPreference.setTumbNailImage(null);
                        DataPreference.setLoginId(null);
                        DataPreference.setLoginNickName(null);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(DataPreference.getLoginId() == null){
            mKakaoLoginBtn.setVisibility(View.VISIBLE);
            mKakaoLogoutBtn.setVisibility(View.GONE);
        }else{
            mKakaoLoginBtn.setVisibility(View.GONE);
            mKakaoLogoutBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //간편로그인시 호출 ,없으면 간편로그인시 로그인 성공화면으로 넘어가지 않음
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startKakaoLogin() {
        Logger.d(TAG, "startKakaoLogin call");
        mKakaocallback = new SessionCallback();
        com.kakao.auth.Session.getCurrentSession().addCallback(mKakaocallback);
        com.kakao.auth.Session.getCurrentSession().checkAndImplicitOpen();
        com.kakao.auth.Session.getCurrentSession().open(AuthType.KAKAO_TALK_EXCLUDE_NATIVE_LOGIN, LoginActivity.this);
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            Logger.d(TAG, "onSessionOpened");
            KakaorequestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Logger.d(TAG, "onSessionOpenFailed");
            if (exception != null) {
                Logger.d(TAG, exception.getMessage());
            }
        }

    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void KakaorequestMe() {
        Logger.d(TAG, "KakaorequestMe call");
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                int ErrorCode = errorResult.getErrorCode();
                int ClientErrorCode = -777;

                if (ErrorCode == ClientErrorCode) {
                    Toast.makeText(getApplicationContext(), "카카오톡 서버의 네트워크가 불안정합니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Logger.d("TAG", "오류로 카카오로그인 실패 ");
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Logger.d("TAG", "오류로 카카오로그인 실패 ");
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Logger.d(TAG, "Login success" + userProfile.toString());
                Logger.d(TAG, "userProfile.getProfileImagePath() = " + userProfile.getProfileImagePath());
                Logger.d(TAG, "userProfile.getId() = " + String.valueOf(userProfile.getId()));
                Logger.d(TAG, "userProfile.getNickname() = " + userProfile.getNickname());
                DataPreference.setLoginId(String.valueOf(userProfile.getId()));
                DataPreference.setLoginNickName(userProfile.getNickname());
                DataPreference.setTumbNailImage(userProfile.getThumbnailImagePath());
                finish();
            }

            // 자동가입이 아닐경우 동의창
            @Override
            public void onNotSignedUp() {
                Logger.d(TAG, "onNotSignedUp call");

            }
        });
    }

    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Logger.d(TAG, something);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
