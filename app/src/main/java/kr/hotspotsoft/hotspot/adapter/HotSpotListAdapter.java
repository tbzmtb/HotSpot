package kr.hotspotsoft.hotspot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.hotspotsoft.hotspot.R;
import kr.hotspotsoft.hotspot.data.HotSpotData;

/**
 * Created by sunyungkim on 16. 8. 9..
 */
public class HotSpotListAdapter extends BaseAdapter implements View.OnClickListener {
    private final String TAG = getClass().getName();
    private Context context;
    private ArrayList<HotSpotData> hotspotData = new ArrayList<>();

    public HotSpotListAdapter(Context context, ArrayList<HotSpotData> hotspotData) {
        this.context = context;
        this.hotspotData = hotspotData;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (hotspotData == null) {
            return null;
        }
        return hotspotData.get(position);
    }

    @Override
    public int getCount() {
        if (hotspotData == null) {
            return 0;
        }
        return hotspotData.size();
    }

    private class HotSpotViewHolder{
        private ImageView hotspotImage;
        private TextView titleText;
        private TextView subTitleText;
        private TextView feeText;
        private TextView locationText;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        HotSpotViewHolder holder;
        if (v == null) {
            holder = new HotSpotViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.hotspot_list_item_row, null);
            holder.hotspotImage = (ImageView)v.findViewById(R.id.hotspot_image);
            holder.titleText = (TextView) v.findViewById(R.id.title_text);
            holder.subTitleText = (TextView) v.findViewById(R.id.sub_title_text);
            holder.feeText = (TextView) v.findViewById(R.id.fee_text);
            holder.locationText = (TextView) v.findViewById(R.id.location_text);
            v.setTag(holder);
        } else {
            holder = (HotSpotViewHolder) v.getTag();
        }

        HotSpotData data = hotspotData.get(position);
        Glide.with(context)
                .load(data.getImagePath())
                .into(holder.hotspotImage);

        holder.titleText.setText(data.getSpotName());
        holder.subTitleText.setText(data.getInfo());
        holder.feeText.setText(data.getHourFee());
        holder.locationText.setText(data.getLocation());
        return v;
    }

    @Override
    public void onClick(View view) {

    }
}
