package com.yixin.nfyh.cloud;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import cn.rui.framework.utils.CommonUtil;

import com.yixin.nfyh.cloud.bll.GlobalSetting;
import com.yixin.nfyh.cloud.device.DefaultDevice;
import com.yixin.nfyh.cloud.device.DeviceReceiverListener;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.ui.ActionbarUtil;
import com.yixin.nfyh.cloud.ui.TimerProgressDialog;
import com.yixin.nfyh.cloud.ui.TimerToast;

public abstract class BaseActivity extends Activity implements OnClickListener
{
	
	protected NfyhApplication		app;
	private Users					mUser;
	private ProgressDialog			progressDialog;
	
	private GlobalSetting			setting;
	private DeviceReceiverListener	mDeviceReceiverListener;
	
	protected void findView()
	{
	}
	
	public Users getUser()
	{
		if (mUser == null) mUser = app.getCurrentUser();
		return mUser;
	}
	
	// 显示返回键
	protected void showHomeAsUp()
	{
		if (getActionBar() != null)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	/**
	 * 获取当前Activity的名称
	 * 
	 * @return
	 */
	protected String getActivityName()
	{
		return "";
	}
	
	public GlobalSetting getSetting()
	{
		if (setting == null) setting = new GlobalSetting(this);
		return setting;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ActionbarUtil.setActionbar(this, getActionBar());
		ActionbarUtil.setTitleAsUpHome(this, getActionBar(), getActivityName());
		app = (NfyhApplication) getApplication();
		mDeviceReceiverListener = new DeviceReceiverListener(this, app.getApiMonitor());
		
		app.setBluetoothListener(mDeviceReceiverListener);
		app.addActivity(this);
	}
	
	protected void setLinsener()
	{
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_main, menu);
		boolean result = super.onCreateOptionsMenu(menu);
		return result;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus)
		{
			View actionView = findViewById(R.id.menu_main_device);
			
			if (app.isConnected() && actionView != null)
			{
				CommonUtil.setActionViewItemIcon(actionView, R.drawable.ic_switch_device_connected);
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		switch (id)
		{
			case android.R.id.home:
				this.finish();
				break;
			case R.id.menu_main_device:
				DefaultDevice.connect(this, app.getApiMonitor());
				break;
			default:
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public NfyhApplication getNfyhApplication()
	{
		return (NfyhApplication) getApplication();
	}
	
	/**
	 * 显示土司信息
	 * 
	 * @param msg
	 */
	public void showMsg(String msg)
	{
		if (msg == null) msg = "";
		TimerToast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onClick(View v)
	{
	}
	
	@Override
	public void setTitle(CharSequence title)
	{
		if (getActionBar() != null)
		{
			getActionBar().setTitle(title);
		}
	}
	
	protected void showProgressDialog(String message)
	{
		if (this.progressDialog == null)
		{
			this.progressDialog = new TimerProgressDialog(this);
			this.progressDialog.setCanceledOnTouchOutside(false); // 点击不取消
		}
		this.progressDialog.setMessage(message);
		this.progressDialog.show();
	}
	
	protected void showProgressDialog()
	{
		this.showProgressDialog("正在获取数据...");
	}
	
	protected void dismissProgressDialog()
	{
		if (this.progressDialog != null && this.progressDialog.isShowing())
		{
			this.progressDialog.dismiss();
		}
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		app.removeActivity(this);
	}
}
