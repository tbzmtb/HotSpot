// IDataService.aidl
package kr.hotspotsoft.hotspot.service;

// Declare any non-default types here with import statements

import kr.hotspotsoft.hotspot.service.IDataServiceCallback;

interface IDataService {

    boolean registerCallback(IDataServiceCallback callback);
    boolean unregisterCallback(IDataServiceCallback callback);
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
