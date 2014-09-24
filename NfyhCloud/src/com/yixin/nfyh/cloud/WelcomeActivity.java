package com.yixin.nfyh.cloud;

import android.content.Intent;
import android.os.Bundle;

/**
 * 欢迎页
 * 
 * @author MrChenrui
 * 
 */
public class WelcomeActivity extends BaseActivity implements Runnable {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		/**
		 * 功能描述：欢迎界面
		 */
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		try {
			/**
			 * 延迟两秒时间
			 */
			Thread.sleep(4000);
			
			// 读取SharedPreferences中需要的数据
			
			/**
			 * 如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
			 */
			if (!getSetting().isApplicationInstalled()) {
				
				startActivity(new Intent(getApplicationContext(), GuideActivity.class));
				getSetting().setApplicationInstalled();
			}
			else {
				
				// 已经登录
				if (getNfyhApplication().isLogin()) {
					getNfyhApplication().setIsLogin(true);
					startActivity(new Intent(this, MainActivity.class));
				}
				else {
					startActivity(new Intent(getApplicationContext(), LoginActivity.class));
				}
			}
			finish();
			
		}
		catch (InterruptedException e) {
			startActivity(new Intent(getApplicationContext(), LoginActivity.class));
			finish();
		}
	}
	
}
