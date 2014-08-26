package com.yixin.nfyh.cloud.ui;

import com.yixin.nfyh.cloud.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 设备连接处理类
 * 
 * @author MrChenrui
 * 
 */
public class DeviceConnectView extends LinearLayout
{
	
	/**
	 * 设备没有发现
	 */
	public final static int	STATE_NOTFOUND			= 0;
	
	/**
	 * 设备连接出错
	 */
	public final static int	STATE_CONNECT_FAILD		= 1;
	
	/**
	 * 设备正在连接
	 */
	public final static int	STATE_CONNECTING		= 3;
	
	/**
	 * 设备正在接收数据
	 */
	public final static int	STATE_RECEIVE_DATA		= 5;
	
	/**
	 * 设备连接成功
	 */
	public final static int	STATE_CONNECT_SUCCESS	= 2;
	
	private ImageView		stateIcon, imgSpinner;
	
	private Button			btnConnect;
	
	private TextView		tvTips;
	private ProgressBar		pgReceiveBar;
	private OnClickListener	scanClickListener;				//扫描事件
															
	private Context			context;
	
	private OnClickListener	disconnectedClickListener;
	
	/**
	 * @return the stateIcon
	 */
	public ImageView getStateIcon()
	{
		return stateIcon;
	}
	
	/**
	 * @return the btnConnect
	 */
	public Button getBtnConnect()
	{
		return btnConnect;
	}
	
	/**
	 * @return the tvTips
	 */
	public TextView getTvTips()
	{
		return tvTips;
	}
	
	public DeviceConnectView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
		
		try
		{
			LayoutInflater inflater = LayoutInflater.from(context);
			inflater.inflate(R.layout.ui_device_connect, this); //初始化布局
			this.stateIcon = (ImageView) findViewById(R.id.img_ui_device_connect_icon); //状态图标
			this.tvTips = (TextView) findViewById(R.id.tv_ui_device_connect_tips);//提示信息
			this.btnConnect = (Button) findViewById(R.id.btn_ui_device_connect);//连接按钮
			this.imgSpinner = (ImageView) findViewById(R.id.img_ui_device_connect_spinner); //动画图标
			this.pgReceiveBar = (ProgressBar) findViewById(R.id.pg_ui_device_connect); //进度条
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置设备视图状态，参考静态变量
	 * 
	 * @param state
	 */
	public void setState(int state)
	{
		switch (state)
		{
			case STATE_NOTFOUND:
				loadDeviceNotFoundView();
				break;
			case STATE_CONNECT_FAILD:
				loadDeviceNotFoundView();
				this.setTips("Connect Error!");
				break;
			case STATE_CONNECT_SUCCESS:
				loadDeviceConnected();
				break;
			case STATE_CONNECTING:
				loadDeviceConnecting();
				break;
			case STATE_RECEIVE_DATA:
				loadDeviceReceiveData(50);
				break;
			
			default:
				break;
		}
	}
	
	/**
	 * 初始化视图状态
	 * 
	 * @param stateIcon
	 * @param tipsId
	 * @param btnTextId
	 * @param showButton
	 */
	public void initView(int stateIcon, int tipsId, int btnTextId,
			boolean showButton)
	{
		//停止之前动画
		stopAnim();
		
		this.stateIcon.setImageResource(stateIcon);
		this.setTips(context.getString(tipsId));
		if (R.drawable.ic_bluetooth != stateIcon)
		{
			this.pgReceiveBar.setVisibility(View.GONE);
			this.imgSpinner.setVisibility(View.GONE);
		}
		if (showButton)
		{
			this.btnConnect.setText(context.getString(btnTextId));
			this.btnConnect.setVisibility(View.VISIBLE);
		}
		else
		{
			this.btnConnect.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 加载设备没有连接视图
	 */
	public void loadDeviceNotFoundView()
	{
		initView(R.drawable.ic_diable_connect, R.string.tips_connect_bluetooth,
				R.string.rescan, true);
		this.tvTips.setCompoundDrawablesWithIntrinsicBounds(
				R.drawable.ic_warning, 0, 0, 0);
		//设置按钮事件
		this.btnConnect.setOnClickListener(scanClickListener);
		
	}
	
	/**
	 * 加载设备已经连接
	 */
	public void loadDeviceConnected()
	{
		initView(R.drawable.ic_connected, R.string.tips_connect_success,
				R.string.disconnet_bluetooth, true);
		this.tvTips.setCompoundDrawablesWithIntrinsicBounds(
				R.drawable.ic_success, 0, 0, 0);
		//设置按钮事件
		this.btnConnect.setOnClickListener(disconnectedClickListener);
	}
	
	/**
	 * 加载设备正在连接视图
	 */
	public void loadDeviceConnecting()
	{
		initView(R.drawable.ic_connected, R.string.tips_connecting_bluetooth,
				0, false);
		this.tvTips.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		// 开始旋转动画
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.spinning);
		this.stateIcon.startAnimation(animation);
		
	}
	
	/**
	 * 加载正在接受数据视图
	 * 
	 * @param progress
	 */
	public void loadDeviceReceiveData(int progress)
	{
		initView(R.drawable.ic_bluetooth, R.string.tips_receive_data, 0, false);
		this.tvTips.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		String tips = context.getString(R.string.tips_receive_data) + "("
				+ progress + "%)";
		setTips(tips);
		
		// 设置进度条
		this.pgReceiveBar.setProgress(progress);
		this.pgReceiveBar.setVisibility(View.VISIBLE);
		
		this.imgSpinner.setVisibility(View.VISIBLE);
		
		// 开始旋转动画
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.spinning);
		this.imgSpinner.startAnimation(animation);
		
	}
	
	/**
	 * 设置提示信息
	 * 
	 * @param tips
	 */
	public void setTips(String tips)
	{
		this.tvTips.setText(tips);
		
	}
	
	/**
	 * 停止动画
	 */
	public void stopAnim()
	{
		this.stateIcon.clearAnimation();
		this.imgSpinner.clearAnimation();
	}
	
	/**
	 * 设置扫描按钮监听事件
	 * 
	 * @param listener
	 *            the listener to set
	 */
	public void setScanButtonOnClickListener(OnClickListener listener)
	{
		this.scanClickListener = listener;
	}
	
	/**
	 * 设置断开连接按钮监听事件
	 * 
	 * @param listener
	 *            the listener to set
	 */
	public void setDisconnectButtonOnClickListener(OnClickListener listener)
	{
		this.disconnectedClickListener = listener;
	}
	
}
