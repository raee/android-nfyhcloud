//package com.yixin.nfyh.cloud.ui;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.animation.AnimationUtils;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
///**
// * 设备接收信息提示视图
// * 
// * @author MrChenrui
// * 
// */
//public class DeviceMessageHeadView extends LinearLayout
//{
//
//	public DeviceMessageHeadView(Context context, AttributeSet attrs)
//	{
//		super(context, attrs);
//		this.context = context;
//		LayoutInflater.from(context).inflate(R.layout.ui_device_receive, this);
//		findView();
//	}
//
//	private View		llDev;
//	private View		imgsend;
//	private Context		context;
//	private TextView	tvMsg;
//	private ProgressBar	pbRec;
//	private View		llTips;
//
//	private void findView()
//	{
//		llDev = findViewById(R.id.ll_ui_deveice_receive_dev);
//		llTips = findViewById(R.id.ll_ui_device_receive_tips);
//		tvMsg = (TextView) findViewById(R.id.tv_ui_upload_server_msg);
//		pbRec = (ProgressBar) findViewById(R.id.pb_ui_device_recevie);
//		imgsend = findViewById(R.id.img_ui_device_recevice_send);
//	}
//
//	/**
//	 * @param progress
//	 *            接收进度
//	 */
//	public void showConnect(int progress)
//	{
//		llTips.setVisibility(View.GONE);
//		llDev.setVisibility(View.VISIBLE);
//		tvMsg.setVisibility(View.VISIBLE);
//		pbRec.setVisibility(View.VISIBLE);
//		imgsend.setVisibility(View.VISIBLE);
//
//		pbRec.setProgress(progress); // 设置进度条
//		imgsend.setAnimation(AnimationUtils.loadAnimation(context,
//				R.anim.device_skip)); // 开始动画
//	}
//
//	public void finsh()
//	{
//		imgsend.clearAnimation(); // 停止动画
//		pbRec.setProgress(100);
//
//	}
//
//}
