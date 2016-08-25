package kr.hotspotsoft.hotspot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kr.hotspotsoft.hotspot.R;
import kr.hotspotsoft.hotspot.util.DataPreference;

/**
 * Created by sunyungkim on 16. 8. 23..
 */
public class RegistrationSpaceActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mStartSpaceRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_space);
        getSupportActionBar().setTitle(getResources().getString(R.string.registration_space));
        Glide.with(this)
                .load(R.drawable.bad_background)
                .centerCrop()
                .into((ImageView) findViewById(R.id.image_background));
        mStartSpaceRegist = (LinearLayout) findViewById(R.id.start_space_regist);
        mStartSpaceRegist.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_space_regist:
                Intent intent = new Intent(RegistrationSpaceActivity.this, RegistrationSelectSpaceTypeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
