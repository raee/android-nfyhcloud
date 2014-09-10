package com.yixin.nfyh.cloud;

import com.yixin.nfyh.cloud.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import cn.rui.framework.ui.WebViewerActivity;

import com.yixin.nfyh.cloud.activity.AlarmListActivity;
import com.yixin.nfyh.cloud.activity.SMSListActivity;
import com.yixin.nfyh.cloud.activity.UserSettingActivity;

/**
 * 院后管理界面
 * 
 * @author 睿
 * 
 */
public class HospitalManagerActivity extends BaseActivity
{
	
	private NfyhApplication	app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yhgl);
		app = (NfyhApplication) getApplication();
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.ll_menu_cyxj: //出院小结
				startActivity(new Intent(this, AlarmListActivity.class));
				break;
			case R.id.ll_menu_myddc: //满意度
				Intent webIntent = new Intent(this, SMSListActivity.class);
				startActivity(webIntent);
				break;
			case R.id.ll_menu_grsz: //个人设置
				Intent intent = new Intent(this, UserSettingActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_menu_yjfk: //意见反馈
				Intent feedbackIntent = new Intent(this,
						WebViewerActivity.class);
				Uri feedbackuri = Uri.parse(getString(R.string.feedback_url));
				feedbackIntent.setData(feedbackuri);
				feedbackIntent.putExtra(WebViewerActivity.EXTRA_COOKIE, app
						.getGlobalsetting().getUser().getCookie()); //TODO:获取Cookie
				startActivity(feedbackIntent);
				break;
			default:
				break;
		}
	}
	
}
