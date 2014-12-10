package com.yixin.nfyh.cloud;

import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.os.Bundle;
import android.widget.TextView;
import cn.rui.framework.utils.AppInfo;

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

		if (Config.isDebug) {
			startActivity(new Intent(this, LoginActivity.class));
			finish();
			return;
		}

		TextView tvVersion = (TextView) findViewById(R.id.tv_welcome_version);
		String version = new AppInfo(this).getVersion();
		tvVersion.setText("V" + version);

		/**
		 * 功能描述：欢迎界面
		 */
		new Thread(this).start();
	}

	/**
	 * 为程序创建桌面快捷方式
	 */
	private void addShortcut() {
		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

		// 快捷方式的名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
		shortcut.putExtra("duplicate", false); // 不允许重复创建
		Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
		shortcutIntent.setClassName(this, this.getClass().getName());
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		// 快捷方式的图标
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.drawable.ic_launcher);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

		sendBroadcast(shortcut);
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
				addShortcut();
				startActivity(new Intent(getApplicationContext(), TermsServiceActivity.class));
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
