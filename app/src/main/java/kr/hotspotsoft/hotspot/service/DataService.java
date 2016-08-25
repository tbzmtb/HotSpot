package kr.hotspotsoft.hotspot.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import kr.hotspotsoft.hotspot.util.Logger;

/**
 * Created by tbzm on 16. 4. 18.
 */
public class DataService extends Service {

    public static final String INTENT_ACTION = "intent.action.bhc.test.service";
    private static final int MSG_WORK = 1;

    final RemoteCallbackList<IDataServiceCallback> callbacks = new RemoteCallbackList();

    private final IDataService.Stub mBinder = new IDataService.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public boolean unregisterCallback(IDataServiceCallback callback)
                throws RemoteException {
            boolean flag = false;

            if (callback != null) {
                flag = callbacks.unregister(callback);
            }

            return flag;
        }

        @Override
        public boolean registerCallback(IDataServiceCallback callback)
                throws RemoteException {


            boolean flag = false;

            if (callback != null) {
                flag = callbacks.register(callback);
            }

            return flag;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;

    }


    @Override
    public void onCreate() {
        Logger.d("Service", "Service is onCreate..");

        handler.sendEmptyMessage(MSG_WORK);

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Logger.d("Service", "Service is onDestory..");

        handler.removeMessages(MSG_WORK);

        super.onDestroy();
    }

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            int N = callbacks.beginBroadcast();
            for (int i = 0; i < N; i++) {
                try {
                    if (msg.obj == null) {
                        msg.obj = "";
                    }
                    IDataServiceCallback cb = callbacks.getBroadcastItem(i);
                    cb.valueChanged(msg.what, msg.obj.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            callbacks.finishBroadcast();

            return false;
        }
    });

}
