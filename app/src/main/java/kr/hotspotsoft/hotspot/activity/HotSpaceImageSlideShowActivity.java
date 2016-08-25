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
import kr.hotspotsoft.hotspot.data.SpaceData;
import kr.hotspotsoft.hotspot.data.SpotImageData;
import kr.hotspotsoft.hotspot.util.Config;
import kr.hotspotsoft.hotspot.util.Logger;
import kr.hotspotsoft.hotspot.view.HotSpotViewPager;
import kr.hotspotsoft.hotspot.view.SlidingTabLayout;

/**
 * Created by sunyungkim on 16. 8. 18..
 */
public class HotSpaceImageSlideShowActivity extends AppCompatActivity {
    private final String TAG = getClass().getName();
    private ArrayList<SpotImageData> mSpaceImageData = new ArrayList<>();
    private ArrayList<String> mImageTitles = new ArrayList<>();
    private SlidingTabLayout slidingTabLayout;
    private HotSpotViewPager mViewPager;
    private ViewPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SpaceData spaceData = getIntent().getParcelableExtra(Config.INTENT_HOT_SPACE_IMAGE_SLIDE_DATA);

        setContentView(R.layout.activity_hotspot_image_slide);
        mViewPager = (HotSpotViewPager) findViewById(R.id.viewpager);
        mViewPager.setSwipeable(true);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mImageTitles, mSpaceImageData) {

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
        mSpaceImageData.clear();
        String imagePath0 = spaceData.getImagePath0();
        String imagePath1 = spaceData.getImagePath1();
        String imagePath2 = spaceData.getImagePath2();
        String imagePath3 = spaceData.getImagePath3();
        String imagePath4 = spaceData.getImagePath4();

        if (imagePath0 != null && !imagePath0.equals("")) {
            mSpaceImageData.add(new SpotImageData(null, imagePath0));
        }
        if (imagePath1 != null && !imagePath1.equals("")) {
            mSpaceImageData.add(new SpotImageData(null, imagePath1));
        }
        if (imagePath2 != null && !imagePath2.equals("")) {
            mSpaceImageData.add(new SpotImageData(null, imagePath2));
        }
        if (imagePath3 != null && !imagePath3.equals("")) {
            mSpaceImageData.add(new SpotImageData(null, imagePath3));
        }
        if (imagePath4 != null && !imagePath4.equals("")) {
            mSpaceImageData.add(new SpotImageData(null, imagePath4));
        }
        notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {
        Logger.d(TAG, "notifyDataSetChanged call");
        mImageTitles.clear();
        for (int i = 0; i < mSpaceImageData.size(); i++) {
            mImageTitles.add(String.valueOf(i));
        }
        slidingTabLayout.setViewPager(mViewPager);
        if (mPagerAdapter != null) {
            mPagerAdapter.notifyDataSetChanged();
        }

    }
}
