package com.yixin.nfyh.cloud.activity;

import java.sql.SQLException;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.v;
import com.yixin.monitors.sdk.api.ApiMonitor;
import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.ConfigServer;
import com.yixin.nfyh.cloud.data.ISignDevice;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.device.DefaultDevice;
import com.yixin.nfyh.cloud.device.DeviceException;
import com.yixin.nfyh.cloud.model.Devices;
import com.yixin.nfyh.cloud.utils.ReflectUtil;

/**
 * 
 * 设置-监测设备切换界面
 * 
 * @author MrChenrui
 * 
 */
public class SettingDeviceActivity extends BaseActivity {
	private GridView				gvDevices;
	private GridViewDevicesAdapter	gvAdapter;
	private ISignDevice				apiDevices;
	private NfyhApplication			app;
	private TextView				tvDeviceInfo;
	private ConfigServer			config;
	private int						currentSelectPosion	= -1;
	
	void show(Object msg) {
		Log.i("bbbb", msg.toString());
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_device);
		
		// 获取设备
		try {
			app = (NfyhApplication) getApplication();
			config = new ConfigServer(this);
			tvDeviceInfo = (TextView) findViewById(R.id.tv_setting_device_info);
			this.gvDevices = (GridView) findViewById(R.id.gv_setting_devices);
			apiDevices = NfyhCloudDataFactory.getFactory(this).getSignDevice();
			this.gvAdapter = new GridViewDevicesAdapter(apiDevices.getDevices());
			this.gvDevices.setOnItemClickListener(gvAdapter);
			this.gvDevices.setAdapter(gvAdapter);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onClick(View arg0) {
	}
	
	@Override
	protected String getActivityName() {
		return getString(R.string.activity_setting_device);
	}
	
	//	@Override
	//	public void onWindowFocusChanged(boolean hasFocus) {
	//		if (hasFocus) {
	//			gvAdapter.setSelectItem(currentSelectPosion);
	//		}
	//		super.onWindowFocusChanged(hasFocus);
	//	}
	
	private class GridViewDevicesAdapter extends BaseAdapter implements OnItemClickListener {
		private List<Devices>	devices;
		
		public GridViewDevicesAdapter(List<Devices> devices) {
			super();
			this.devices = devices;
		}
		
		@Override
		public int getCount() {
			return devices.size();
		}
		
		@Override
		public Object getItem(int position) {
			return devices.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			Devices m = devices.get(position);
			int resid = ReflectUtil.getDrawableId(R.drawable.class, m.getLogo());
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.view_device_item, null);
				// 默认设备
				if (m.getIsUsed() == 1) {
					tvDeviceInfo.setText(m.getComment());
					currentSelectPosion = position;
					view.setBackgroundResource(R.drawable.btn_big_normal_selected);
					// gvDevices.requestFocusFromTouch();
					// gvDevices.setSelection(position);
				}
			}
			
			int pading = 50;
			view.setPadding(pading, 50, pading, 50);
			ImageView imgLogo = (ImageView) view.findViewById(R.id.img_view_device_logo);
			TextView tvName = (TextView) view.findViewById(R.id.tv_view_device_name);
			
			imgLogo.setBackgroundResource(resid);
			
			tvName.setText(m.getName());
			
			return view;
		}
		
		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
			app.disconnect();
			
			// 设置选择状态
			for (int i = 0; i < adapter.getCount(); i++) {
				View view = gvDevices.getChildAt(i);
				if (i == position) {
					view.setBackgroundResource(R.drawable.btn_big_normal_selected);
				}
				else {
					view.setBackgroundResource(R.drawable.btn_big_normal_disable);
				}
				int pading = 30;
				view.setPadding(pading, 50, pading, 50);
			}
			
			Devices model = devices.get(position);
			//v.setSelected(true);
			//setSelectItem(position);
			config.setDefaultDevice(model.getDevId());
			tvDeviceInfo.setText(model.getComment());
			app.connect();
		}
		
		/**
		 * 设置选择的Item
		 * 
		 * @param position
		 */
		//		public void setSelectItem(int position) {
		//			gvDevices.requestFocusFromTouch(); // 获取焦点
		//			gvDevices.setSelection(position); // 设置选择状态
		//		}
		
	}
}