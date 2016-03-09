package com.yixin.nfyh.cloud.bll;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient.ErrorCode;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import cn.rui.framework.ui.RuiDialog;

import com.rae.im.sdk.rongcloud.IMLoginListener;
import com.rae.im.sdk.rongcloud.RaeRongCloudIM;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.QQBindActivity;
import com.yixin.nfyh.cloud.data.NfyhUserProvider;
import com.yixin.nfyh.cloud.i.ILogin;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.w.NfyhWebserviceFactory;
import com.yixin.nfyh.cloud.w.SoapConnectionCallback;
import com.yixin.nfyh.cloud.w.WebServerException;

/**
 * 登录处理逻辑，已经帐号管理
 * 
 * @author ChenRui
 * 
 */
public class Account implements SoapConnectionCallback<Users> {
	private static final String APPID = "1103162244";
	private Context mContext;
	private ILoginCallback mListener;
	private GlobalSetting mSetting;
	private NfyhApplication mApplication;
	private Tencent mTencent;
	private ILogin mLoginApi;

	public Account(Context context) {
		this.mContext = context;
		this.mSetting = new GlobalSetting(context);
		this.mApplication = (NfyhApplication) mContext.getApplicationContext();
		mTencent = Tencent.createInstance(APPID, mApplication);
		mLoginApi = NfyhWebserviceFactory.getFactory(mContext).getLogin();
		mLoginApi.setOnConnectonCallback(this);
	}

	/**
	 * 设置登录回调
	 * 
	 * @param l
	 */
	public void setLoginCallbackListener(ILoginCallback l) {
		this.mListener = l;
	}

	/**
	 * 本地数据库登录
	 * 
	 * @param username
	 * @param pwd
	 */
	public void loginInLocal(String username, String pwd) {
		onSoapConnectSuccess(getGuestUser());
	}

	/**
	 * 获取游客帐号
	 * 
	 * @return
	 */
	public Users getGuestUser() {
		// 构造游客帐号
		Users guest = new Users();
		guest.setUsername("guest");
		guest.setPwd("guest");
		guest.setUid("0");
		guest.setName("离线用户");
		guest.setSex("男");

		return guest;
	}

	/**
	 * 登录
	 * 
	 * @param username
	 * @param pwd
	 */
	public void login(String username, String pwd) {
		mLoginApi.login(username, pwd);
	}

	/**
	 * QQ 登录
	 */
	public void loginByQQ() {
		Toast.makeText(mContext, "正在验证..", Toast.LENGTH_LONG).show();
		String openId = mSetting.getValue("OPEN_ID", "");

		// 过期校验
		String time = mSetting.getValue("QQ_TIME_OUT", "0");
		long timeMillis = Long.parseLong(time);
		if (System.currentTimeMillis() >= timeMillis) { // 已过期
			mTencent.login((Activity) mContext, "all", new QQUIListener());
			return;
		}

		if (!TextUtils.isEmpty(openId)) {
			mLoginApi.loginByQQ(openId);
			return;
		}

		mTencent.login((Activity) mContext, "all", new QQUIListener());

	}

	/**
	 * QQ 绑定
	 * 
	 * @param username
	 * @param pwd
	 * @param openId
	 */
	public void bindQQ(final String username, String pwd, String openId) {
		mLoginApi.setOnConnectonCallback(new SoapConnectionCallback<Users>() {

			@Override
			public void onSoapConnectedFalid(WebServerException e) {
				Account.this.onSoapConnectedFalid(e);
			}

			@Override
			public void onSoapConnectSuccess(Users data) {
				Toast.makeText(mContext, username + "授权成功！", Toast.LENGTH_SHORT)
						.show();
				Account.this.onSoapConnectSuccess(data);
			}
		});

		mLoginApi.bindQQ(username, pwd, openId);
	}

	public Tencent getTencent() {
		return mTencent;
	}

