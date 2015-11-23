package com.yixin.nfyh.cloud.activity;

import java.sql.SQLException;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cn.rui.framework.ui.WebViewerActivity;
import cn.rui.framework.utils.AppInfo;
import cn.rui.framework.widget.RuiSwitch;
import cn.rui.framework.widget.RuiSwitch.OnCheckedChangeListener;

import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.LoginActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.ConfigServer;
import com.yixin.nfyh.cloud.bll.VersionUpdate;
import com.yixin.nfyh.cloud.data.ISignDevice;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.model.Devices;
import com.yixin.nfyh.cloud.service.CoreBinder;
import com.yixin.nfyh.cloud.service.CoreService;

public class UserSettingActivity extends BaseActivity implements OnCheckedChangeListener
{
	private Button				btnLogout;
	// private TextView tvUserName;
	// private SubMenu menuCheckVersion;
	// private CheckVersionService versionService;
	// private BroadcastReceiver receiver;
	private TextView			tvAppVersion;
	private ConfigServer		config;
	private ISignDevice			apiDevice;
	private TextView			tvDeviceName;
	private RuiSwitch			swAutoDevice;
	private RuiSwitch			swAutoTips;
	private RuiSwitch			swDesktop;
	private RuiSwitch			swFall;
	private RuiSwitch			swPullMsg;
	private RuiSwitch			swYuanhou;
	private VersionUpdate		mVersionUpdate;
	private CoreBinder			mBinder;
	private ServiceConnection	mConnection;
	private RuiSwitch			swAutoUpload;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_main);
		config = new ConfigServer(this);
		this.apiDevice = NfyhCloudDataFactory.getFactory(this).getSignDevice();
		findView();
		initConfig();
		setLinsener();
		String type = getIntent().getStringExtra("type");
		
		// 版本检测
		if (type != null && "versionupdate".equals(type))
		{
			this.updateVersion();
		}
		
		this.mConnection = new ServiceConnection()
		{
			
			@Override
			public void onServiceDisconnected(ComponentName name)
			{
				
			}
			
			@Override
			public void onServiceConnected(ComponentName name, IBinder service)
			{
				if (service != null)
				{
					mBinder = (CoreBinder) service;
				}
			}
		};
		bindService(new Intent(this, CoreService.class), mConnection, Context.BIND_AUTO_CREATE);
	}
	
	/**
	 * 初始化默认配置
	 */
	private void initConfig()
	{
		this.swDesktop.setChecked(config.getBooleanConfig(ConfigServer.KEY_ENABLE_DESKTOP));
		this.swFall.setChecked(config.getBooleanConfig(ConfigServer.KEY_ENABLE_FALL));
		this.swPullMsg.setChecked(config.getBooleanConfig(ConfigServer.KEY_ENABLE_PULLMSG));
		this.swYuanhou.setChecked(config.getBooleanConfig(ConfigServer.KEY_ENABLE_TIXING));
		this.swAutoDevice.setChecked(config.getBooleanConfig(ConfigServer.KEY_AUTO_CONNECTED));
		this.swAutoTips.setChecked(config.getBooleanConfig(ConfigServer.KEY_AUTO_TIPS));
		
		this.swAutoUpload.setChecked(config.getBooleanConfig(ConfigServer.KEY_AUTO_UPLOAD)); // 自动上传
		
		Devices device;
		try
		{
			device = apiDevice.getCurrentDevices();
			String deviceName = device == null ? "获取设备失败" : device.getName();
			tvDeviceName.setText(deviceName);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent();
		
		switch (v.getId())
		{
		
			case R.id.ll_setting_pullmsg:// 开启消息推送
				swPullMsg.setChecked(!swPullMsg.isChecked());
				break;
			case R.id.ll_setting_yuanhou:// 开启院后提醒
				swYuanhou.setChecked(!swYuanhou.isChecked());
				break;
			case R.id.ll_setting_autodevice:// 设备
				swAutoDevice.setChecked(!swAutoDevice.isChecked());
				break;
			case R.id.ll_setting_tips:// 告警
				swAutoTips.setChecked(!swAutoTips.isChecked());
				break;
			case R.id.ll_setting_device:// 监测设备选择
				intent.setClass(this, SettingDeviceActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_setting_pall:// 跌倒设置
				intent.setClass(this, SettingFallDeviceActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_setting_autoupload: // 自动上传
				swAutoUpload.setChecked(!swAutoUpload.isChecked());
				break;
			case R.id.ll_setting_desktop:// 桌面呼救
				intent.setClass(this, SettingDesktopActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_setting_version: // 版本更新
				updateVersion();
				break;
			case R.id.ll_menu_yjfk: // 意见反馈
				Intent feedbackIntent = new Intent(this, WebViewerActivity.class);
				Uri feedbackuri = Uri.parse(getString(R.string.url_yjfk));
				feedbackIntent.setData(feedbackuri);
				feedbackIntent.putExtra(WebViewerActivity.EXTRA_COOKIE, app.getGlobalsetting().getUser().getCookie());
				startActivity(feedbackIntent);
				break;
			case R.id.btn_exit:
				logout();
				break;
			default:
				break;
		}
	}
	
	private void logout()
	{
		getNfyhApplication().exit();
		getNfyhApplication().logout();
		
		Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtra(Intent.EXTRA_TEXT, true);
		startActivity(intent);
		this.finish();
	}
	
	@Override
	protected void findView()
	{
		swDesktop = ((RuiSwitch) findViewById(R.id.sw_setting_desktop));
		swFall = ((RuiSwitch) findViewById(R.id.sw_setting_pall));
		swPullMsg = ((RuiSwitch) findViewById(R.id.sw_setting_pullmsg));
		swYuanhou = ((RuiSwitch) findViewById(R.id.sw_setting_yuanhou));
		swAutoDevice = (RuiSwitch) findViewById(R.id.sw_setting_autodevice);
		swAutoTips = (RuiSwitch) findViewById(R.id.sw_setting_tips);
		swAutoUpload = (RuiSwitch) findViewById(R.id.sw_setting_autoupload);
		
		this.btnLogout = (Button) findViewById(R.id.btn_exit);
		tvAppVersion = (TextView) findViewById(R.id.tv_setting_app_version);
		AppInfo appinfo = new AppInfo(this);
		tvAppVersion.setText("V" + appinfo.getVersion());
		tvDeviceName = (TextView) findViewById(R.id.tv_setting_device_name);
	}
	
	@Override
	protected void setLinsener()
	{
		this.btnLogout.setOnClickListener(this);
		findViewById(R.id.ll_setting_desktop).setOnClickListener(this);
		findViewById(R.id.ll_setting_device).setOnClickListener(this);
		findViewById(R.id.ll_setting_pall).setOnClickListener(this);
		findViewById(R.id.ll_setting_pullmsg).setOnClickListener(this);
		findViewById(R.id.ll_setting_yuanhou).setOnClickListener(this);
		findViewById(R.id.ll_setting_version).setOnClickListener(this);
		findViewById(R.id.ll_setting_autodevice).setOnClickListener(this);
		findViewById(R.id.ll_setting_tips).setOnClickListener(this);
		findViewById(R.id.ll_setting_version).setOnClickListener(this);
		findViewById(R.id.ll_setting_autoupload).setOnClickListener(this);
		findViewById(R.id.ll_menu_yjfk).setOnClickListener(this);
		
		swDesktop.setOnCheckedChangeListener(this);
		swFall.setOnCheckedChangeListener(this);
		swPullMsg.setOnCheckedChangeListener(this);
		swYuanhou.setOnCheckedChangeListener(this);
		swAutoDevice.setOnCheckedChangeListener(this);
		swAutoTips.setOnCheckedChangeListener(this);
		swAutoUpload.setOnCheckedChangeListener(this);
		
	}
	
	@Override
	protected String getActivityName()
	{
		return getString(R.string.grsz);
	}
	
	@Override
	protected void onRestart()
	{
		initConfig();
		super.onRestart();
	}
	
	@Override
	public void onCheckedChanged(RuiSwitch switchView, boolean isChecked)
	{
		switch (switchView.getId())
		{
			case R.id.sw_setting_pall:// 开启跌倒监测
				config.enableFall(isChecked);
				break;
			case R.id.sw_setting_pullmsg:
				config.enablePullMsg(isChecked);
				break;
			case R.id.sw_setting_yuanhou:
				config.enableTixing(isChecked);
				break;
			case R.id.sw_setting_desktop:// 开启桌面呼救
				config.enableDesktop(isChecked);
				break;
			case R.id.sw_setting_autodevice:
				config.enavleAutoconnect(isChecked);
				break;
			case R.id.sw_setting_tips:
				config.enableAutoTips(isChecked);
				break;
			case R.id.sw_setting_autoupload:
				config.enableAutoUpload(isChecked);
				break;
			default:
				break;
		}
	}
	
	private void updateVersion()
	{
		if (mVersionUpdate == null)
		{
			mVersionUpdate = new VersionUpdate(this);
		}
		mVersionUpdate.check();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (config.getBooleanConfig(ConfigServer.KEY_ENABLE_PULLMSG))
		{
			mBinder.startPullMessage();
		}
		else
		{
			mBinder.stopPullMessage();
		}
		unbindService(mConnection);
	}
	
}
