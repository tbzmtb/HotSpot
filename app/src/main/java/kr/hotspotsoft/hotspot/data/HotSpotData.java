package kr.hotspotsoft.hotspot.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sunyungkim on 16. 8. 8..
 */
public class HotSpotData implements Parcelable {
    private String categoryType;
    private String spotName;
    private String availableStaff;
    private String dayFee;
    private String hourFee;
    private String spotSize;
    private String imagePath;
    private String email;
    private String info;
    private String registDate;
    private String location;

    public HotSpotData(String categoryType, String spotName, String availableStaff,
                       String dayFee, String hourFee, String spotSize, String imagePath, String email, String info, String registDate, String location) {
        this.categoryType = categoryType;
        this.spotName = spotName;
        this.availableStaff = availableStaff;
        this.dayFee = dayFee;
        this.hourFee = hourFee;
        this.spotSize = spotSize;
        this.imagePath = imagePath;
        this.email = email;
        this.info = info;
        this.registDate = registDate;
        this.location = location;
    }


    public String getCategoryType() {
        return categoryType;
    }

    public String getSpotName() {
        return spotName;
    }

    public String getAvailableStaff() {
        return availableStaff;
    }

    public String getDayFee() {
        return dayFee;
    }

    public String getHourFee() {
        return hourFee;
    }

    public String getSpotSize() {
        return spotSize;
    }

    public String getEmail() {
        return email;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getInfo() {
        return info;
    }

    public String getRegistDate() {
        return registDate;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryType);
        dest.writeString(spotName);
        dest.writeString(availableStaff);
        dest.writeString(dayFee);
        dest.writeString(hourFee);
        dest.writeString(spotSize);
        dest.writeString(imagePath);
        dest.writeString(email);
        dest.writeString(info);
        dest.writeString(registDate);
        dest.writeString(location);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HotSpotData> CREATOR = new Creator<HotSpotData>() {
        @Override
        public HotSpotData createFromParcel(Parcel source) {
            String categoryType = source.readString();
            String spotName = source.readString();
            String availableStaff = source.readString();
            String dayFee = source.readString();
            String hourFee = source.readString();
            String spotSize = source.readString();
            String imagePath = source.readString();
            String email = source.readString();
            String explain = source.readString();
            String registDate = source.readString();
            String location = source.readString();
            return new HotSpotData(categoryType, spotName, availableStaff, dayFee, hourFee, spotSize,
                    imagePath, email, explain, registDate, location);
        }

        @Override
        public HotSpotData[] newArray(int size) {
            return new HotSpotData[size];
        }
    };
}
