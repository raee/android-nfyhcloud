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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.rui.framework.widget.RuiSwitch;
import cn.rui.framework.widget.RuiSwitch.OnCheckedChangeListener;

import com.yixin.monitors.sdk.api.ApiMonitor;
import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.ConfigServer;
import com.yixin.nfyh.cloud.data.ISignDevice;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.model.Devices;
import com.yixin.nfyh.cloud.utils.ReflectUtil;

/**
 * 
 * 设置-监测设备切换界面
 * 
 * @author MrChenrui
 * 
 */
public class SettingDeviceActivity extends BaseActivity implements OnCheckedChangeListener {
	private GridView				gvDevices;
	private GridViewDevicesAdapter	gvAdapter;
	private ISignDevice				apiDevices;
	private NfyhApplication			app;
	private TextView				tvDeviceInfo;
	private ConfigServer			config;
	private RuiSwitch				swAutoRun;
	private EditText				etDevicePin;
	private EditText				etDeviceName;
	private List<Devices>			devices;
	
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
			gvDevices = (GridView) findViewById(R.id.gv_setting_devices);
			etDeviceName = (EditText) findViewById(R.id.et_setting_device_devicename);
			etDevicePin = (EditText) findViewById(R.id.et_setting_device_pin);
			findViewById(R.id.btn_setting_device_update).setOnClickListener(this);
			
			apiDevices = NfyhCloudDataFactory.getFactory(this).getSignDevice();
			devices = apiDevices.getDevices();
			this.gvAdapter = new GridViewDevicesAdapter(devices);
			this.gvDevices.setOnItemClickListener(gvAdapter);
			this.gvDevices.setAdapter(gvAdapter);
			
			swAutoRun = (RuiSwitch) findViewById(R.id.sw_setting_sign);
			swAutoRun.setOnCheckedChangeListener(this);
			boolean isAutoRun = config.getBooleanConfig(ConfigServer.KEY_ENABLE_AUTO_RUN);
			swAutoRun.setChecked(isAutoRun);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_setting_device_update:
				updateDevice();
				break;
			
			default:
				break;
		}
	}
	
	private void updateDevice() {
		String name = etDeviceName.getText().toString();
		String pin = etDevicePin.getText().toString();
		ApiMonitor api = app.getApiMonitor();
		try {
			Devices device = apiDevices.getCurrentDevices();
			device.setDeviceName(name);
			device.setDevicePin(pin);
			apiDevices.updateDevices(device);
			Toast.makeText(this, "成功更新设备为：" + name, Toast.LENGTH_SHORT).show();
			devices.clear();
			devices = apiDevices.getDevices();
			gvAdapter.notifyDataSetChanged();
		}
		catch (SQLException e) {
			e.printStackTrace();
			Toast.makeText(this, "更新设备错误，数据库发生错误！", Toast.LENGTH_SHORT).show();
		}
		
		if (api != null) {
			api.configDevice(name, pin);
		}
	}
	
	@Override
	protected String getActivityName() {
		return getString(R.string.activity_setting_device);
	}
	
	private class GridViewDevicesAdapter extends BaseAdapter implements OnItemClickListener {
		
		public GridViewDevicesAdapter(List<Devices> devices) {
			super();
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
					etDeviceName.setText(m.getDeviceName());
					etDevicePin.setText(m.getDevicePin());
					view.setBackgroundResource(R.drawable.btn_big_normal_selected);
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
			tvDeviceInfo.setText(model.getComment());
			config.setDefaultDevice(model.getDevId());
			etDeviceName.setText(model.getDeviceName());
			etDevicePin.setText(model.getDevicePin());
			app.connect();
		}
		
	}
	
	@Override
	public void onCheckedChanged(RuiSwitch switchView, boolean isChecked) {
		config.setConfig(ConfigServer.KEY_ENABLE_AUTO_RUN, isChecked + "");
	}
}