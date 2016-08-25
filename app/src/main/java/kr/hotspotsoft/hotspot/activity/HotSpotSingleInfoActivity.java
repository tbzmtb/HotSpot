package kr.hotspotsoft.hotspot.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import kr.hotspotsoft.hotspot.R;
import kr.hotspotsoft.hotspot.adapter.HotSpotSingleInfoAdapter;
import kr.hotspotsoft.hotspot.asynctask.GetHotSpaceDataTask;
import kr.hotspotsoft.hotspot.asynctask.GetHotSpotDetailDataTask;
import kr.hotspotsoft.hotspot.data.HotSpotData;
import kr.hotspotsoft.hotspot.data.HotSpotDetailData;
import kr.hotspotsoft.hotspot.data.SpaceData;
import kr.hotspotsoft.hotspot.util.Config;
import kr.hotspotsoft.hotspot.util.Logger;

/**
 * Created by sunyungkim on 16. 8. 9..
 */
public class HotSpotSingleInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = getClass().getName();
    private PullToRefreshListView mPullToRefreshView;
    private HotSpotDetailDataHandler mHandler;
    private HotSpotSingleInfoAdapter mAdapter;
    private ArrayList<HotSpotDetailData> mHotSpotDetailData = new ArrayList<>();
    private Button mBtnBook;
    private Button mBtnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final HotSpotData hotSpotData = getIntent().getParcelableExtra(Config.INTENT_HOT_SPOT_DATA);
        Logger.d(TAG, "hotSpotData name=" + hotSpotData.getSpotName());
        setContentView(R.layout.activity_hotspot_single_info);
        mHandler = new HotSpotDetailDataHandler();
        mBtnBook = (Button) findViewById(R.id.btn_book);
        mBtnBook.setOnClickListener(this);
        mBtnCall = (Button) findViewById(R.id.btn_call);
        mBtnCall.setOnClickListener(this);
        mPullToRefreshView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_hotspot_listview);
        mAdapter = new HotSpotSingleInfoAdapter(HotSpotSingleInfoActivity.this, mHotSpotDetailData);
        mPullToRefreshView.setAdapter(mAdapter);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                Logger.d(TAG, "onRefresh call ");
                new GetHotSpotDetailDataTask(HotSpotSingleInfoActivity.this, mHandler, hotSpotData.getSpotName()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        new GetHotSpotDetailDataTask(HotSpotSingleInfoActivity.this, mHandler, hotSpotData.getSpotName()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    private class HotSpotDetailDataHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg == null) {
                return;
            }
            if (msg.what == Config.HOTSPOT_DETAIL_DATA_HANDLER) {
                if (msg.obj == null) {
                    return;
                }
                try {
                    mHotSpotDetailData.clear();
                    String data = msg.obj.toString();
                    JSONArray array = new JSONArray(data);
                    for (int i = 0; i < array.length(); i++) {
                        String spotName = array.getJSONObject(i).getString(Config.PARAM_SPOT_NAME);
                        String telNumber = array.getJSONObject(i).getString(Config.PARAM_TEL_NUMBER);
                        String imagePath = array.getJSONObject(i).getString(Config.PARAM_IMAGE_PATH).replace("\\", "");
                        String bankAccount = array.getJSONObject(i).getString(Config.PARAM_BANK_ACCOUNT);
                        String ownerName = array.getJSONObject(i).getString(Config.PARAM_OWNER_NAME);
                        String bankName = array.getJSONObject(i).getString(Config.PARAM_BANK_NAME);
                        String introduce = array.getJSONObject(i).getString(Config.PARAM_INTRODUCE);
                        String shortIntroduce = array.getJSONObject(i).getString(Config.PARAM_SHORT_INTRODUCE);
                        String availableTime = array.getJSONObject(i).getString(Config.PARAM_AVAILABLE_TIME);
                        String holidays = array.getJSONObject(i).getString(Config.PARAM_HOLIDAYS);
                        String facilityInfo = array.getJSONObject(i).getString(Config.PARAM_FACILITY_INFO);
                        String website = array.getJSONObject(i).getString(Config.PARAM_WEBSITE);
                        String address = array.getJSONObject(i).getString(Config.PARAM_ADDRESS);
                        String cautions = array.getJSONObject(i).getString(Config.PARAM_CAUTIONS);
                        Logger.d(TAG, "spotName = " + spotName);
                        Logger.d(TAG, "image_path = " + imagePath);
                        Logger.d(TAG, "bankAccount = " + bankAccount);
                        HotSpotDetailData spotData = new HotSpotDetailData();
                        spotData.setSpotName(spotName);
                        spotData.setTelNumber(telNumber);
                        spotData.setImagePath(imagePath);
                        spotData.setBankAccount(bankAccount);
                        spotData.setOwnerName(ownerName);
                        spotData.setBankName(bankName);
                        spotData.setIntroduce(introduce);
                        spotData.setShortIntroduce(shortIntroduce);
                        spotData.setAvailableTime(availableTime);
                        spotData.setHolidays(holidays);
                        spotData.setFacilityInfo(facilityInfo);
                        spotData.setWebsite(website);
                        spotData.setAddress(address);
                        spotData.setCaution(cautions);
                        mHotSpotDetailData.add(spotData);
                        new GetHotSpaceDataTask(HotSpotSingleInfoActivity.this, mHandler, spotName).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (msg.what == Config.SPACE_DATA_HANDLER) {
                if (msg.obj == null) {
                    return;
                }
                try {
                    String data = msg.obj.toString();
                    JSONArray array = new JSONArray(data);
                    HotSpotDetailData hotSpotDetailData = mHotSpotDetailData.get(0);
                    ArrayList<SpaceData> dataList = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        SpaceData spaceData = new SpaceData();
                        spaceData.setSpaceName(array.getJSONObject(i).getString(Config.PARAM_SPACE_NAME));
                        spaceData.setSpaceType(array.getJSONObject(i).getString(Config.PARAM_SPACE_TYPE));
                        spaceData.setSpaceAvailableTime(array.getJSONObject(i).getString(Config.PARAM_SPACE_AVAILABLE_TIME));
                        spaceData.setSpaceAvailablePeople(array.getJSONObject(i).getString(Config.PARAM_SPACE_AVAILABLE_PEOPLE));
                        spaceData.setSpaceInfo(array.getJSONObject(i).getString(Config.PARAM_SPACE_INFO));
                        spaceData.setWifiEnable(array.getJSONObject(i).getString(Config.PARAM_WIFI_ENABLE));
                        spaceData.setParkingEnable(array.getJSONObject(i).getString(Config.PARAM_PARKING_ENABLE));
                        spaceData.setImagePath0(array.getJSONObject(i).getString(Config.PARAM_IMAGE_00));
                        spaceData.setImagePath1(array.getJSONObject(i).getString(Config.PARAM_IMAGE_01));
                        spaceData.setImagePath2(array.getJSONObject(i).getString(Config.PARAM_IMAGE_02));
                        spaceData.setImagePath3(array.getJSONObject(i).getString(Config.PARAM_IMAGE_03));
                        spaceData.setImagePath4(array.getJSONObject(i).getString(Config.PARAM_IMAGE_04));
                        spaceData.setTimeFee(array.getJSONObject(i).getString(Config.PARAM_TIME_FEE));
                        spaceData.setDayFee(array.getJSONObject(i).getString(Config.PARAM_DAY_FEE));
                        dataList.add(spaceData);
                    }
                    Logger.d(TAG, "dataList.size = " + dataList.size());
                    hotSpotDetailData.setSpaceDataList(dataList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
            }
        }
    }

    private void goDialActivity(String dial) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dial));
        startActivity(intent);
    }

    private void notifyDataSetChanged() {
        Logger.d(TAG, "notifyDataSetChanged call");
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullToRefreshView.onRefreshComplete();

            }
        }, 200);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_book:
                if (mAdapter == null) {
                    return;
                }
                if (mAdapter.getCheckedSpaceData() == null) {
                    Toast.makeText(HotSpotSingleInfoActivity.this, getString(R.string.please_choose_space), Toast.LENGTH_SHORT).show();
                    return;
                }
                Logger.d(TAG, mAdapter.getCheckedSpaceData().getSpaceName());
                break;
            case R.id.btn_call:
                goDialActivity(mHotSpotDetailData.get(0).getTelNumber());
                break;
        }
    }
}