package com.yixin.nfyh.cloud;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import cn.rui.framework.ui.WebViewerActivity;

import com.rae.alarm.AlarmListActivity;
import com.yixin.nfyh.cloud.activity.SMSListActivity;
import com.yixin.nfyh.cloud.activity.UserSettingActivity;
import com.yixin.nfyh.cloud.bll.ApiController;

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
			case R.id.ll_menu_myddc: //满意度
				Intent webIntent = new Intent(this, SMSListActivity.class);
				startActivity(webIntent);
				break;
			case R.id.ll_menu_cyxj: // 出院提醒
				startActivity(new Intent(this, AlarmListActivity.class));
				break;
			case R.id.ll_menu_bqpg: // 病情评估
				
				Intent bqpgIntent = new Intent(this, WebViewerActivity.class);
				Uri bqpguri = Uri.parse(ApiController.get().getBingQingPingGuUrl().replace("@uid", app.getCurrentUser().getUid()));
				bqpgIntent.setData(bqpguri);
				bqpgIntent.putExtra(WebViewerActivity.EXTRA_COOKIE, app.getGlobalsetting().getUser().getCookie());
				startActivity(bqpgIntent);
				
				break;
//			case R.id.ll_menu_yjfk: //意见反馈
//				Intent feedbackIntent = new Intent(this, WebViewerActivity.class);
//				Uri feedbackuri = Uri.parse(getString(R.string.url_yjfk));
//				feedbackIntent.setData(feedbackuri);
//				feedbackIntent.putExtra(WebViewerActivity.EXTRA_COOKIE, app.getGlobalsetting().getUser().getCookie()); 
//				startActivity(feedbackIntent);
//				break;
			default:
				break;
		}
	}
	
}
