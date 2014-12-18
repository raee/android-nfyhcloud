package cn.rui.framework.utils;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class BaiduLocation {
	private static final String		TAG				= "BaiduLocation";
	private static final String		BAIDU_KEY		= "1venUaFhMfvFqGkEDiNzVhW7";
	public LocationClient			mLocationClient	= null;
	private MyLocationListenner		myListener		= new MyLocationListenner();
	private IBaiduLocationResult	resultCallback;
	private BaiduMap				mBaiduMap;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            当前对象
	 * @param resultCallback
	 *            定位成功后调用的接口
	 */
	public BaiduLocation(Context context, IBaiduLocationResult resultCallback) {
		mLocationClient = new LocationClient(context);
		mLocationClient.setAK(BAIDU_KEY);
		mLocationClient.registerLocationListener(myListener);
		setLocationOption();
		this.resultCallback = resultCallback;
		this.start();
	}

	public void setFromBaiduMap(BaiduMap map) {
		this.mBaiduMap = map;
		mBaiduMap.setMyLocationEnabled(true);// 开启定位图层
	}

	/**
	 * 开始请求位置
	 */
	public void start() {
		mLocationClient.start();
	}

	/**
	 * 请求其他位置信息
	 */
	public void request() {
		this.mLocationClient.requestLocation();
		this.mLocationClient.requestPoi();
	}

	/**
	 * 停止请求
	 */
	public void stop() {
		this.mLocationClient.stop();
	}

	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			int code = location.getLocType();
			Log.i(TAG, "error code:" + code);
			Log.i(TAG, "经度：" + location.getLatitude());
			Log.i(TAG, "纬度：" + location.getLongitude());
			Log.i(TAG, "地址：" + location.getAddrStr());

			/*
			 * 61 ： GPS定位结果 62 ： 扫描整合定位依据失败。此时定位结果无效。 63 ：
			 * 网络异常，没有成功向服务器发起请求。此时定位结果无效。 65 ： 定位缓存的结果。 66 ：
			 * 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果 67 ：
			 * 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果 68 ：
			 * 网络连接失败时，查找本地离线定位时对应的返回结果 161： 表示网络定位结果 162~167： 服务端定位失败。
			 */

			if (code == 63) {
				resultCallback.onReceiveLocationFaild(code, "网络异常，定位失败");
				return;
			}

			if (code > 161) {
				resultCallback.onReceiveLocationFaild(code, "服务器定位失败");
				return;
			}

			if (location == null || resultCallback == null) {
				return;
			}
			else if (location.getLatitude() != Double.MIN_VALUE) {
				if (mBaiduMap != null) {
					location.setLatitude(location.getLatitude() - 0.00050);
					// 构造定位数据
					MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())		// 设置定位数据的精度信息，单位：米
							.latitude(location.getLatitude()).longitude(location.getLongitude()).direction(100) // 设置定位数据的方向信息
							.build();
					mBaiduMap.setMyLocationData(locData);

					// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
					MyLocationConfiguration config = new MyLocationConfiguration(LocationMode.NORMAL, true, null);
					mBaiduMap.setMyLocationConfigeration(config);
					LatLng lat = new LatLng(location.getLatitude(), location.getLongitude());
					MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(lat, 19);
					mBaiduMap.animateMapStatus(update);
					mLocationClient.stop();
				}

				resultCallback.onReceiveLocationSuccess(location);
			}
			else {
				resultCallback.onReceiveLocationFaild(code, "定位失败，未知异常:" + code);
			}

		}

		@Override
		public void onReceivePoi(BDLocation location) {
			if (location == null || resultCallback == null) {
				return;
			}
			else if (location.getLatitude() != Double.MIN_VALUE) {
				resultCallback.onReceiveLocationSuccess(location);
				Log.v("cr", "poi:");
			}
		}
	}

	// 设置相关参数
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		// option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
		option.setPriority(LocationClientOption.GpsFirst); // 不设置，默认是gps优先
		mLocationClient.setLocOption(option);
	}

}