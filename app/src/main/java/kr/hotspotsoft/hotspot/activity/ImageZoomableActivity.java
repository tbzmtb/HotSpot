package kr.hotspotsoft.hotspot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import kr.hotspotsoft.hotspot.R;
import kr.hotspotsoft.hotspot.data.SpotImageData;
import kr.hotspotsoft.hotspot.util.Config;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by sunyungkim on 16. 7. 31..
 */
public class ImageZoomableActivity extends AppCompatActivity{
    private final String TAG = getClass().getName();
    private ImageView mDetailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zoomable_image);
        mDetailImage = (ImageView) findViewById(R.id.zoomable_image);
        SpotImageData data = getIntent().getParcelableExtra(Config.INTENT_HOT_SPOT_SINGLE_IMAGE_DATA);
        Glide.with(this)
                .load(data.getImagePath())
                .into(mDetailImage);
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(mDetailImage);
        mAttacher.update();
    }
}