	/**
	 * QQ 登出
	 */
	public void logout() {
		mTencent.logout(mApplication);
		mSetting.remove("OPEN_ID");
		mSetting.remove("QQ_TIME_OUT");
	}

	/*
	 * 登录成功。
	 */
	@Override
	public void onSoapConnectSuccess(final Users data) {

		// 登录融云
		RaeRongCloudIM.getInstance().login(data.getAppSecret(), data.getUid(),
				data.getName(), data.getHeadImage(), new IMLoginListener() {

					@Override
					public void onTokenIncorrect() {
						mListener.OnLoginFaild("登录过期，请重新登录！");
					}

					@Override
					public void onSuccess(String token) {
						mApplication.setUserInfo(data);
						mApplication.setIsLogin(true);
						if (!data.getUid().equals("0")) {
							mSetting.setUser(data);
						}

						RongIM.setUserInfoProvider(new NfyhUserProvider(
								mContext), true); // 用户提供者

						mListener.OnLoginSuccess(data.getUsername(),
								data.getPwd());

					}

					@Override
					public void onError(ErrorCode code) {
						mListener.OnLoginFaild("IM服务器错误：" + code.getValue());

					}

					@Override
					public void onIMLoginSuccess(String userName,
							String password, String extra) {

					}

					@Override
					public void onIMLoginError(String userName,
							String password, int errorCode, String msg) {
						mListener.OnLoginFaild("IM Token错误。");
						if (msg != null) {
							Log.e("LoginActivity", msg);
						}
					}
				});

	}

	@Override
	public void onSoapConnectedFalid(WebServerException e) {
		if (e.getCode() == WebServerException.CODE_NULL_DATA) {
			mListener.OnLoginFaild("用户名或密码错误！");
		} else if (e.getCode() == 403) { // QQ 未授权
			new RuiDialog.Builder(mContext).buildTitle("QQ未绑定")
					.buildMessage("该QQ帐号没有绑定云服务帐号，需绑定后才能使用，是否绑定？")
					.buildLeftButton("不绑定", null)
					.buildRight("绑定", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 跳转QQ绑定页面
							Intent intent = new Intent(mContext,
									QQBindActivity.class);
							intent.putExtra("openId",
									mSetting.getValue("OPEN_ID", ""));
							mContext.startActivity(intent);
							dialog.dismiss();
						}
					}).show();

			mListener.OnLoginFaild(e.getMessage());
		} else {
			this.mListener.OnLoginFaild(e.getMessage());
		}
	}

	/**
	 * QQ 登录授权回调
	 * 
	 * @author ChenRui
	 * 
	 */
	class QQUIListener implements IUiListener {

		@Override
		public void onError(UiError e) {
			onSoapConnectedFalid(new WebServerException(e.errorCode,
					e.errorMessage));
		}

		@Override
		public void onComplete(Object jsonObj) {
			// QQ 回调,再次登录。
			Log.i("Account", jsonObj.toString());

			try {
				JSONObject obj = (JSONObject) jsonObj;
				String openId = obj.getString("openid");
				long currentTime = System.currentTimeMillis();
				long timeout = obj.getInt("expires_in") + currentTime;

				// 保存登录信息
				mSetting.setValue("OPEN_ID", openId);
				mSetting.setValue("QQ_TIME_OUT", "" + timeout);
				mSetting.commit();

				mTencent.setOpenId(openId);
				mLoginApi.loginByQQ(openId);
				Log.i("Account", "openID:" + openId);
			} catch (JSONException e) {
				onError(new UiError(303, "QQ授权出错[Json]!", "QQ授权出错[Json]!"));
				e.printStackTrace();
			} catch (NumberFormatException e) {
				onError(new UiError(303, "QQ授权出错[Parse]!", "QQ授权出错[Parse]!"));
			}
		}

		@Override
		public void onCancel() {
			onSoapConnectedFalid(new WebServerException("登录取消！"));
		}
	}
}
