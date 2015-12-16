package com.rae.im.sdk.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

/**
 * “Ï≤ΩHTTP«Î«Û
 * 
 * @author ChenRui
 * 
 */
public class RaeAsyncHttpClient {
	private final DefaultHttpClient mClient;
	private HashMap<String, String> mHeader;
	private List<NameValuePair> mHttpParams;

	public RaeAsyncHttpClient() {
		mClient = new DefaultHttpClient();
		mHeader = new HashMap<String, String>();
		mHttpParams = new ArrayList<NameValuePair>();
	}

	public void post(String uri, IRaeAsyncResponse l) {
		if (uri == null || l == null)
			return;
		new HttpAsyncTask(l).execute(uri);
	}

	private HttpUriRequest initHttpRequest(String uri)
			throws UnsupportedEncodingException {

		HttpPost request = new HttpPost(uri);

		for (Entry<String, String> header : mHeader.entrySet()) {
			request.addHeader(header.getKey(), header.getValue());
		}

		HttpEntity postParams = new UrlEncodedFormEntity(mHttpParams);
		request.setEntity(postParams);

		return request;

	}

	public void addHead(String key, Object value) {
		if (key == null || value == null)
			return;
		mHeader.put(key, value.toString());
	}

	public void addHttpRequestParam(String key, Object value) {
		if (key == null || value == null)
			return;
		BasicNameValuePair item = new BasicNameValuePair(key, value.toString());
		mHttpParams.add(item);
	}

	class HttpAsyncTask extends AsyncTask<String, Integer, HttpResponse> {

		private IRaeAsyncResponse mLinstener;

		public HttpAsyncTask(IRaeAsyncResponse l) {
			mLinstener = l;
		}

		@Override
		protected HttpResponse doInBackground(String... urls) {
			HttpResponse response = null;

			try {
				response = mClient.execute(initHttpRequest(urls[0]));
			} catch (Exception e) {
				mLinstener.onHttpFaild(0, null, e);
			}

			return response;
		}

		@Override
		protected void onPostExecute(HttpResponse response) {
			super.onPostExecute(response);
			if (response == null)
				return;
			try {

				int statusCode = response.getStatusLine().getStatusCode(); // ∑µªÿ¬Î

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));

				String inputLine;
				StringBuffer buf = new StringBuffer();

				while ((inputLine = reader.readLine()) != null) {
					buf.append(inputLine);
				}

				reader.close();

				if (statusCode != 200) {
					mLinstener.onHttpFaild(statusCode, buf.toString(), null);
				} else {
					mLinstener.onHttpSuccess(statusCode, buf.toString());
				}

			} catch (Exception e) {
				mLinstener.onHttpFaild(0, null, e);
			}

		}
	}

}
