package kr.hotspotsoft.hotspot.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tbzm on 15. 10. 19.
 */
public class CategoryData implements Parcelable {
    private String categoryName;
    private String categoryType;

    public CategoryData(String categoryName, String categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryType(){
        return categoryType;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryName);
        dest.writeString(categoryType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoryData> CREATOR = new Creator<CategoryData>() {
        @Override
        public CategoryData createFromParcel(Parcel source) {
            String categoryName = source.readString();
            String categoryType = source.readString();
            return new CategoryData(categoryName, categoryType);
        }

        @Override
        public CategoryData[] newArray(int size) {
            return new CategoryData[size];
        }
    };
}
