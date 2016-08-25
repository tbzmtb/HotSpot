package kr.hotspotsoft.hotspot.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sunyungkim on 16. 8. 9..
 */
public class HotSpotDetailData implements Parcelable {
    private String spotName;
    private String telNumber;
    private String imagePath;
    private String bankAccount;
    private String ownerName;
    private String bankName;
    private String introduce;
    private String shortIntroduce;
    private String availableTime;
    private String holidays;
    private String facilityInfo;
    private String website;
    private String address;
    private String caution;
    private ArrayList<SpaceData> spaceDataList;

    public HotSpotDetailData() {

    }

    public HotSpotDetailData(Parcel in) {
        readFromParcel(in);
    }

    public void setSpotName(String value) {
        spotName = value;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setTelNumber(String value) {
        telNumber = value;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setImagePath(String value) {
        imagePath = value;
    }

    public String getImagePath() {
        return imagePath;
    }


    public void setBankAccount(String value) {
        bankAccount = value;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setOwnerName(String value) {
        ownerName = value;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setBankName(String value) {
        bankName = value;
    }

    public String getBankName() {
        return bankName;
    }


    public void setIntroduce(String value) {
        introduce = value;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setShortIntroduce(String value) {
        shortIntroduce = value;
    }

    public String getShortIntroduce() {
        return shortIntroduce;
    }

    public void setAvailableTime(String value) {
        availableTime = value;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setHolidays(String value) {
        holidays = value;
    }

    public String getHolidays() {
        return holidays;
    }

    public void setFacilityInfo(String value) {
        facilityInfo = value;
    }

    public String getFacilityInfo() {
        return facilityInfo;
    }

    public void setWebsite(String value) {
        website = value;
    }

    public String getWebsite() {
        return website;
    }

    public void setAddress(String value) {
        address = value;
    }

    public String getAddress() {
        return address;
    }

    public void setCaution(String value) {
        caution = value;
    }

    public String getCaution() {
        return caution;
    }
    public void setSpaceDataList(ArrayList<SpaceData> dataList) {
        this.spaceDataList = dataList;
    }

    public ArrayList<SpaceData> getSpaceDataList() {
        return spaceDataList;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(spotName);
        dest.writeString(telNumber);
        dest.writeString(imagePath);
        dest.writeString(bankAccount);
        dest.writeString(ownerName);
        dest.writeString(bankName);
        dest.writeString(introduce);
        dest.writeString(shortIntroduce);
        dest.writeString(availableTime);
        dest.writeString(holidays);
        dest.writeString(facilityInfo);
        dest.writeString(website);
        dest.writeString(address);
        dest.writeString(caution);
    }

    public void readFromParcel(Parcel in) {
        spotName = in.readString();
        telNumber = in.readString();
        imagePath = in.readString();
        bankAccount = in.readString();
        ownerName = in.readString();
        bankName = in.readString();
        introduce = in.readString();
        shortIntroduce = in.readString();
        availableTime = in.readString();
        holidays = in.readString();
        facilityInfo = in.readString();
        website = in.readString();
        address = in.readString();
        caution = in.readString();
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public HotSpotDetailData createFromParcel(Parcel in) {
            return new HotSpotDetailData(in);
        }

        public HotSpotDetailData[] newArray(int size) {
            return new HotSpotDetailData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
