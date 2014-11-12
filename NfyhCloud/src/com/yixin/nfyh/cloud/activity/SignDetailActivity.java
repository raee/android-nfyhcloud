package com.yixin.nfyh.cloud.activity;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import cn.rui.framework.ui.RuiDialog;

import com.yixin.monitors.sdk.model.PackageModel;
import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.DeviceConnectActivity;
import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.ConfigServer;
import com.yixin.nfyh.cloud.bll.SignCore;
import com.yixin.nfyh.cloud.bll.sign.SignCoreListener;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.ui.ActionbarUtil;
import com.yixin.nfyh.cloud.ui.InputSignView;
import com.yixin.nfyh.cloud.ui.TopMsgView;

public class SignDetailActivity extends BaseActivity implements
		SignCoreListener {
	private boolean keyDownResult = false;
	private boolean mIsNoUpload = false; // 是否没上传
	private TopMsgView viewMsg = null;
	private KeyguardLock mKeyguardLock;
	private WakeLock mWakeLock;
	private ViewGroup rootView;
	private Users user;
	private SignCore mSignCore;
	private InputSignView mInputSignView;
	private SignTypes mCurrentSignType;
	private View mUploadButton;
	private boolean mIsTipsWindowDismiss; // 提示窗口是否关闭了
	private ConfigServer mConfig;
	private boolean mIsFromDevice = false; // 是否来自设备的数据

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 至于锁屏之上
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		this.rootView = (ViewGroup) getLayoutInflater().inflate(
				R.layout.activity_sign_detail, null);
		setContentView(rootView);

		// 键盘管理器
		KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		// 电源管理
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

		// 键盘锁
		mKeyguardLock = mKeyguardManager.newKeyguardLock("unlock");

		// 唤醒锁
		mWakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.SCREEN_DIM_WAKE_LOCK, "PowerManager");

		// 键盘解锁
		mKeyguardLock.disableKeyguard();
		mWakeLock.setReferenceCounted(false); // 设置超时锁
		// 点亮屏幕
		mWakeLock.acquire();

		user = ((NfyhApplication) getApplication()).getCurrentUser(); // 获取用户
		mSignCore = new SignCore(this);
		mSignCore.setSignCoreListener(this);

		mConfig = new ConfigServer(this);

		mInputSignView = (InputSignView) findViewById(R.id.sign_view);
		mUploadButton = mInputSignView.findViewById(R.id.btn_sign_upload);
		mUploadButton.setOnClickListener(this);
		initSign();

		if ("device".equals(getIntent().getStringExtra("from"))) {
			boolean isAutoUpload = mConfig
					.getBooleanConfig(ConfigServer.KEY_AUTO_UPLOAD);
			if (isAutoUpload) {
				this.sync();
			}
		}

	}

	// 初始化体征
	private void initSign() {
		String type = getIntent().getStringExtra(Intent.EXTRA_TEXT);// 获取体征类型
		type = type == null ? "1000" : type;

		if (type.equals("-1")) { // 蓝牙传递过来的的数据。
			mIsFromDevice = true;
			PackageModel models = getIntent().getParcelableExtra("data");// 蓝牙接收的数据
			mInputSignView.setDataList(mSignCore
					.converSignDataModelToDbModel(models.getSignDatas()));
			mInputSignView.setShowType(InputSignView.TYPE_BLUETOOTH);
			mInputSignView
					.showTipsWindow(new DialogInterface.OnDismissListener() {

						@Override
						public void onDismiss(DialogInterface dialog) {
							mIsTipsWindowDismiss = true;
							if (!mUploadButton.isEnabled()) {
								showUploadSuccess("上传成功");
							}
						}
					});
			getActionBar().setTitle(user.getName());
			mIsNoUpload = true;
			return;
		}

		this.mCurrentSignType = mSignCore.getSignType(type);
		mInputSignView.setDataList(mSignCore.getSignTypes(type));// 获取体征参数
		ActionbarUtil.setTitleAsUpHome(this, getActionBar(),
				mCurrentSignType.getName()); // 返回栏
		mInputSignView.setUp();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 重新锁上屏幕
		mKeyguardLock.disableKeyguard();
		mWakeLock.release();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		mIsNoUpload = mInputSignView.hasChanage(); // 是否没有上传

		if (keyCode == KeyEvent.KEYCODE_BACK && mIsNoUpload) {
			showUploadTips();
		} else {
			keyDownResult = super.onKeyDown(keyCode, event);
		}

		return keyDownResult;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		upload();
	}

	private void upload() {
		if (mSignCore.isSyncing()) {
			showMsg("数据正在同步,请稍后...");
		} else {
			showDataDialog();
		}
	}

	// 上传失败
	@Override
	public void onSignCoreError(int code, String msg) {
		if (viewMsg == null)
			return;
		viewMsg.setMsg(msg);
		viewMsg.anim();
		viewMsg.setIcon(R.drawable.icon_write_faild);
		viewMsg.setBackgroundColor(getResources().getColor(R.color.mihuang));
		mUploadButton.setEnabled(true);
	}

	// 上传成功
	@Override
	public void onSignCoreSuccess(int code, String msg) {
		if (viewMsg == null)
			return;
		viewMsg.setMsg(msg);
		viewMsg.stopAnim();
		viewMsg.setIcon(R.drawable.icon_write_success);
		if (code == 200) {

			mUploadButton.setEnabled(false);
			if (!mIsFromDevice) { // 不是来自设备的
				return;
			}
			if (!mConfig.getBooleanConfig(ConfigServer.KEY_AUTO_TIPS)
					|| mIsTipsWindowDismiss) { // 没有体征提示的直接显示上传成功。
				showUploadSuccess(msg);
			}
		}

	}

	private void showUploadSuccess(String msg) {
		Intent intent = new Intent(SignDetailActivity.this,
				DeviceConnectActivity.class);
		intent.putExtra(DeviceConnectActivity.INTENT_EXTRA_TIPS, msg);
		intent.putExtra(DeviceConnectActivity.INTENT_EXTRA_MESSAGE, "");
		intent.putExtra(DeviceConnectActivity.EXTRA_NAME,
				DeviceConnectActivity.EXTRA_SHOW_SUCCESS);
		startActivity(intent);
		finish();
	}

	// 正在上传
	@Override
	public void onUploading() {
		if (viewMsg != null) {
			viewMsg.setMsg("正在上传...");
			viewMsg.setIcon(R.drawable.common_loading);
			AnimationDrawable drawable = (AnimationDrawable) viewMsg
					.getIconImageView().getDrawable();
			drawable.start();
		}
	}

	/**
	 * 显示数据确认对话框
	 */
	private void showDataDialog() {
		String source = "<p>您确定这些数据是：</p><p>" + user.getName() + "</p>";
		for (SignTypes m : mInputSignView.getDataList()) {
			source += "<p>";
			source += m.getName();
			source += "：";
			source += m.getDefaultValue();
			source += "</p>";
		}

		String html = Html.fromHtml(source).toString();

		new RuiDialog.Builder(this).buildTitle("数据确认").buildMessage(html)
				.buildLeftButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (mInputSignView.getShowType() == -1) {
							finish();
						}
						dialog.dismiss();
					}
				}).buildRight("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						sync();
						dialog.dismiss();
					}
				}).show();
	}

	/**
	 * 显示体征上传提示
	 */
	private boolean showUploadTips() {
		new RuiDialog.Builder(this).buildTitle("数据上传")
				.buildMessage("您的数据还没上传，是否需要上传到云服务？")
				.buildLeftButton("放弃", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mIsNoUpload = false;
						dialog.dismiss();
						finish();
					}
				}).buildRight("上传", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						keyDownResult = true;
						showDataDialog();
						dialog.dismiss();
					}
				}).show();

		return keyDownResult;
	}

	/**
	 * 同步数据
	 */
	private void sync() {
		if (viewMsg == null) {
			viewMsg = new TopMsgView(this, null);
		}
		viewMsg.setIcon(R.drawable.view_browser_web_update);
		viewMsg.setMsg("正在上传数据...");
		viewMsg.anim();
		viewMsg.show((ViewGroup) this.rootView.getChildAt(0));

		// findViewById(R.id.menu_sign_detail_sync).startAnimation(AnimationUtils.loadAnimation(this,
		// R.anim.fade_in)); // start Animation

		mSignCore.sysnc(mInputSignView.getDataList(), this);
		// mSignCore.saveUserSign(mInputSignView.getDataList()); // 本地保存
		//
		// mSignCore.upload();

		mIsNoUpload = false;
		mInputSignView.setChange(false);
	}

}
