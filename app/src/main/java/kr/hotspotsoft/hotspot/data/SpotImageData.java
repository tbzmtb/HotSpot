package kr.hotspotsoft.hotspot.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sunyungkim on 16. 8. 9..
 */
public class SpotImageData implements Parcelable {
    private String spotName;
    private String imagePath;

    public SpotImageData(String spotName, String imagePath) {
        this.spotName = spotName;
        this.imagePath = imagePath;

    }

    public String getSpotName() {
        return spotName;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(spotName);
        dest.writeString(imagePath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SpotImageData> CREATOR = new Creator<SpotImageData>() {
        @Override
        public SpotImageData createFromParcel(Parcel source) {
            String spotName = source.readString();
            String imagePath = source.readString();
            return new SpotImageData(spotName, imagePath);
        }

        @Override
        public SpotImageData[] newArray(int size) {
            return new SpotImageData[size];
        }
    };
}

