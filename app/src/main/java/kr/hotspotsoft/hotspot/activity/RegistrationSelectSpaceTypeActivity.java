package kr.hotspotsoft.hotspot.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.hotspotsoft.hotspot.R;
import kr.hotspotsoft.hotspot.adapter.SpaceTypeGridViewAdapter;
import kr.hotspotsoft.hotspot.data.SpaceData;
import kr.hotspotsoft.hotspot.data.TypeGridViewData;
import kr.hotspotsoft.hotspot.util.Config;
import kr.hotspotsoft.hotspot.util.Logger;

/**
 * Created by sunyungkim on 16. 8. 23..
 */
public class RegistrationSelectSpaceTypeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegistrationSelectSpaceTypeActivity";
    private GridView mTypeGridView;
    private SpaceTypeGridViewAdapter mAdapter;
    private ArrayList<TypeGridViewData> mSpaceTypeData = new ArrayList<>();
    private Button mBtnTimeSelect;
    private Button mBtnDaySelect;
    private Button mBtnBack;
    private Button mBtnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_space_type_registration_space);
        getSupportActionBar().setTitle(getResources().getString(R.string.select_space_type));
        mTypeGridView = (GridView) findViewById(R.id.type_grid_view);
        mSpaceTypeData.clear();
        String[] categoryData = getResources().getStringArray(R.array.category);
        for (int i = 0; i < categoryData.length; i++) {
            TypeGridViewData data = new TypeGridViewData();
            data.setTypeName(categoryData[i]);
            data.setSelected(Config.TYPE_SELECTED_NONE);
            mSpaceTypeData.add(data);
        }
        mAdapter = new SpaceTypeGridViewAdapter(RegistrationSelectSpaceTypeActivity.this, mSpaceTypeData);
        mTypeGridView.setAdapter(mAdapter);
        mBtnTimeSelect = (Button) findViewById(R.id.btn_time_select);
        mBtnTimeSelect.setOnClickListener(this);
        mBtnTimeSelect.setSelected(true);
        mBtnDaySelect = (Button) findViewById(R.id.btn_day_select);
        mBtnDaySelect.setSelected(false);
        mBtnDaySelect.setOnClickListener(this);
        mBtnBack = (Button) findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(this);
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mBtnNext.setOnClickListener(this);
    }

    private void setDayOrTimeBtnNone() {
        mBtnDaySelect.setSelected(false);
        mBtnTimeSelect.setSelected(false);
    }

    private String getSelectedFeeType() {
        if (mBtnTimeSelect.isSelected()) {
            return Config.TIME_SELECTED;
        } else {
            return Config.DAY_SELECTED;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_time_select:
                setDayOrTimeBtnNone();
                view.setSelected(true);
                break;
            case R.id.btn_day_select:
                setDayOrTimeBtnNone();
                view.setSelected(true);
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_next:
                Logger.d(TAG, "getSelectedFeeType = " + getSelectedFeeType());
                Logger.d(TAG, "getSelectedType.size() = " + mAdapter.getSelectedType().size());
                if(mAdapter.getSelectedType().size() == 0){
                    Toast.makeText(RegistrationSelectSpaceTypeActivity.this, getString(R.string.please_choose_space_type), Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
    }
}
