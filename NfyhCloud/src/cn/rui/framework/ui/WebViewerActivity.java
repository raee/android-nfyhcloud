package cn.rui.framework.ui;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ZoomButtonsController;
import android.widget.ZoomControls;

import com.yixin.nfyh.cloud.R;

/**
 * 用于查看网页的窗体
 * <p>
 * Uri uri = Uri.parse(getResources().getString(R.string.url_health_assess));
 * </p>
 * <p>
 * webIntent.setData(uri);
 * </p>
 * 
 * @author MrChenrui
 * 
 */
@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
public class WebViewerActivity extends Activity implements OnClickListener
{
	
	public static final String	EXTRA_COOKIE	= "EXTRA_COOKIE";
	
	protected String			mUrl;
	protected WebView			mWebView;
	protected Button			btnReload;
	
	private String				cookie;
	private View				loaddingView;
	private boolean				isOneIn			= true;			// 是否第一次进入
																	
	private Animation			animation;
	private String				message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xr_layout_webviewer);
		
		ActionBar bar = getActionBar();
		if (bar != null)
		{
			bar.setDisplayUseLogoEnabled(false);
			bar.setDisplayShowHomeEnabled(false);
			bar.setDisplayHomeAsUpEnabled(true);
		}
		if (getIntent().getDataString() != null && !"null".equals(getIntent().getDataString()))
		{
			this.mUrl = getIntent().getDataString();
		}
		else
		{
			this.mUrl = "file:///android_asset/system/view.html";
		}
		if (getIntent().getStringExtra("title") != null)
		{
			setTitle(getIntent().getStringExtra("title"));
		}
		
		animation = AnimationUtils.loadAnimation(this, R.anim.spinning);
		this.mWebView = (WebView) findViewById(R.id.xr_webviewer_webView);
		loaddingView = findViewById(R.id.ll_setting_webview_loadding);
		setDefaultWebViewOption(this.mWebView);
		loadDefautData();
		this.mWebView.loadUrl(mUrl);
		this.message = getIntent().getStringExtra("message");
	}
	
	/**
	 * 初始化默认Intent进来的数据
	 */
	private void loadDefautData()
	{
		Bundle data = getIntent().getExtras();
		if (data.containsKey(EXTRA_COOKIE))
		{
			cookie = data.getString(EXTRA_COOKIE);
			cookie = ".ASPXAUTH=" + cookie;
			URL url;
			try
			{
				url = new URL(this.mUrl);
				setCookie(url.getHost(), cookie);
				Log.i("webview", url.getHost());
			}
			catch (MalformedURLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 设置Webview的默认设置
	 */
	private void setDefaultWebViewOption(WebView v)
	{
		try
		{
			WebSettings setting = v.getSettings();
			setting.setJavaScriptEnabled(true); // 启动脚本
			
			setting.setBuiltInZoomControls(true); // 设置可缩放的
			WebViewHelper.setZoomControlGone(mWebView); // 隐藏缩放按钮
			
			v.setWebChromeClient(new MyWebChromeClient());
			v.setWebViewClient(new MyWebClient());
			
			setting.setDisplayZoomControls(false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// 返回键监听，默认返回上一页
		if (keyCode == KeyEvent.KEYCODE_BACK && this.mWebView.canGoBack())
		{
			mWebView.goBack();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_webview, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		
		int id = item.getItemId();
		if (id == R.id.menu_refresh)
		{
			mWebView.reload();
		}
		else if (id == android.R.id.home)
		{
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class MyWebChromeClient extends WebChromeClient
	{
	}
	
	private void start()
	{
		
		View refresh = this.findViewById(R.id.menu_refresh);
		if (refresh != null)
		{
			refresh.startAnimation(animation);
		}
		if (isOneIn)
		{
			ImageView imageView = (ImageView) loaddingView.findViewById(R.id.img_xr_webview_loadding);
			((AnimationDrawable) imageView.getDrawable()).start();
			loaddingView.setVisibility(View.VISIBLE);
		}
	}
	
	private void stop()
	{
		if (isOneIn)
		{
			isOneIn = false;
			ImageView imageView = (ImageView) loaddingView.findViewById(R.id.img_xr_webview_loadding);
			((AnimationDrawable) imageView.getDrawable()).stop();
			loaddingView.setVisibility(View.GONE);
			mWebView.setVisibility(View.VISIBLE);
		}
		View refresh = this.findViewById(R.id.menu_refresh);
		if (refresh != null)
		{
			refresh.clearAnimation();
		}
	}
	
	private class MyWebClient extends WebViewClient
	{
		// 加载错误
		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
		{
			stop();
		}
		
		// 加载完毕
		@Override
		public void onPageFinished(WebView view, String url)
		{
			stop();
			if (message != null)
			{
				view.loadUrl("javascript:loadHtml('" + message + "')");
			}
			else
			{
				setTitle(view.getTitle());
			}
		}
		
		// 开始加载网页
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon)
		{
			start();
		}
		
	}
	
	/**
	 * 设置Cookie
	 * 
	 * @param url
	 *            作用域
	 * @param cookie
	 *            类似：cookiename=12346
	 */
	private void setCookie(String url, String cookie)
	{
		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		if (cookieManager.getCookie(url) != null)
		{
			cookieManager.removeAllCookie();
		}
		cookieManager.setAcceptCookie(true);
		cookieManager.setCookie(url, cookie);
		cookieManager.acceptCookie();
		CookieSyncManager.getInstance().sync();
		Log.i("webview", "设置Cookie：" + cookie);
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
}
