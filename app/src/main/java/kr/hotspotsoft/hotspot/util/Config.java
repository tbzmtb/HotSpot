package kr.hotspotsoft.hotspot.util;

import android.content.Context;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import java.util.Calendar;

/**
 * Created by tbzm on 16. 4. 18.
 */
public class Config {
    public static final String SERVER_POST_URL = "http://52.198.4.175/manager/";

    public static final String GET_HOT_SPOT_IMAGE_DATA_POST_PHP = "get_hot_spot_image_data_post.php";
    public static final String GET_HOT_SPOT_DETAIL_DATA_POST_PHP = "get_hot_spot_detail_data_post.php";
    public static final String GET_CATEGORY_DATA_POST_PHP = "get_category_data_post.php";
    public static final String GET_HOT_SPOT_DATA_POST_PHP = "get_hot_spot_data_post.php";
    public static final String GET_HOT_SPACE_DATA_POST_PHP = "get_hot_space_data_post.php";

    public static final String TYPE_SELECTED_NONE = "0";
    public static final String TYPE_SELECTED_ENABLE = "1";
    public static final String TIME_SELECTED = "0";
    public static final String DAY_SELECTED = "1";

    public static final String PARAM_CATEGORY_NAME = "category_name";
    public static final String PARAM_CATEGORY_TYPE = "category_type";
    public static final String PARAM_IMAGE_PATH = "image_path";
    public static final String PARAM_BANK_ACCOUNT = "bank_account";
    public static final String PARAM_BANK_NAME = "bank_name";
    public static final String PARAM_INTRODUCE = "introduce";
    public static final String PARAM_SHORT_INTRODUCE = "short_introduce";
    public static final String PARAM_AVAILABLE_TIME = "available_time";
    public static final String PARAM_HOLIDAYS = "holidays";
    public static final String PARAM_FACILITY_INFO = "facility_info";
    public static final String PARAM_WEBSITE = "website";
    public static final String PARAM_ADDRESS = "address";
    public static final String PARAM_CAUTIONS = "cautions";
    public static final String PARAM_TEL_NUMBER = "tel_number";
    public static final String PARAM_SPOT_NAME = "spot_name";
    public static final String PARAM_OWNER_NAME = "owner_name";
    public static final String PARAM_START_INDEX = "sindex";
    public static final String PARAM_END_INDEX = "eindex";
    public static final String PARAM_EQUALS = "=";
    public static final String PARAM_AND = "&";
    public static final String PARAM_AVAILABLE_STAFF = "available_staff";

    public static final String PARAM_IMAGE_00 = "img0";
    public static final String PARAM_IMAGE_01 = "img1";
    public static final String PARAM_IMAGE_02 = "img2";
    public static final String PARAM_IMAGE_03 = "img3";
    public static final String PARAM_IMAGE_04 = "img4";
    public static final String PARAM_TIME_FEE = "time_fee";
    public static final String PARAM_DAY_FEE = "day_fee";
    public static final String PARAM_HOUR_FEE = "hour_fee";
    public static final String PARAM_SPOT_SIZE = "spot_size";
    public static final String PARAM_ENABlE = "enable";
    public static final String PARAM_INFO = "info";
    public static final String PARAM_REGIST_DATE = "regist_date";
    public static final String PARAM_LOCATION = "location";

    public static final String PARAM_SPACE_NAME = "space_name";
    public static final String PARAM_SPACE_TYPE = "space_type";
    public static final String PARAM_SPACE_AVAILABLE_TIME = "space_available_time";
    public static final String PARAM_SPACE_AVAILABLE_PEOPLE = "space_available_people";
    public static final String PARAM_SPACE_INFO = "space_info";
    public static final String PARAM_WIFI_ENABLE = "wifi_enable";
    public static final String WIFI_ENABLE = "1";
    public static final String PARAM_PARKING_ENABLE = "parking_enable";
    public static final String PARKING_ENABLE = "1";

    public static final int DATA = 1000;
    public static final int HOTSPOT_DETAIL_DATA_HANDLER = DATA * 0x01;
    public static final int HOTSPOT_DATA_HANDLER = DATA * 0x02;
    public static final int CATEGORY_DATA_HANDLER = DATA * 0x03;
    public static final int HOTSPOT_IMAGE_DATA_HANDLER = DATA * 0x04;
    public static final int SPACE_DATA_HANDLER = DATA * 0x05;
    private static int sScreenWidthDP = -1;
    private static int sScreenWidth = -1;
    private static int sScreenHeight = -1;

    public static final String PREF_LOGIN_KEY = "login_id_key";
    public static final String PREF_LOGIN_NICK_NAME_KEY = "pass_wordk_key";
    public static final String PREF_GWIDX_KEY = "gwidx_key";
    public static final String PREF_THUMBNAIL_IMAGE_KEY = "push_notification_key";

    public static final String INTENT_CATEGORY_DATA = "INTENT_CATEGORY_DATA";
    public static final String INTENT_HOT_SPOT_DATA = "INTENT_HOT_SPOT_DATA";
    public static final String INTENT_HOT_SPOT_IMAGE_SLIDE_DATA = "INTENT_HOT_SPOT_IMAGE_SLIDE_DATA";
    public static final String INTENT_HOT_SPACE_IMAGE_SLIDE_DATA = "INTENT_HOT_SPACE_IMAGE_SLIDE_DATA";
    public static final String INTENT_HOT_SPOT_SINGLE_IMAGE_DATA = "INTENT_HOT_SPOT_SINGLE_IMAGE_DATA";
    public static final String GCM_SEND_KEY = "com.matescorp.parkinggo.gcmsend";

    public static int getScreenHeight(Context context) {
        if (sScreenHeight == -1) {
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            sScreenHeight = size.y;
        }
        return sScreenHeight;
    }

    public static int getScreenHeightInDp(Context context) {
        if (sScreenHeight == -1) {
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            sScreenHeight = size.y;
        }
        return pixelsToDp(context, sScreenHeight);
    }

    public static int getScreenWidthInDp(Context context) {
        if (sScreenWidthDP == -1) {
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            sScreenWidthDP = pixelsToDp(context, size.x);
        }
        return sScreenWidthDP;
    }

    public static int getScreenWidth(Context context) {
        if (sScreenWidth == -1) {
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            sScreenWidth = size.x;
        }

        return sScreenWidth;
    }

    public static float dpToPixels(Context context, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int pixelsToDp(Context context, float pixels) {
        float density = context.getResources().getDisplayMetrics().densityDpi;
        return Math.round(pixels / (density / 160f));
    }


    public static String milliSecond2Time(String milliSecond) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(milliSecond));
        int hr = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        String s_hr = String.valueOf(hr);
        String s_min = String.valueOf(min);
        if (hr < 10) {
            s_hr = "0" + hr;
        }
        if (min < 10) {
            s_min = "0" + min;
        }
        return s_hr + ":" + s_min;
    }

    public static String milliSecond2HeaderTime(String milliSecond) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(milliSecond));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String s_year = String.valueOf(year);
        String s_month = String.valueOf(month);
        String s_day = String.valueOf(day);
        if (month < 10) {
            s_month = "0" + month;
        }
        return s_year + "/" + s_month+"/"+s_day;
    }


    public static String milliSecond2Time(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        int hr = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        String s_hr = String.valueOf(hr);
        String s_min = String.valueOf(min);
        if (hr < 10) {
            s_hr = "0" + hr;
        }
        if (min < 10) {
            s_min = "0" + min;
        }
        return s_hr + ":" + s_min;
    }
}
