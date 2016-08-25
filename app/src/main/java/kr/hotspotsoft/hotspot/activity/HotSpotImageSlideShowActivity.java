package kr.hotspotsoft.hotspot.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import kr.hotspotsoft.hotspot.R;
import kr.hotspotsoft.hotspot.adapter.ViewPagerAdapter;
import kr.hotspotsoft.hotspot.asynctask.GetHotSpotImageDataTask;
import kr.hotspotsoft.hotspot.data.HotSpotDetailData;
import kr.hotspotsoft.hotspot.data.SpotImageData;
import kr.hotspotsoft.hotspot.util.Config;
import kr.hotspotsoft.hotspot.util.Logger;
import kr.hotspotsoft.hotspot.view.HotSpotViewPager;
import kr.hotspotsoft.hotspot.view.SlidingTabLayout;

/**
 * Created by sunyungkim on 16. 8. 9..
 */
public class HotSpotImageSlideShowActivity extends AppCompatActivity {
    private HotSpotDetailSlideDataHandler mDataHandler;
    private final String TAG = getClass().getName();
    private ArrayList<SpotImageData> mSpotImageData = new ArrayList<>();
    private ArrayList<String> mImageTitles = new ArrayList<>();
    private SlidingTabLayout slidingTabLayout;
    private HotSpotViewPager mViewPager;
    private ViewPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final HotSpotDetailData spotImageData = getIntent().getParcelableExtra(Config.INTENT_HOT_SPOT_IMAGE_SLIDE_DATA);

        mDataHandler = new HotSpotDetailSlideDataHandler();
        setContentView(R.layout.activity_hotspot_image_slide);
        mViewPager = (HotSpotViewPager) findViewById(R.id.viewpager);
        mViewPager.setSwipeable(true);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mImageTitles, mSpotImageData) {

            @Override
            public Fragment getItem(int position) {
                return super.getItem(position);
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
        slidingTabLayout.setViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected call " + position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });

        new GetHotSpotImageDataTask(HotSpotImageSlideShowActivity.this, mDataHandler, spotImageData.getSpotName()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    private class HotSpotDetailSlideDataHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg == null) {
                return;
            }
            if (msg.what == Config.HOTSPOT_IMAGE_DATA_HANDLER) {
                if (msg.obj == null) {
                    return;
                }
                try {
                    mSpotImageData.clear();
                    String data = msg.obj.toString();
                    JSONArray array = new JSONArray(data);
                    for (int i = 0; i < array.length(); i++) {
                        String spotName = array.getJSONObject(i).getString(Config.PARAM_SPOT_NAME);
                        String imagePath = array.getJSONObject(i).getString(Config.PARAM_IMAGE_PATH).replace("\\", "");
                        Logger.d(TAG, "spotName = " + spotName);
                        Logger.d(TAG, "image_path = " + imagePath);
                        mSpotImageData.add(new SpotImageData(spotName, imagePath));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
            }

        }
    }

    private void notifyDataSetChanged() {
        Logger.d(TAG, "notifyDataSetChanged call");
        mImageTitles.clear();
        for (int i = 0; i < mSpotImageData.size(); i++) {
            mImageTitles.add(String.valueOf(i));
        }
        slidingTabLayout.setViewPager(mViewPager);
        if (mPagerAdapter != null) {
            mPagerAdapter.notifyDataSetChanged();
        }

    }
}
