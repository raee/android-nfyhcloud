package com.yixin.nfyh.cloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.service.CoreServerBinder;
import com.yixin.nfyh.cloud.ui.ActionbarUtil;
import com.yixin.nfyh.cloud.ui.DeviceConnectView;

/**
 * @author MrChenrui
 * 
 */
public class ReceiveDataActivity extends BaseActivity
{
	private DeviceConnectView	deviceConnectView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);// 设置ActionBar 浮动到view 上层来  
		super.onCreate(savedInstanceState);
		
		ActionbarUtil.setBackgroud(this, getActionBar(),
				R.drawable.actionbar_split);
		
		setContentView(R.layout.activity_device_recevie);
		this.deviceConnectView = (DeviceConnectView) findViewById(R.id.view_connect_device);
		loadState();
	}
	
	private void loadState()
	{
		if (getIntent() == null) return;
		if (getIntent().getExtras() == null) return;
		Bundle extra = getIntent().getExtras();
		String msg = extra.getString(Intent.EXTRA_TEXT);
		String action = getIntent().getAction();
		
		if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_CONNECTED.equals(action)) // 已经连接
		{
			deviceConnectView.loadDeviceConnected();
		}
		else if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_CONNECTING
				.equals(action)) // 连接中
		{
			deviceConnectView.loadDeviceConnecting();
		}
		else if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_DISCONNECTED
				.equals(action)) //断开连接
		{
			deviceConnectView.loadDeviceNotFoundView();
			deviceConnectView.setTips("设备已经断开连接！");
		}
		else if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_FAILD.equals(action)) // 连接失败
		{
			deviceConnectView.loadDeviceNotFoundView();
			deviceConnectView.setTips(msg);
		}
		else if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_RECEVICED
				.equals(action)) // 接收到数据
		{
			this.finish();
		}
		else if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_RECEVICEING
				.equals(action))// 接收数据中
		{
			deviceConnectView.loadDeviceReceiveData(80);
			deviceConnectView.setTips("正在接收：80%");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v)
	{
	}
}
