package kr.hotspotsoft.hotspot.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kr.hotspotsoft.hotspot.R;
import kr.hotspotsoft.hotspot.adapter.HotSpotMainCategoryListAdapter;
import kr.hotspotsoft.hotspot.asynctask.GetCategoryDataTask;
import kr.hotspotsoft.hotspot.data.CategoryData;
import kr.hotspotsoft.hotspot.util.Config;
import kr.hotspotsoft.hotspot.util.DataPreference;
import kr.hotspotsoft.hotspot.util.Logger;

public class HotSpotMainCategoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private long mBackKeyPressedTime;
    private Toast mFinishToast;
    private final String TAG = getClass().getName();
    private PullToRefreshListView mPullToRefreshView;
    private DataHandler mHandler;
    private ArrayList<CategoryData> mCategoryDataList = new ArrayList<>();
    private ArrayList<CategoryData> mServerAllDataList = new ArrayList<>();
    private HotSpotMainCategoryListAdapter mAdapter;
    public static HotSpotMainCategoryActivity INSTANCE;
    public TextView mNavTitleTextView;
    public ImageView mProfileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);
        DataPreference.PREF = getSharedPreferences(HotSpotMainCategoryActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
        INSTANCE = this;
        mHandler = new DataHandler();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        header.setOnClickListener(mNavHeaderOnClickListener);
        mNavTitleTextView = (TextView) header.findViewById(R.id.nav_header_title);
        mProfileImageView = (ImageView) header.findViewById(R.id.imageView);
        mAdapter = new HotSpotMainCategoryListAdapter(this, mCategoryDataList, mServerAllDataList);
        mPullToRefreshView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
        mPullToRefreshView.setAdapter(mAdapter);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                Logger.d(TAG, "onRefresh call ");
                new GetCategoryDataTask(HotSpotMainCategoryActivity.this, mHandler).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (DataPreference.getLoginId() == null && DataPreference.getLoginNickName() == null) {
            mNavTitleTextView.setText(getString(R.string.you_need_to_login));
        } else {
            mNavTitleTextView.setText(DataPreference.getLoginNickName());
        }
        if (DataPreference.getThumbNailImage() != null) {
            Glide.with(this)
                    .load(DataPreference.getThumbNailImage())
                    .bitmapTransform(new CropCircleTransformation(Glide.get(this).getBitmapPool()))
                    .into(mProfileImageView);
        } else {
            Glide.with(this)
                    .load(android.R.drawable.sym_def_app_icon)
                    .bitmapTransform(new CropCircleTransformation(Glide.get(this).getBitmapPool()))
                    .into(mProfileImageView);
        }

        new GetCategoryDataTask(HotSpotMainCategoryActivity.this, mHandler).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    private class DataHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg == null) {
                return;
            }

            if (msg.what == Config.CATEGORY_DATA_HANDLER) {
                if (msg.obj == null) {
                    return;
                }
                try {
                    mCategoryDataList.clear();
                    String data = msg.obj.toString();
                    JSONArray array = new JSONArray(data);
                    String[] categoryData = getResources().getStringArray(R.array.category);
                    for (int i = 0; i < categoryData.length; i++) {

                        mCategoryDataList.add(new CategoryData(categoryData[i], String.valueOf(i)));

                    }
//                    for (int i = 0; i < array.length(); i++) {
//                        String categoryName = array.getJSONObject(i).getString(Config.PARAM_CATEGORY_NAME);
//                        String categoryType = array.getJSONObject(i).getString(Config.PARAM_CATEGORY_TYPE);
//                        Logger.d(TAG, "categoryName = " + categoryName);
//                        Logger.d(TAG, "categoryType = " + categoryType);
//                        mCategoryDataList.add(new CategoryData(categoryName, categoryType));
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
            }
        }
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() > mBackKeyPressedTime + 2000) {
                mBackKeyPressedTime = System.currentTimeMillis();
                mFinishToast = Toast.makeText(HotSpotMainCategoryActivity.this, getResources().getString(R.string.backbutton_click_is_finish), Toast.LENGTH_SHORT);
                mFinishToast.show();
                return;
            }
            if (System.currentTimeMillis() <= mBackKeyPressedTime + 2000) {
                mFinishToast.cancel();
                super.onBackPressed();
            }
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (DataPreference.getLoginId() == null && DataPreference.getLoginNickName() == null) {
            Intent intent = new Intent(HotSpotMainCategoryActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.request_confirm) {
            Logger.d(TAG, "request_confirm clicked");
        }else if(id == R.id.cancel_refund){
        }else if(id == R.id.used_complete){
        }else if(id == R.id.registration_space){
            Intent intent = new Intent(HotSpotMainCategoryActivity.this, RegistrationSpaceActivity.class);
            startActivity(intent);
        }else if(id == R.id.management_space){
        }else if(id == R.id.terms_of_use){
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    View.OnClickListener mNavHeaderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HotSpotMainCategoryActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    };
}
