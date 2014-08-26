package com.yixin.nfyh.cloud.w;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.SparseArray;

/**
 * Webserver调用异常返回对象
 * 
 * @author MrChenrui
 * 
 */
public class WebServerException
{

	private int									code				= 0;

	private String								message				= "";

	public static final int						CODE_NET_DISCONNECT	= -1;							// 没有网络连接

	public static final int						CODE_NULL_DATA		= -2;							// 数据解析为空

	public static final int						CODE_OK				= 1;							// 调用成功

	public static final int						CODE_ERROR			= 0;							// 服务器返回错误信息

	private static final SparseArray<String>	Messages			= new SparseArray<String>();

	private String								data;
	static
	{
		Messages.put(CODE_NET_DISCONNECT, "网络连接没有打开！");
		Messages.put(500, "服务器发生错误！code:500");
		Messages.put(505, "服务器发生错误！code:500");
		Messages.put(404, "接口不存在！code:500");
		Messages.put(CODE_NULL_DATA, "服务器返回数据为空！");
	}

	public WebServerException()
	{
	}

	public WebServerException(String msg)
	{
		this.setMessage(msg);
	}

	public WebServerException(int code)
	{
		this.setCode(code);
		this.message = Messages.get(code);
	}

	public WebServerException(int code, String msg)
	{
		this.setCode(code);
		if (Messages.get(code) != null)
		{
			this.message = Messages.get(code);
		}
		else
		{
			this.setMessage(msg);
		}
	}

	public static WebServerException parser(String json)
	{
		WebServerException result = new WebServerException();
		JSONObject obj;
		try
		{
			obj = new JSONObject(json);
			result.setCode(obj.getInt("status"));
			result.setMessage(obj.getString("message"));
			result.setData(obj.getString("data"));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			result.setCode(1);
			result.setData(json);
			result.setMessage("数据解析错误！");
		}
		return result;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}
}
