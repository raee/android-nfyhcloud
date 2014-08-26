//package com.yixin.nfyh.cloud.widget;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.graphics.Paint;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yixin.nfyh.cloud.R;
//
//public class ViewItemRemind extends LinearLayout {
//
//	private TextView tvOrdersExecuteTime;
//	private TextView tvOrdersContent;
//	private ImageView olAgreeAgreement;
//	private TextView tvFromTime;
//	private LinearLayout llOrdersAlarm;
//
//	private Context mContext;
//
//	private int ordersType;
//
//	public int getOrdersType() {
//		return ordersType;
//	}
//
//	public void setOrdersType(int ordersType) {
//		this.ordersType = ordersType;
//	}
//
//	public ViewItemRemind(Context context) {
//		super(context);
//		this.mContext = context;
//		LayoutInflater.from(context).inflate(R.layout.view_item_remind, this);
//		tvOrdersExecuteTime = (TextView) findViewById(R.id.tv_alarm_remain_time);
//		tvOrdersContent = (TextView) findViewById(R.id.tv_orders_content);
//		olAgreeAgreement = (ImageView) findViewById(R.id.ol_agree_agreement);
//		tvFromTime = (TextView) findViewById(R.id.tv_from_time);
//		llOrdersAlarm = (LinearLayout) findViewById(R.id.ll_orders_alarm);
//		this.setLongClickable(true);// 长按监听
//	}
//
//	public ViewItemRemind(Context context, AttributeSet attrs) {
//		super(context);
//
//	}
//
//	/**
//	 * 设置医嘱的内容
//	 */
//	public void setTvOrdersContent(String content) {
//		tvOrdersContent.setText(content);
//	}
//
//	/**
//	 * 设置医嘱的时间
//	 */
//	@SuppressLint("SimpleDateFormat")
//	public void setTvOrdersExecuteTime(String executeDate, String executeTime) {
//		tvOrdersExecuteTime.setText(executeDate);
//		 int day = tranDay(executeDate);
//		 String TimeContent = "";
//		 if(day == 0){
//			 TimeContent = "今天";
//		 }else if(day < 0){
//			 TimeContent = day + "天前";
//		 }else {
//			 TimeContent = day + "天后";
//		 }
////		tvFromTime.setText(executeTime.substring(0, 5));
//		tvFromTime.setText(TimeContent);
//	}
//
//	/**
//	 * 相差多少天
//	 */
//	@SuppressLint("SimpleDateFormat")
//	private int tranDay(String executeTime) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
//		Date date = null;
//		try {
//			date = sdf.parse(executeTime);
//			if (date == null) {
//				return 0;
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		Date nowDate = new Date(System.currentTimeMillis());
//		long i = (date.getTime() - nowDate.getTime()) / (1000 * 60 * 60 * 24);
//		return (int) i;
//	}
//
//	/**
//	 * 设置提醒是否过期
//	 */
//	public void setViewEnable(boolean bool) {
//		if (bool) {
//			llOrdersAlarm.setBackgroundDrawable(getResources().getDrawable(
//					R.drawable.clock_time_line_item_bg));
//
//			tvOrdersExecuteTime.getPaint()
//					.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//			tvOrdersContent.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//			
//			LinearLayout llOrdersBg = (LinearLayout) findViewById(R.id.ll_orders_bg);
//			llOrdersBg.setVisibility(View.VISIBLE);
//			llOrdersBg.setBackgroundColor(getResources()
//					.getColor(R.color.white));
//			llOrdersBg.getBackground().setBounds(llOrdersAlarm.getBackground().copyBounds());
////			llOrdersBg.getBackground().setAlpha(150);
//		} else {
//			llOrdersAlarm.setBackgroundDrawable(getResources().getDrawable(
//					R.drawable.clock_time_line_item_bg_red_dot));
//		}
//	}
//
//}
