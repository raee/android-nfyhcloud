//package com.yixin.nfyh.cloud.ui;
//
//import java.util.ArrayList;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.webkit.WebView;
//import android.widget.GridView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.yixin.nfyh.cloud.model.SignTypes;
//import com.yixin.nfyh.cloud.ui.adapter.SignTypeItemGridViewAdapter;
//import com.yixin.nfyh.cloud.utils.ReflectUtil;
//
///**
// * 体征详细视图
// * 
// * @author MrChenrui
// * 
// */
//@SuppressLint("SetJavaScriptEnabled")
//public class SignDetailView extends LinearLayout
//{
//	
//	private ArrayList<View>	views		= new ArrayList<View>();
//	private ArrayList<View>	viewParames	= new ArrayList<View>();
//	private Context			context;
//	private GridView		gvSigns;
//	private ListView		lvParamLayout;
//	private WebView			webView;
//	private WebView			systemwebView;
//	
//	public SignDetailView(Context context)
//	{
//		super(context);
//		this.context = context;
//		LayoutInflater.from(context).inflate(R.layout.ui_sign_detail, this);
//		
//		gvSigns = (GridView) this.findViewById(R.id.gv_ui_sign_detail_signs);
//		lvParamLayout = (ListView) findViewById(R.id.ll_ui_sign_detail_params_title);
//		this.webView = (WebView) findViewById(R.id.webview_sign_chat);
//		this.webView.getSettings().setJavaScriptEnabled(true);
//		this.webView.loadUrl("file:///android_asset/chat/index.html");
//		
//		this.systemwebView = (WebView) findViewById(R.id.webview_sign_system);
//		
//		this.systemwebView.getSettings().setJavaScriptEnabled(true);
//		this.systemwebView.loadUrl("file:///android_asset/system/index.html");
//		
//	}
//	
//	public GridView getGridView()
//	{
//		return this.gvSigns;
//	}
//	
//	public void addSignType(SignTypes m)
//	{
//		View signView = LayoutInflater.from(context).inflate(
//				R.layout.ui_sign_edit_item, null);
//		TextView tvValue = (TextView) signView
//				.findViewById(R.id.tv_ui_sign_edit_item_value);
//		
//		TextView tvTitle = (TextView) signView
//				.findViewById(R.id.tv_sign_edit_item_title);
//		TextView tvUnit = (TextView) signView
//				.findViewById(R.id.tv_sign_edit_item_unit);
//		
//		tvTitle.setText(m.getName());
//		tvUnit.setText(m.getTypeUnit());
//		tvValue.setText(m.getDefaultValue());
//		signView.setTag(m.getTypeId());
//		this.views.add(signView);
//	}
//	
//	public void setup()
//	{
//		gvSigns.setAdapter(new SignTypeItemGridViewAdapter(views));
//		lvParamLayout.setAdapter(new SignTypeItemGridViewAdapter(viewParames));
//	}
//	
//	public void addParameter(SignTypes m)
//	{
//		ListItemView view = new ListItemView(context, null,
//				R.style.Widget_Light_IOS_ListItem);
//		int iconId = ReflectUtil.getDrawableId(R.drawable.class,
//				m.getTypeIcon());
//		
//		view.setText(m.getName());
//		view.setSubText(m.getDefaultValue());
//		view.setIcon(iconId);
//		view.setNextEnable(true);
//		view.setTag(m.getTypeId());
//		viewParames.add(view);
//	}
//	
//	public void refreshChat()
//	{
//		// 刷新图表
//	}
//	
//	public void setTips(String tips)
//	{
//	}
//}
