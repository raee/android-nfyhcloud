package cn.rui.framework.utils;

public interface SoapCallback
{
	void onSoapResponse(Object response);

	void onSoapError(int code, String msg);
}
