package kr.hotspotsoft.hotspot.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.hotspotsoft.hotspot.R;
import kr.hotspotsoft.hotspot.activity.ImageZoomableActivity;
import kr.hotspotsoft.hotspot.data.SpotImageData;
import kr.hotspotsoft.hotspot.util.Config;

public class PagerViewFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_POSITION = "position";
    private static final String SPOT_DATA_KEY = "spot_data_key";
    private static final String TITLE_DATA_ARRAY = "title_data_array";
    private final String TAG = getClass().getName();
    private ArrayList<SpotImageData> mImageData;
    private ArrayList<String> mTitles;
    private int position;

    public static PagerViewFragment newInstance(int position, ArrayList<String> titles, ArrayList<SpotImageData> imageData) {
        PagerViewFragment f = new PagerViewFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putStringArrayList(TITLE_DATA_ARRAY, titles);
        b.putParcelableArrayList(SPOT_DATA_KEY, imageData);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        mImageData = getArguments().getParcelableArrayList(SPOT_DATA_KEY);
        mTitles = getArguments().getStringArrayList(TITLE_DATA_ARRAY);
        View rootView = inflater.inflate(R.layout.page, container, false);
        rootView.setTag(mImageData.get(position));
        rootView.setOnClickListener(this);

        ImageView image = (ImageView) rootView.findViewById(R.id.slide_image);

        String title = mTitles.get(position);
        Log.d(TAG, "title == " + title);
        SpotImageData spotData = mImageData.get(position);

        Glide.with(getActivity())
                .load(spotData.getImagePath())
                .into(image);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        SpotImageData spotData = (SpotImageData) view.getTag();
        Intent intent = new Intent(getActivity(), ImageZoomableActivity.class);
        intent.putExtra(Config.INTENT_HOT_SPOT_SINGLE_IMAGE_DATA, spotData);
        startActivity(intent);
    }
}