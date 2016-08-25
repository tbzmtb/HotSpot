package kr.hotspotsoft.hotspot.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tbzm on 16. 4. 20.
 */
public class HotspotSQLiteHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "parkingdata.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_PARKING_LIST = "table_parking_list";
    public static final String TABLE_USER_LIST = "table_user_list";

    public static final String COL_INEDEX = "_id";
    public static final String COL_SENSOR_ID = "sensorid";
    public static final String COL_GWIDX = "gwidx";
    public static final String COL_DATE = "date";
    public static final String COL_STATE = "state";
    public static final String COL_BATTERY = "battery";
    public static final String COL_LOT_NAME = "lotname";

    private static final String DATABASE_CREATE_PARKING_LIST = "create table "
            + TABLE_PARKING_LIST + "(" + COL_INEDEX
            + " integer primary key autoincrement, "
            + COL_SENSOR_ID + " text not null , "
            + COL_GWIDX + " text not null , "
            + COL_DATE + " text not null , "
            + COL_STATE + " text not null , "
            + COL_BATTERY + " text not null , "
            + COL_LOT_NAME + " text not null );";

    private static final String DATABASE_CREATE_USER_LIST = "create table "
            + TABLE_USER_LIST + "(" + COL_INEDEX
            + " integer primary key autoincrement, "
            + COL_SENSOR_ID + " text not null , "
            + COL_GWIDX + " text not null , "
            + COL_DATE + " text not null , "
            + COL_STATE + " text not null );";

    public HotspotSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_PARKING_LIST);
        db.execSQL(DATABASE_CREATE_USER_LIST);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_PARKING_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_USER_LIST);
        onCreate(db);
    }
}
