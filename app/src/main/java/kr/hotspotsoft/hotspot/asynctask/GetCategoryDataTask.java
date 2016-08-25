package kr.hotspotsoft.hotspot.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import kr.hotspotsoft.hotspot.util.Config;
import kr.hotspotsoft.hotspot.util.Logger;


/**
 * Created by tbzm on 16. 5. 11.
 */
public class GetCategoryDataTask extends AsyncTask<String, Void, String> {
    private final String TAG = getClass().getName();
    private Context mContext;
    private Handler mHandler;

    public GetCategoryDataTask(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
    }

    @Override
    protected String doInBackground(String... args) {
        String returnValue = null;
        try {
            String urlString = Config.SERVER_POST_URL + Config.GET_CATEGORY_DATA_POST_PHP;
            Logger.d(TAG, "urlString = " + urlString);
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            conn.setReadTimeout(20000);
            conn.setRequestMethod("POST");
//            StringBuffer params = new StringBuffer("");
//            params.append(Config.PARAM_RTCID + Config.PARAM_EQUALS + DataPreference.getPeerRtcid());
            PrintWriter output = new PrintWriter(conn.getOutputStream());
//            output.print(params.toString());
            output.close();

            // Response받기
            StringBuffer sb = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            for (; ; ) {
                String line = br.readLine();
                if (line == null) break;
                sb.append(line + "\n");
            }

            br.close();
            conn.disconnect();

            returnValue = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null && mContext != null) {
            Logger.d(TAG, "result = " + s.trim());
            Message msg = new Message();
            msg.obj = s.trim();
            msg.what = Config.CATEGORY_DATA_HANDLER;
            mHandler.sendMessage(msg);
        }
    }
}
