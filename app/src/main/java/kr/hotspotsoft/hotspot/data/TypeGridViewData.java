package kr.hotspotsoft.hotspot.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sunyungkim on 16. 8. 23..
 */
public class TypeGridViewData implements Parcelable {
    private String typeName;
    private String selected;

    public TypeGridViewData() {

    }

    public TypeGridViewData(Parcel in) {
        readFromParcel(in);
    }

    public void setTypeName(String name) {
        typeName = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setSelected(String value) {
        selected = value;
    }

    public String getSelected() {
        return selected;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(typeName);
        dest.writeString(selected);

    }

    public void readFromParcel(Parcel in) {
        typeName = in.readString();
        selected = in.readString();
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public TypeGridViewData createFromParcel(Parcel in) {
            return new TypeGridViewData(in);
        }

        public TypeGridViewData[] newArray(int size) {
            return new TypeGridViewData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}

