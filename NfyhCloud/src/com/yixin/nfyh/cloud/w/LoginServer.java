package com.yixin.nfyh.cloud.w;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.data.IUser;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.i.ILogin;
import com.yixin.nfyh.cloud.model.Users;

/**
 * 登录
 * 
 * @author MrChenrui
 * 
 */
public class LoginServer extends WebserverConnection implements ILogin {
	private SoapConnectionCallback<Users>	mListener;
	private String							password;
	private IUser							mUser;

	public LoginServer(Context context) {
		super(context);
		mUser = NfyhCloudDataFactory.getFactory(context).getUser();
	}

	@Override
	public void login(String userName, String pwd) {
		this.password = pwd;
		String methodName = context.getString(R.string.soap_method_login);
		NfyhSoapConnection<Users> conn = new NfyhSoapConnection<Users>(context);
		conn.setParser(new LoginParser());
		conn.setParams("user_Account", userName);
		conn.setParams("userPwd", pwd);
		conn.setonSoapConnectionCallback(mListener);
		conn.request(methodName);
	}

	private class LoginParser implements IWebserverParser<Users> {

		@Override
		public Users parser(String json) {
			try {
				JSONObject obj = new JSONObject(json);
				Object isNull = obj.get("Userinfo");

				if (isNull.toString().equals("null")) {
					return null;
				} // 没有对象

				Users userInfo = new Users();
				JSONObject user = obj.getJSONObject("Userinfo");

				String username = user.getString("Useraccount");
				String uid = user.getString("Id");
				String cookie = obj.getString("Cookie");
				String realName = user.getString("Username");
				int sex = user.getInt("Usersex");

				// 模块配置
				JSONArray signTypeArray = obj.getJSONArray("SignType"); // 体征
				JSONArray moduleArray = obj.getJSONArray("Service"); // 模块

				// 将模块插入到字典表中。
				parse(moduleArray, uid, 0);
				parse(signTypeArray, uid, 1);

				userInfo.setSex(sex == 1 ? "男" : "女");
				userInfo.setUid(uid);
				userInfo.setName(realName);
				userInfo.setCookie(cookie);
				userInfo.setUsername(username);
				userInfo.setAge(60);
				userInfo.setPwd(password); // TODO:加密

				return userInfo;
			}
			catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}

	}

	@Override
	public void setOnConnectonCallback(SoapConnectionCallback<Users> l) {
		this.mListener = l;
	}

	/**
	 * 解析模块和体征类型
	 * @param arr
	 *            Json
	 * @param uid
	 *            用户Id
	 * @param code
	 *            类型：[0]-模块；[1]-体征类型
	 */
	private void parse(JSONArray arr, String uid, int code) {
		try {
			String[] values = new String[arr.length()];
			for (int i = 0; i < arr.length(); i++) {
				values[i] = arr.getString(i);
			}
			if (code == 0) {
				mUser.addUserModule(uid, values);
			}
			else {
				mUser.addUserSignType(uid, values);
			}

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
