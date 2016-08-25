package kr.hotspotsoft.hotspot.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import kr.hotspotsoft.hotspot.R;
import kr.hotspotsoft.hotspot.activity.HotSpaceImageSlideShowActivity;
import kr.hotspotsoft.hotspot.activity.HotSpotImageSlideShowActivity;
import kr.hotspotsoft.hotspot.data.HotSpotDetailData;
import kr.hotspotsoft.hotspot.data.SpaceData;
import kr.hotspotsoft.hotspot.util.Config;
import kr.hotspotsoft.hotspot.util.Logger;

/**
 * Created by sunyungkim on 16. 8. 9..
 */
public class HotSpotSingleInfoAdapter extends BaseAdapter implements View.OnClickListener {
    private final String TAG = getClass().getName();
    private Context context;
    private ArrayList<HotSpotDetailData> hotSpotDetailData;
    private HashMap<Integer, CheckBox> mHashMap = new HashMap<>();
    private SpaceData mCheckedSpaceData = null;
    private TextView mTotalFeeTextView;
    private SpaceData mSelectedSpaceData;

    public HotSpotSingleInfoAdapter(Context context, ArrayList<HotSpotDetailData> hotSpotDetailData) {
        this.context = context;
        this.hotSpotDetailData = hotSpotDetailData;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (hotSpotDetailData == null) {
            return null;
        }
        return hotSpotDetailData.get(position);
    }

    @Override
    public int getCount() {
        if (hotSpotDetailData == null) {
            return 0;
        }
        return hotSpotDetailData.size();
    }

