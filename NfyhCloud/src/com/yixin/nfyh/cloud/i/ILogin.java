package com.yixin.nfyh.cloud.i;

import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.w.SoapConnectionCallback;

public interface ILogin {
	void login(String userName, String pwd);

	void loginByQQ(String openId);

	void bindQQ(String username, String pwd, String openId);

	void setOnConnectonCallback(SoapConnectionCallback<Users> l);
}
