package kr.hotspotsoft.hotspot.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sunyungkim on 16. 8. 9..
 */
public class SpaceData implements Parcelable {
    private String spaceName;
    private String spaceType;
    private String spaceAvailableTime;
    private String spaceAvailablePeople;
    private String spaceInfo;
    private String wifiEnable;
    private String parkingEnable;
    private String imagePath0;
    private String imagePath1;
    private String imagePath2;
    private String imagePath3;
    private String imagePath4;
    private String dayFee;
    private String timeFee;

    public SpaceData() {

    }

    public SpaceData(Parcel in) {
        readFromParcel(in);
    }

    public void setSpaceName(String name){
        spaceName = name;
    }

    public String getSpaceName(){
        return spaceName;
    }

    public void setSpaceType(String type) {
        spaceType = type;
    }

    public void setSpaceAvailableTime(String time) {
        spaceAvailableTime = time;
    }

    public void setSpaceAvailablePeople(String num) {
        spaceAvailablePeople = num;
    }

    public void setSpaceInfo(String info) {
        spaceInfo = info;
    }

    public void setWifiEnable(String enable) {
        wifiEnable = enable;
    }

    public void setParkingEnable(String enable) {
        parkingEnable = enable;
    }

    public void setImagePath0(String path) {
        imagePath0 = path;
    }

    public void setImagePath1(String path) {
        imagePath1 = path;
    }

    public void setImagePath2(String path) {
        imagePath2 = path;
    }

    public void setImagePath3(String path) {
        imagePath3 = path;
    }

    public void setImagePath4(String path) {
        imagePath4 = path;
    }

    public void setDayFee(String fee) {
        dayFee = fee;
    }

    public void setTimeFee(String fee) {
        timeFee = fee;
    }

    public String getSpaceType() {
        return spaceType;
    }

    public String getSpaceAvailableTime() {
        return spaceAvailableTime;
    }

    public String getSpaceAvailablePeople() {
        return spaceAvailablePeople;
    }

    public String getSpaceInfo() {
        return spaceInfo;
    }

    public String getWifiEnable() {
        return wifiEnable;
    }

    public String getParkingEnable() {
        return parkingEnable;
    }

    public String getImagePath0() {
        return imagePath0;
    }

    public String getImagePath1() {
        return imagePath1;
    }

    public String getImagePath2() {
        return imagePath2;
    }

    public String getImagePath3() {
        return imagePath3;
    }

    public String getImagePath4() {
        return imagePath4;
    }

    public String getDayFee() {
        return dayFee;
    }

    public String getTimeFee() {
        return timeFee;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(spaceName);
        dest.writeString(spaceType);
        dest.writeString(spaceAvailableTime);
        dest.writeString(spaceAvailablePeople);
        dest.writeString(spaceInfo);
        dest.writeString(wifiEnable);
        dest.writeString(parkingEnable);
        dest.writeString(imagePath0);
        dest.writeString(imagePath1);
        dest.writeString(imagePath2);
        dest.writeString(imagePath3);
        dest.writeString(imagePath4);
        dest.writeString(timeFee);
        dest.writeString(dayFee);

    }

    public void readFromParcel(Parcel in) {
        spaceName = in.readString();
        spaceType = in.readString();
        spaceAvailableTime = in.readString();
        spaceAvailablePeople = in.readString();
        spaceInfo = in.readString();
        wifiEnable = in.readString();
        parkingEnable = in.readString();
        imagePath0 = in.readString();
        imagePath1 = in.readString();
        imagePath2 = in.readString();
        imagePath3 = in.readString();
        imagePath4 = in.readString();
        timeFee = in.readString();
        dayFee = in.readString();
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public SpaceData createFromParcel(Parcel in) {
            return new SpaceData(in);
        }

        public SpaceData[] newArray(int size) {
            return new SpaceData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
