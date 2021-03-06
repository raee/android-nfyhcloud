package com.yixin.nfyh.cloud.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import cn.rui.framework.utils.InputUtils;
import cn.rui.framework.widget.RuiSwitch;
import cn.rui.framework.widget.RuiSwitch.OnCheckedChangeListener;

import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.ConfigServer;
import com.yixin.nfyh.cloud.server.FallServer;
import com.yixin.nfyh.cloud.server.IFallCallback;
import com.yixin.nfyh.cloud.ui.TopMsgView;

/**
 * 设置-跌倒设备绑定界面
 * 
 * @author MrChenrui
 * 
 */
public class SettingFallDeviceActivity extends BaseActivity implements
		OnCheckedChangeListener, TextWatcher, IFallCallback
{
	private Button			btnSend;
	private RuiSwitch		swFall;
	private TextView		etNumber;
	private FallServer		fallServer;
	private ConfigServer	config;
	
	private Handler			handler;
	private View			viewContent;
	private ViewGroup		rootView;
	private TopMsgView		msgView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		rootView = (ViewGroup) getLayoutInflater().inflate(
				R.layout.activity_setting_fall, null);
		setContentView(rootView);
		
		fallServer = new FallServer(this, this);
		config = new ConfigServer(this);
		
		msgView= new TopMsgView(this, null);
		
		swFall = (RuiSwitch) findViewById(R.id.sw_setting_fall);
		etNumber = (TextView) findViewById(R.id.tv_setting_fall_number);
		btnSend = (Button) findViewById(R.id.btn_setting_fall_send);
		viewContent = findViewById(R.id.ll_setting_fall_content);
		
		boolean isChecked = config
				.getBooleanConfig(ConfigServer.KEY_ENABLE_FALL);
		swFall.setChecked(isChecked);
		viewContent.setVisibility(isChecked ? View.VISIBLE : View.GONE);
		etNumber.setText(config.getConfig(ConfigServer.KEY_FALL_BIND_NUMBER));
		btnSend.setEnabled(InputUtils.isPhoneNumber(etNumber.getText()
				.toString().trim()));
		
		swFall.setOnCheckedChangeListener(this);
		etNumber.addTextChangedListener(this);
		btnSend.setOnClickListener(this);
		
		handler = new Handler(new Handler.Callback()
		{
			
			@Override
			public boolean handleMessage(Message msg)
			{
				
				switch (msg.what)
				{
					case 0:
						if (!btnSend.isEnabled())
						{
							btnSend.setText(msg.obj + "秒后，重新绑定");
						}
						break;
					case 1:
						btnSend.setEnabled(true);
						btnSend.setText("重新绑定");
						msgView.setVisibility(View.GONE);
						break;
					default:
						break;
				}
				return false;
			}
		});
		
	}
	
	@Override
	protected String getActivityName()
	{
		return getString(R.string.activity_setting_fall);
	}
	
	@Override
	public void onClick(View v)
	{
		String number = etNumber.getText().toString().trim();
		
		if (InputUtils.isPhoneNumber(number))
		{
			fallServer.bind(number);
			config.setConfig(ConfigServer.KEY_FALL_BIND_NUMBER, number); //保存到数据库中
			btnSend.setText("正在绑定...");
			btnSend.setEnabled(false);
			
			msgView.setMsg("请注意：有没有收到信息？！没有则需要重新绑定。");
			msgView.setIcon(R.drawable.icon_write_alert);
			msgView.show((ViewGroup) this.rootView.getChildAt(0));
			
			// 30秒超时返回
			new Timer().schedule(new TimerTask()
			{
				private int	time	= 30;
				
				@Override
				public void run()
				{
					if (time <= 0)
					{
						this.cancel();
						Message.obtain(handler, 1, time).sendToTarget();
						return;
					}
					
					time--;
					Message.obtain(handler, 0, time).sendToTarget();
				}
			}, 0, 1000);
		}
		else
		{
			TopMsgView msg = new TopMsgView(this, null);
			msg.setBackgroundColor(getResources().getColor(R.color.yellow));
			msg.setMsg("手机号码不正确");
		}
		
	}
	
	@Override
	public void onCheckedChanged(RuiSwitch switchView, boolean isChecked)
	{
		config.enableFall(isChecked);
		viewContent.setVisibility(isChecked ? View.VISIBLE : View.GONE);
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after)
	{
		
	}
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		String number = s.toString().trim();
		if (InputUtils.isPhoneNumber(number))
		{
			btnSend.setEnabled(true);
		}
		else
		{
			btnSend.setEnabled(false);
		}
	}
	
	@Override
	public void afterTextChanged(Editable s)
	{
		
	}
	
	@Override
	public void onBindSuccess(String msg)
	{
		btnSend.setText(msg);
		btnSend.setEnabled(true);
	}
	
	@Override
	public void onBindError(String msg)
	{
		btnSend.setText(msg);
		btnSend.setEnabled(true);
	}
}
