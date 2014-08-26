//package cn.rui.framework.utils;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//
//import android.util.Log;
//
//public class HttpUtil
//{
//
//	/**
//	 * HTTP回调接口
//	 * 
//	 * @author 睿
//	 * 
//	 */
//	public interface OnHttpCallback
//	{
//		void OnResponseSuccess(int code, String html, HttpResponse response);
//
//		void OnResonseFileSuccess(int code, InputStream stream,
//				HttpResponse response);
//
//		void OnHttpError(int code, String msg);
//	}
//
//	private URL				url;
//	private HttpResponse	mHttpResponse;
//	private HttpEntity		mHttpEntity;
//	private OnHttpCallback	callback;
//	private String			strURL;
//
//	public HttpUtil(String url)
//	{
//		this.url = defaultUrl(url);
//		this.strURL = url;
//	}
//
//	private URL defaultUrl(String url)
//	{
//		try
//		{
//			return new URL(url);
//		}
//		catch (MalformedURLException e)
//		{
//			try
//			{
//				return new URL("www.baidu.com");
//			}
//			catch (MalformedURLException e1)
//			{
//				e1.printStackTrace();
//			}
//		}
//		return null;
//	}
//
//	public void request()
//	{
//		new AsysnHttpRequest().start();
//	}
//
//	public void setResponseListener(OnHttpCallback l)
//	{
//		this.callback = l;
//	}
//
//	private class AsysnHttpRequest extends Thread
//	{
//
//		@Override
//		public void run()
//		{
//			try
//			{
//				// 生成一个请求对象
//				HttpGet httpGet = new HttpGet(strURL);
//				httpGet.setHeader("Accept-Charset", "GBK");
//
//				// 生成一个Http客户端对象
//				HttpClient httpClient = new DefaultHttpClient();
//
//				// 下面使用Http客户端发送请求，并获取响应内容
//				InputStream inputStream = null;
//
//				// 发送请求并获得响应对象
//				mHttpResponse = httpClient.execute(httpGet);
//
//				int resopnseCode = mHttpResponse.getStatusLine()
//						.getStatusCode();
//
//				if (resopnseCode != 200)
//				{
//					if (callback != null)
//						callback.OnHttpError(resopnseCode, "请求错误，无法进行正常的请求。");
//					return;
//				}
//
//				// 获得响应的消息实体
//				mHttpEntity = mHttpResponse.getEntity();
//
//				// 获取一个输入流
//				inputStream = mHttpEntity.getContent();
//
//				callback.OnResonseFileSuccess(resopnseCode, inputStream,
//						mHttpResponse);
//
//				BufferedReader bufferedReader = new BufferedReader(
//						new InputStreamReader(inputStream));
//
//				String result = "";
//				String line = "";
//
//				while (null != (line = bufferedReader.readLine()))
//				{
//					result += line;
//				}
//
//				// 将结果打印出来，可以在LogCat查看
//				Log.i("nettest", result);
//
//				if (callback != null)
//					callback.OnResponseSuccess(resopnseCode, result,
//							mHttpResponse);
//
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//				if (callback != null)
//					callback.OnHttpError(-1, e.getMessage());
//			}
//		}
//
//	}
//
//}
