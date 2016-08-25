package kr.hotspotsoft.hotspot.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import kr.hotspotsoft.hotspot.R;
import kr.hotspotsoft.hotspot.activity.HotSpotListActivity;
import kr.hotspotsoft.hotspot.data.CategoryData;
import kr.hotspotsoft.hotspot.util.Config;

/**
 * Created by tbzm on 15. 10. 19.
 */
public class HotSpotMainCategoryListAdapter extends BaseAdapter implements View.OnClickListener {
    private final String TAG = getClass().getName();
    private Context context;
    private ArrayList<CategoryData> categoryData = new ArrayList<>();
    private ArrayList<CategoryData> serverData = new ArrayList<>();

    public HotSpotMainCategoryListAdapter(Context context, ArrayList<CategoryData> categoryData, ArrayList<CategoryData> serverData) {
        this.context = context;
        this.categoryData = categoryData;
        this.serverData = serverData;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (serverData == null) {
            return null;
        }
        return serverData.get(position);
    }

    @Override
    public int getCount() {
        if (serverData == null) {
            return 0;
        }
        return serverData.size() + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
//        DetectedViewHolder holder;
//        if (v == null) {
//            holder = new DetectedViewHolder();
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            v = inflater.inflate(R.layout.detected_item_row, null);
//            holder.mImageView = (ImageView) v.findViewById(R.id.icon_image);
//            holder.mFileName = (TextView) v.findViewById(R.id.file_Name);
//            holder.mDate = (TextView) v.findViewById(R.id.date);
//            v.setTag(holder);
//        } else {
//            holder = (DetectedViewHolder) v.getTag();
//        }
        if (position == 0) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.category_list_item, null);
            LinearLayout parentLayout = (LinearLayout) v.findViewById(R.id.item_parent_layout);
            for (int i = 0; i < categoryData.size(); i++) {
                View child = inflater.inflate(R.layout.category_list_item_row, null);
                Button left_button = (Button) child.findViewById(R.id.left_button);
                left_button.setTag(categoryData.get(i));
                left_button.setText(categoryData.get(i).getCategoryName());
                left_button.setOnClickListener(this);
                if (i + 1 < categoryData.size()) {
                    Button right_button = (Button) child.findViewById(R.id.right_button);
                    right_button.setTag(categoryData.get(i + 1));
                    right_button.setText(categoryData.get(i + 1).getCategoryName());
                    right_button.setOnClickListener(this);
                    right_button.setVisibility(View.VISIBLE);

                }else{
                    Button right_button = (Button) child.findViewById(R.id.right_button);
                    right_button.setVisibility(View.GONE);
                }
                if (i % 2 == 0) {
                    parentLayout.addView(child);
                }
            }
        }
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_button:
            case R.id.right_button:
                CategoryData categoryData = (CategoryData) view.getTag();
                Intent intent = new Intent(context, HotSpotListActivity.class);
                intent.putExtra(Config.INTENT_CATEGORY_DATA, categoryData);
                context.startActivity(intent);
                break;
        }
    }
}
