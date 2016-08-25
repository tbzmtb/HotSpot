package kr.hotspotsoft.hotspot.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import kr.hotspotsoft.hotspot.R;
import kr.hotspotsoft.hotspot.adapter.HotSpotListAdapter;
import kr.hotspotsoft.hotspot.asynctask.GetHotSpotDataTask;
import kr.hotspotsoft.hotspot.data.CategoryData;
import kr.hotspotsoft.hotspot.data.HotSpotData;
import kr.hotspotsoft.hotspot.util.Config;
import kr.hotspotsoft.hotspot.util.Logger;

/**
 * Created by sunyungkim on 16. 8. 8..
 */
public class HotSpotListActivity extends AppCompatActivity {
    private final String TAG = getClass().getName();
    private PullToRefreshListView mPullToRefreshView;
    private HotSpotDataHandler mHandler;
    private ArrayList<HotSpotData> mHotSpotData = new ArrayList<>();
    private HotSpotListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final CategoryData categoryData = getIntent().getParcelableExtra(Config.INTENT_CATEGORY_DATA);
        Logger.d(TAG, "categoryData name=" + categoryData.getCategoryName());
        Logger.d(TAG, "categoryData type=" + categoryData.getCategoryType());

        setContentView(R.layout.activity_hotspot_list);
        mHandler = new HotSpotDataHandler();
        mPullToRefreshView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_hotspot_listview);
        mAdapter = new HotSpotListAdapter(HotSpotListActivity.this, mHotSpotData);
        mPullToRefreshView.setAdapter(mAdapter);
        mPullToRefreshView.setOnItemClickListener(onItemClickListener);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                Logger.d(TAG, "onRefresh call ");
                new GetHotSpotDataTask(HotSpotListActivity.this, mHandler, categoryData.getCategoryType()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }
        });

        new GetHotSpotDataTask(HotSpotListActivity.this, mHandler, categoryData.getCategoryType()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class HotSpotDataHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg == null) {
                return;
            }

            if (msg.what == Config.HOTSPOT_DATA_HANDLER) {
                if (msg.obj == null) {
                    return;
                }
                try {
                    String data = msg.obj.toString();
                    JSONArray array = new JSONArray(data);
                    mHotSpotData.clear();
                    for (int i = 0; i < array.length(); i++) {
                        String categoryType = array.getJSONObject(i).getString(Config.PARAM_CATEGORY_TYPE);
                        String spotName = array.getJSONObject(i).getString(Config.PARAM_SPOT_NAME);
                        String availableStaff = array.getJSONObject(i).getString(Config.PARAM_AVAILABLE_STAFF);
                        String dayFee = array.getJSONObject(i).getString(Config.PARAM_DAY_FEE);
                        String hourFee = array.getJSONObject(i).getString(Config.PARAM_HOUR_FEE);
                        String spotSize = array.getJSONObject(i).getString(Config.PARAM_SPOT_SIZE);
                        String imagePath = array.getJSONObject(i).getString(Config.PARAM_IMAGE_PATH).replace("\\", "");
                        String email = array.getJSONObject(i).getString(Config.PARAM_ENABlE);
                        String info = array.getJSONObject(i).getString(Config.PARAM_INFO);
                        String registDate = array.getJSONObject(i).getString(Config.PARAM_REGIST_DATE);
                        String location = array.getJSONObject(i).getString(Config.PARAM_LOCATION);

                        Logger.d(TAG, "categoryType = " + categoryType);
                        Logger.d(TAG, "spotName = " + spotName);

                        Logger.d(TAG, "availableStaff = " + availableStaff);
                        Logger.d(TAG, "dayFee = " + dayFee);
                        Logger.d(TAG, "hourFee = " + hourFee);

                        Logger.d(TAG, "spotSize = " + spotSize);
                        Logger.d(TAG, "imagePath = " + imagePath);
                        Logger.d(TAG, "email = " + email);

                        Logger.d(TAG, "info = " + info);
                        Logger.d(TAG, "registDate = " + registDate);
                        Logger.d(TAG, "location = " + location);
                        mHotSpotData.add(new HotSpotData(categoryType, spotName, availableStaff, dayFee,
                                hourFee, spotSize, imagePath, email, info, registDate, location));
                    }
                    notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Logger.d(TAG, "position =" + i);
            Logger.d(TAG, "mHotSpotData.size() =" + mHotSpotData.size());
            if (mHotSpotData.size() < i) {
                return;
            }
            Intent intent = new Intent(HotSpotListActivity.this, HotSpotSingleInfoActivity.class);
            intent.putExtra(Config.INTENT_HOT_SPOT_DATA, mHotSpotData.get(i - 1));
            startActivity(intent);
        }
    };

    private void notifyDataSetChanged() {
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
}
