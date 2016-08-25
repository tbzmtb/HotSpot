package kr.hotspotsoft.hotspot.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by tbzm on 16. 4. 26.
 */
public class HotSpotViewPager extends ViewPager {
    private boolean swipeable = true;

    public HotSpotViewPager(Context context) {
        super(context);
    }

    public HotSpotViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSwipeable(boolean swipeable) {
        this.swipeable = swipeable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return (this.swipeable) ? super.onInterceptTouchEvent(event) : false;
    }

}
