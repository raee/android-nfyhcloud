package com.rae.im.sdk.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.os.AsyncTask;

/**
 * 异步HTTP请求
 * 
 * @author ChenRui
 * 
 */
public class RaeAsyncHttpClient
{
	private DefaultHttpClient		mClient;
	private HashMap<String, String>	mHeader;
	private List<NameValuePair>		mHttpParams;
	
	public RaeAsyncHttpClient()
	{
		mHeader = new HashMap<String, String>();
		mHttpParams = new ArrayList<NameValuePair>();
		
		mClient = getNewHttpClient();
		
	}
	
	public DefaultHttpClient getNewHttpClient()
	{
		try
		{
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			
			SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));
			
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
			
			return new DefaultHttpClient(ccm, params);
		}
		catch (Exception e)
		{
			return new DefaultHttpClient();
		}
	}
	
	public void post(String uri, IRaeAsyncResponse l)
	{
		if (uri == null || l == null) return;
		new HttpAsyncTask(l).execute(uri);
	}
	
	private HttpUriRequest initHttpRequest(String uri) throws UnsupportedEncodingException
	{
		
		HttpPost request = new HttpPost(uri);
		
		for (Entry<String, String> header : mHeader.entrySet())
		{
			request.addHeader(header.getKey(), header.getValue());
		}
		
		HttpEntity postParams = new UrlEncodedFormEntity(mHttpParams);
		request.setEntity(postParams);
		
		return request;
		
	}
	
	public void addHead(String key, Object value)
	{
		if (key == null || value == null) return;
		mHeader.put(key, value.toString());
	}
	
	public void addHttpRequestParam(String key, Object value)
	{
		if (key == null || value == null) return;
		BasicNameValuePair item = new BasicNameValuePair(key, value.toString());
		mHttpParams.add(item);
	}
	
	class HttpAsyncTask extends AsyncTask<String, Integer, HttpResponse>
	{
		
		private IRaeAsyncResponse	mLinstener;
		
		public HttpAsyncTask(IRaeAsyncResponse l)
		{
			mLinstener = l;
		}
		
		@Override
		protected HttpResponse doInBackground(String... urls)
		{
			HttpResponse response = null;
			
			try
			{
				response = mClient.execute(initHttpRequest(urls[0]));
			}
			catch (SSLPeerUnverifiedException e)
			{
				// 证书错误
				mLinstener.onHttpFaild(0, "浏览器证书错误，请与你们的网络管理员联系。", e);
			}
			catch (Exception e)
			{
				mLinstener.onHttpFaild(0, null, e);
			}
			
			return response;
		}
		
		@Override
		protected void onPostExecute(HttpResponse response)
		{
			super.onPostExecute(response);
			if (response == null) return;
			try
			{
				
				int statusCode = response.getStatusLine().getStatusCode(); // 返回码
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				
				String inputLine;
				StringBuffer buf = new StringBuffer();
				
				while ((inputLine = reader.readLine()) != null)
				{
					buf.append(inputLine);
				}
				
				reader.close();
				
				if (statusCode != 200)
				{
					mLinstener.onHttpFaild(statusCode, buf.toString(), null);
				}
				else
				{
					mLinstener.onHttpSuccess(statusCode, buf.toString());
				}
				
			}
			catch (Exception e)
			{
				mLinstener.onHttpFaild(0, null, e);
			}
			
		}
	}
	
	public class SSLSocketFactoryEx extends SSLSocketFactory
	{
		
		SSLContext	sslContext	= SSLContext.getInstance("TLS");
		
		public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
		{
			super(truststore);
			
			TrustManager tm = new X509TrustManager()
			{
				public java.security.cert.X509Certificate[] getAcceptedIssuers()
				{
					return null;
				}
				
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException
				{
				}
				
				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException
				{
				}
			};
			sslContext.init(null, new TrustManager[] { tm }, null);
		}
		
		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException
		{
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}
		
		@Override
		public Socket createSocket() throws IOException
		{
			return sslContext.getSocketFactory().createSocket();
		}
	}
	
}
