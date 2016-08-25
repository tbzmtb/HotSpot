package kr.hotspotsoft.hotspot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

import kr.hotspotsoft.hotspot.R;
import kr.hotspotsoft.hotspot.data.TypeGridViewData;
import kr.hotspotsoft.hotspot.util.Config;
import kr.hotspotsoft.hotspot.util.Logger;

/**
 * Created by sunyungkim on 16. 8. 23..
 */
public class SpaceTypeGridViewAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String TAG = "SpaceTypeGridViewAdapter";
    private Context mContext;
    private ArrayList<TypeGridViewData> mCategoryData;

    public SpaceTypeGridViewAdapter(Context context, ArrayList<TypeGridViewData> data) {
        mContext = context;
        mCategoryData = data;
    }

    private static class ViewHolder {
        public Button mBtnView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            convertView = inflater.inflate(R.layout.grid_type_row, null);
            holder = new ViewHolder();
            holder.mBtnView = (Button) convertView.findViewById(R.id.grid_btn_view);
            holder.mBtnView.setOnClickListener(this);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mBtnView.setText(mCategoryData.get(position).getTypeName());
        holder.mBtnView.setTag(mCategoryData.get(position));

        Logger.d(TAG, mCategoryData.get(position).getSelected());
        if (mCategoryData.get(position).getSelected().equals(Config.TYPE_SELECTED_ENABLE)) {
            holder.mBtnView.setSelected(true);
//            holder.mBtnView.setBackgroundColor(mContext.getResources().getColor(R.color.com_kakao_button_background_press));
        } else {
            holder.mBtnView.setSelected(false);

//            holder.mBtnView.setBackgroundColor(mContext.getResources().getColor(R.color.com_kakao_account_button_background));
        }
        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return mCategoryData.get(i);
    }

    @Override
    public int getCount() {
        return mCategoryData.size();
    }

    private int getSelectedNum() {
        int count = 0;
        for (int i = 0; i < mCategoryData.size(); i++) {
            if (mCategoryData.get(i).getSelected().equals(Config.TYPE_SELECTED_ENABLE)) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<TypeGridViewData> getSelectedType() {
        ArrayList<TypeGridViewData> data = new ArrayList<>();
        for (int i = 0; i < mCategoryData.size(); i++) {
            if (mCategoryData.get(i).getSelected().equals(Config.TYPE_SELECTED_ENABLE)) {
                data.add(mCategoryData.get(i));
            }
        }
        return data;
    }

    @Override
    public void onClick(View view) {
        int selectedNum = getSelectedNum();

        TypeGridViewData data = (TypeGridViewData) view.getTag();
        if (data.getSelected().equals(Config.TYPE_SELECTED_ENABLE)) {
            data.setSelected(Config.TYPE_SELECTED_NONE);
        } else {
            if (selectedNum > 2) {
                return;
            }
            data.setSelected(Config.TYPE_SELECTED_ENABLE);
        }
        notifyDataSetChanged();
    }
}
