package com.yixin.nfyh.cloud;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import cn.rui.framework.ui.RuiDialog;
import cn.rui.framework.utils.CommonUtil;

import com.rae.core.image.loader.ImageLoader;
import com.yixin.nfyh.cloud.bll.GlobalSetting;
import com.yixin.nfyh.cloud.broadcastreceiver.DeviceReceviceBroadcasetreceiver;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.service.CoreServerBinder;
import com.yixin.nfyh.cloud.service.CoreService;
import com.yixin.nfyh.cloud.ui.ActionbarUtil;
import com.yixin.nfyh.cloud.ui.TimerProgressDialog;
import com.yixin.nfyh.cloud.ui.TimerToast;

public abstract class BaseActivity extends Activity implements OnClickListener
{
	
	protected NfyhApplication	app;
	private Users				mUser;
	private ProgressDialog		progressDialog;
	
	protected void findView()
	{
	}
	
	public Users getUser()
	{
		if (mUser == null) mUser = app.getCurrentUser();
		return mUser;
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
	
	private GlobalSetting						setting;
	private DeviceReceviceBroadcasetreceiver	receiver;
	protected CoreServerBinder					binder;
	private boolean								isCreated	= false;
	
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
		
		Intent intent = new Intent(this, CoreService.class);
		startService(intent);
		app = (NfyhApplication) getApplication();
		app.addActivity(this);
		binder = app.getBinder();
		
	}
	
	protected void setLinsener()
	{
	}
	
	@Override
	protected void onDestroy()
	{
		if (receiver != null)
		{
			this.unregisterReceiver(receiver);
		}
		ImageLoader.getInstance().clearMemoryCache();
//		ImageLoader.getInstance().stop();
		super.onDestroy();
		
	}
	
//	@Override
//	protected void onPause()
//	{
////		ImageLoader.getInstance().clearMemoryCache();
//		ImageLoader.getInstance().pause();
//		super.onPause();
//	}
//	
//	@Override
//	protected void onResume()
//	{
//		ImageLoader.getInstance().resume();
//		super.onResume();
//	}
	
	/**
	 * 注册
	 * 
	 * @param context
	 * @param contentView
	 */
	public void registerReceiver(Context context, View contentView,
			View actionView)
	{
		Log.i("tt", "--> 注册广播！");
		receiver = new DeviceReceviceBroadcasetreceiver(context, contentView,
				actionView);
		IntentFilter filter = new IntentFilter();
		filter.addAction(CoreServerBinder.ACTION_BLUETOOTH_DEVICE_CONNECTED);
		filter.addAction(CoreServerBinder.ACTION_BLUETOOTH_DEVICE_CONNECTING);
		filter.addAction(CoreServerBinder.ACTION_BLUETOOTH_DEVICE_DISCONNECTED);
		filter.addAction(CoreServerBinder.ACTION_BLUETOOTH_DEVICE_FAILD);
		filter.addAction(CoreServerBinder.ACTION_BLUETOOTH_DEVICE_RECEVICED);
		filter.addAction(CoreServerBinder.ACTION_BLUETOOTH_DEVICE_RECEVICEING);
		context.registerReceiver(receiver, filter);
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
		if (isCreated || !hasFocus) return;
		init();
	}
	
	private void init()
	{
		View contentView = findViewById(R.id.contentview);
		View actionView = findViewById(R.id.menu_main_device);
		
		if (contentView == null) return;
		if (receiver != null) return; // 已经注册广播
		
		registerReceiver(this, contentView, actionView);
		
		if (binder != null && binder.getDevice().isConnected()
				&& actionView != null)
		{
			CommonUtil.setActionViewItemIcon(actionView,
					R.drawable.ic_switch_device_connected);
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
				if (this.binder.getDevice().isConnected())
				{
					new RuiDialog.Builder(this)
							.buildTitle("断开连接")
							.buildMessage("设备已经连接，是否要断开监测设备？")
							.buildLeftButton("否", null)
							.buildRight("断开",
									new DialogInterface.OnClickListener()
									{
										
										@Override
										public void onClick(
												DialogInterface dialog,
												int which)
										{
											binder.getDevice().disConnect();
											dialog.dismiss();
											
										}
									}).show();
				}
				else
				{
					this.binder.conncet();
				}
				
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
	
	public void exit()
	{
		try
		{
			List<Activity> ats = this.app.getActivitys();
			for (Activity activity : ats)
			{
				activity.finish();
			}
			System.exit(0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View v)
	{
		
	}
	
	@Override
	public void setTitle(CharSequence title)
	{
		getActionBar().setTitle(title);
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
	
}
