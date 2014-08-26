package com.yixin.nfyh.cloud.w;

public interface SoapConnectionCallback<T>
{
	void onSoapConnectSuccess(T data);

	void onSoapConnectedFalid(WebServerException e);
}
