package com.yixin.nfyh.cloud;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;

import com.yixin.nfyh.cloud.bll.GlobalSetting;

/**
 * 服务条款同意视图
 * 
 * @author ChenRui
 * 
 */
public class TermsServiceActivity extends Activity implements OnClickListener {
	private WebView			mWebView;
	private GlobalSetting	mSetting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms);
		this.mWebView = (WebView) findViewById(R.id.web_terms);
		findViewById(R.id.btn_terms_agree).setOnClickListener(this);
		findViewById(R.id.btn_terms_disagree).setOnClickListener(this);
		this.mSetting = new GlobalSetting(this);
		mWebView.loadUrl("file:///android_asset/system/readme.html");
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_terms_agree:
				this.mSetting.setApplicationInstalled();
				startActivity(new Intent(this, GuideActivity.class));
				break;
			default:
				break;
		}
		finish();
	}
}
