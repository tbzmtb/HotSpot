package kr.hotspotsoft.hotspot.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import java.util.ArrayList;

import kr.hotspotsoft.hotspot.data.SpotImageData;
import kr.hotspotsoft.hotspot.view.PagerViewFragment;

public abstract class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> titles;
    private ArrayList<SpotImageData> imageData;
    private final String TAG = getClass().getName();

    public ViewPagerAdapter(FragmentManager fm, ArrayList<String> titles, ArrayList<SpotImageData> imageData) {
        super(fm);
        this.titles = titles;
        this.imageData = imageData;
    }

    @Override
    public Fragment getItem(int position) {
        return PagerViewFragment.newInstance(position, titles, imageData);
    }

    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);

    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}