    private class HotSpotDetailViewHolder {
        ImageView mTitleImageView;
        TextView mTitleTextView;
        TextView mSubTitleTextView;
        TextView mIntroduceTextView;
        TextView mAvailableTimeTextView;
        TextView mHolidaysTextView;
        TextView mFacilityTextView;
        TextView mCautionsTextView;
        TextView mAddressTitleTextView;
        TextView mAddressTextView;
        TextView mAddressWebsite;
        LinearLayout mSpaceLayout;
        TextView mTotalFeeText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        HotSpotDetailViewHolder holder;
        if (v == null) {
            holder = new HotSpotDetailViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.hotspot_single_item_row, null);
            holder.mTitleImageView = (ImageView) v.findViewById(R.id.title_imageview);
            holder.mTitleImageView.setOnClickListener(this);
            holder.mTitleTextView = (TextView) v.findViewById(R.id.title_text);
            holder.mSubTitleTextView = (TextView) v.findViewById(R.id.sub_title_text);
            holder.mIntroduceTextView = (TextView) v.findViewById(R.id.introduce_text);
            holder.mAvailableTimeTextView = (TextView) v.findViewById(R.id.available_time_text);
            holder.mHolidaysTextView = (TextView) v.findViewById(R.id.holidays_text);
            holder.mFacilityTextView = (TextView) v.findViewById(R.id.facility_text);
            holder.mCautionsTextView = (TextView) v.findViewById(R.id.cautions_text);
            holder.mAddressTitleTextView = (TextView) v.findViewById(R.id.address_title);
            holder.mAddressTextView = (TextView) v.findViewById(R.id.address_text);
            holder.mAddressWebsite = (TextView) v.findViewById(R.id.address_website);
            holder.mAddressWebsite.setOnClickListener(this);
            holder.mSpaceLayout = (LinearLayout) v.findViewById(R.id.space_layout);
            holder.mTotalFeeText = (TextView) v.findViewById(R.id.total_fee_text);
            mTotalFeeTextView = holder.mTotalFeeText;
            v.setTag(holder);
        } else {
            holder = (HotSpotDetailViewHolder) v.getTag();
        }
        HotSpotDetailData data = hotSpotDetailData.get(position);
        Glide.with(context)
                .load(data.getImagePath())
                .centerCrop()
                .into(holder.mTitleImageView);
        holder.mTitleTextView.setText(data.getSpotName());
        holder.mSubTitleTextView.setText(data.getShortIntroduce());
        holder.mIntroduceTextView.setText(data.getIntroduce());
        holder.mAvailableTimeTextView.setText(data.getAvailableTime());
        holder.mHolidaysTextView.setText(data.getHolidays());
        holder.mFacilityTextView.setText(data.getFacilityInfo());
        holder.mCautionsTextView.setText(data.getCaution());
        holder.mAddressTitleTextView.setText(data.getSpotName());
        holder.mAddressTextView.setText(data.getAddress());
        holder.mAddressWebsite.setText(data.getWebsite());
        mHashMap.clear();
        holder.mSpaceLayout.removeAllViews();
        holder.mTotalFeeText.setText("0");
        for (int i = 0; i < data.getSpaceDataList().size(); i++) {
            ArrayList<SpaceData> dataList = data.getSpaceDataList();
            SpaceData spaceData = dataList.get(i);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View spaceView = inflater.inflate(R.layout.space_item_layout, null);
            spaceView.setTag(spaceData);
            spaceView.setOnClickListener(onSpaceItemClicked);
            CheckBox checkBox = (CheckBox) spaceView.findViewById(R.id.space_check_box);
            mHashMap.put(i, checkBox);
            checkBox.setOnClickListener(onCheckClickListner);
            checkBox.setTag(spaceData);
            TextView spaceFeeText = (TextView) spaceView.findViewById(R.id.fee_text);
            spaceFeeText.setText(spaceData.getTimeFee());

            TextView tv = (TextView) spaceView.findViewById(R.id.space_text);
            tv.setText(spaceData.getSpaceName());
            holder.mSpaceLayout.addView(spaceView);
        }
        return v;
    }

    View.OnClickListener onCheckClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for (int i = 0; i < mHashMap.size(); i++) {
                mHashMap.get(i).setChecked(false);
            }
            ((CheckBox) view).setChecked(true);
            mCheckedSpaceData = (SpaceData) view.getTag();
            mTotalFeeTextView.setText(mCheckedSpaceData.getTimeFee());
        }
    };

    View.OnClickListener onSpaceItemClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mSelectedSpaceData = (SpaceData) view.getTag();
            Logger.d(TAG, mSelectedSpaceData.getSpaceName());
            MaterialDialog dialog = new MaterialDialog.Builder(context)
                    .customView(R.layout.space_detail_view, true)
                    .positiveText(R.string.btn_close)
                    .build();
            View customView = dialog.getCustomView();
            ImageView imageView = (ImageView) customView.findViewById(R.id.space_detail_image);
            imageView.setOnClickListener(spaceDetailPopupImageOnClickListener);
            Glide.with(context)
                    .load(mSelectedSpaceData.getImagePath0())
                    .centerCrop()
                    .into(imageView);
            TextView titleText = (TextView) customView.findViewById(R.id.title_text);
            titleText.setText(mSelectedSpaceData.getSpaceName());
            TextView detailInfo = (TextView) customView.findViewById(R.id.space_detail_info);
            detailInfo.setText(mSelectedSpaceData.getSpaceInfo());
            TextView feeText = (TextView)customView.findViewById(R.id.fee_text);
            feeText.setText(mSelectedSpaceData.getTimeFee());
            TextView typeText = (TextView)customView.findViewById(R.id.space_detail_type);
            typeText.setText(getTypeTextFromTypeNum(mSelectedSpaceData.getSpaceType()));
            TextView bookTime = (TextView)customView.findViewById(R.id.space_detail_book_time);
            bookTime.setText(mSelectedSpaceData.getSpaceAvailableTime());
            TextView bookMaxNum = (TextView)customView.findViewById(R.id.space_detail_book_num);
            bookMaxNum.setText(mSelectedSpaceData.getSpaceAvailablePeople());

            LinearLayout wifiLayout = (LinearLayout)customView.findViewById(R.id.wifi_layout);
            if(mSelectedSpaceData.getWifiEnable().equals(Config.WIFI_ENABLE)){
                wifiLayout.setVisibility(View.VISIBLE);
            }else{
                wifiLayout.setVisibility(View.GONE);
            }
            LinearLayout parkingLayout = (LinearLayout)customView.findViewById(R.id.parking_layout);
            if(mSelectedSpaceData.getWifiEnable().equals(Config.PARKING_ENABLE)){
                parkingLayout.setVisibility(View.VISIBLE);
            }else{
                parkingLayout.setVisibility(View.GONE);
            }

            dialog.show();
        }
    };

    View.OnClickListener spaceDetailPopupImageOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, HotSpaceImageSlideShowActivity.class);
            intent.putExtra(Config.INTENT_HOT_SPACE_IMAGE_SLIDE_DATA, mSelectedSpaceData);
            context.startActivity(intent);
        }
    };

    private String getTypeTextFromTypeNum(String num){
        String[] categoryData = context.getResources().getStringArray(R.array.category);
        for(int i=0;i<categoryData.length;i++){
            if(String.valueOf(i).equals(num)){
                return categoryData[i];
            }
        }
        return categoryData[0];
    }

    public SpaceData getCheckedSpaceData() {
        return mCheckedSpaceData;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_imageview:
                Intent intent = new Intent(context, HotSpotImageSlideShowActivity.class);
                intent.putExtra(Config.INTENT_HOT_SPOT_IMAGE_SLIDE_DATA, hotSpotDetailData.get(0));
                context.startActivity(intent);
                break;
            case R.id.address_website:
                TextView textView = (TextView) view;
                goWebSite(textView.getText().toString());
                break;
        }
    }

    private void goWebSite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }
}

