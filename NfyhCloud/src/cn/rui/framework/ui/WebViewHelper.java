package cn.rui.framework.ui;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;
import android.widget.ZoomControls;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewHelper
{
	/**
	 * 设置默认配置
	 * 
	 * @param webView
	 */
	public static void setDefaultOption(WebView webView)
	{
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDisplayZoomControls(false);
		setZoomControlGone(webView); // 隐藏缩放按钮
		webView.getSettings().setBuiltInZoomControls(true); // 设置可缩放的
	}
	
	/**
	 * 隐藏缩放按钮
	 * 
	 * @param webView
	 *            Webview
	 */
	public static void setZoomControlGone(View webView)
	{
		try
		{
			Class<WebView> classType = WebView.class;
			Field field = classType.getDeclaredField("mZoomButtonsController");
			field.setAccessible(true);
			// 获取到控制ZoomControls的控制器
			ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(webView);
			// 获取ZoomControls
			ZoomControls zoomControls = (ZoomControls) mZoomButtonsController.getZoomControls();
			zoomControls.setVisibility(View.GONE);
			// 改变ZoomControl的样式
			zoomControls.removeAllViews();
			
			try
			{
				field.set(webView, mZoomButtonsController);
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
	}
}
