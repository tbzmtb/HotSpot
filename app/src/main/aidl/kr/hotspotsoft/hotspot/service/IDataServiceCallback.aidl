// IDataServiceCallback.aidl
package kr.hotspotsoft.hotspot.service;

interface IDataServiceCallback {
	oneway void valueChanged(int what, String data);
}
