package com.yixin.nfyh.cloud.w;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;
import cn.rui.framework.utils.CommonUtil;
import cn.rui.framework.utils.SoapCallback;
import cn.rui.framework.utils.SoapConnection;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.ApiController;

/**
 * 院后统一调用API HTTP连接
 * 
 * @author ChenRui
 * 
 * @param <T>
 */
public class NfyhSoapConnection<T> extends SoapConnection implements
		SoapCallback {

	private Context context;

	private HashMap<String, Object> params;

	private SoapConnectionCallback<T> mListener;

	private IWebserverParser<T> mParser;

	public NfyhSoapConnection(Context context) {
		this.context = context;
	}

	public void setonSoapConnectionCallback(SoapConnectionCallback<T> l) {
		this.mListener = l;
	}

	public void setParser(IWebserverParser<T> parser) {
		this.mParser = parser;
	}

	public void setParams(String name, Object value) {
		if (this.params == null) {
			this.params = new HashMap<String, Object>();
		}
		if (this.params.containsKey(name)) {
			this.params.remove(name); // 移除之前的Key
		}
		this.params.put(name, value);
	}

	public void request(String methodName) {

		// 检查网络
		if (!CommonUtil.checkNetConnected(context)) {
			mListener.onSoapConnectedFalid(new WebServerException(
					WebServerException.CODE_NET_DISCONNECT));
			return;
		}

		String url = ApiController.get().getApiUrl();
		String namespace = context.getString(R.string.soap_namespace);
		this.setSoapCallbackListener(this);
		this.request(url, namespace, methodName, params);
	}

	@Override
	@Deprecated
	public void setSoapCallbackListener(SoapCallback l) {
		super.setSoapCallbackListener(l);
	}

	@Override
	@Deprecated
	public void request(String url, String namespace, String methodName,
			Map<String, Object> params) {
		super.request(url, namespace, methodName, params);
	}

	@Override
	public void onSoapResponse(Object response) {
		String json = response.toString();
		if (TextUtils.isEmpty(json)) {
			mListener.onSoapConnectedFalid(new WebServerException(
					WebServerException.CODE_NULL_DATA));
			return;
		}
		WebServerException result = WebServerException.parser(json);
		if (result.getCode() == 1 || result.getCode() == 200) {
			T data = mParser.parser(result.getData());
			if (data == null) {
				mListener.onSoapConnectedFalid(new WebServerException(
						WebServerException.CODE_NULL_DATA));
			} else {
				mListener.onSoapConnectSuccess(data); // // 调用成功！
			}
		} else {
			mListener.onSoapConnectedFalid(result); // 服务器返回失败内容
		}
	}

	@Override
	public void onSoapError(int code, String msg) {
		mListener.onSoapConnectedFalid(new WebServerException(code, msg));
	}
}
