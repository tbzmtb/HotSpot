package kr.hotspotsoft.hotspot.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by tbzm on 16. 4. 20.
 */
public class HotspotProvider extends ContentProvider {
    public static final String AUTHORITY = "kr.hotspotsoft.hotspot.provider.HotspotProvider";
    public static final Uri PARKING_TABLE_URI = Uri.parse("content://"+AUTHORITY+"/parking_table");
    public static final Uri USER_TABLE_URI = Uri.parse("content://"+AUTHORITY+"/user_table");
    private static final int PARKING_TABLE = 0;
    private static final int USER_TABLE = 1;
    private HotspotSQLiteHelper mParkingHelper;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "parking_table", PARKING_TABLE);
        uriMatcher.addURI(AUTHORITY, "user_table", USER_TABLE);

    }

    @Override
    public boolean onCreate() {
        mParkingHelper = new HotspotSQLiteHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor result = null;
        switch (uriMatcher.match(uri)) {
            case PARKING_TABLE: {
                SQLiteDatabase database = mParkingHelper.getReadableDatabase();
                result = database.query(HotspotSQLiteHelper.TABLE_PARKING_LIST, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case USER_TABLE: {
                SQLiteDatabase database = mParkingHelper.getReadableDatabase();
                result = database.query(HotspotSQLiteHelper.TABLE_USER_LIST, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
        }
        return result;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return String.valueOf(uriMatcher.match(uri));
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case PARKING_TABLE: {
                SQLiteDatabase database = mParkingHelper.getWritableDatabase();
                long newId = database.insert(HotspotSQLiteHelper.TABLE_PARKING_LIST, null, values);

                return uri.parse(PARKING_TABLE_URI.toString() + "/?newid=" + newId);
            }
            case USER_TABLE: {
                SQLiteDatabase database = mParkingHelper.getWritableDatabase();
                long newId = database.insert(HotspotSQLiteHelper.TABLE_USER_LIST, null, values);

                return uri.parse(USER_TABLE_URI.toString() + "/?newid=" + newId);
            }
            default:
                return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case PARKING_TABLE: {
                SQLiteDatabase database = mParkingHelper.getWritableDatabase();
                int deleted = database.delete(HotspotSQLiteHelper.TABLE_PARKING_LIST, selection, selectionArgs);

                return deleted;

            }
            case USER_TABLE: {
                SQLiteDatabase database = mParkingHelper.getWritableDatabase();
                int deleted = database.delete(HotspotSQLiteHelper.TABLE_USER_LIST, selection, selectionArgs);

                return deleted;

            }

        }
        return -1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = -1;
        switch (uriMatcher.match(uri)) {
            case PARKING_TABLE: {
                SQLiteDatabase database = mParkingHelper.getWritableDatabase();
                count = database.update(HotspotSQLiteHelper.TABLE_PARKING_LIST, values, selection, selectionArgs);

                break;
            }
            case USER_TABLE: {
                SQLiteDatabase database = mParkingHelper.getWritableDatabase();
                count = database.update(HotspotSQLiteHelper.TABLE_USER_LIST, values, selection, selectionArgs);

                break;
            }
        }
        return count;
    }
}
