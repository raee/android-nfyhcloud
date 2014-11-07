package com.yixin.nfyh.cloud.w;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.model.VersionUpdateModel;

public class VersionUpdateServer extends WebserverConnection {
	private String	mMethodName;
	private String	mJsonUpload;
	private int		mVersionCode;
	
	public VersionUpdateServer(Context context) {
		super(context);
		mMethodName = context.getString(R.string.soap_method_version_update);
		String packageName = context.getPackageName();
		try {
			mVersionCode = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
			JSONObject obj = new JSONObject();
			obj.put("Version", mVersionCode);
			obj.put("PackName", packageName);
			mJsonUpload = obj.toString();
		}
		catch (Exception e) {
			mJsonUpload = "{\"Version\":0,\"PackName\":\"" + packageName + "\"}"; //默认Json
		}
		
	}
	
	public void check(SoapConnectionCallback<VersionUpdateModel> l) {
		NfyhSoapConnection<VersionUpdateModel> conn = new NfyhSoapConnection<VersionUpdateModel>(context);
		conn.setParams("json", mJsonUpload);
		conn.setonSoapConnectionCallback(l);
		conn.setParser(new IWebserverParser<VersionUpdateModel>() {
			
			@Override
			public VersionUpdateModel parser(String json) {
				VersionUpdateModel model = new VersionUpdateModel();
				try {
					JSONObject obj = new JSONObject(json);
					model.setVersionCode(obj.getInt("Version"));
					if (model.getVersionCode() > mVersionCode) {
						model.setUpdateContent(obj.getString("UpdateContent"));
						model.setDownloadUrl(obj.getString("DownloadUrl"));
						model.setVersionName(obj.getString("VersionName"));
					}
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
				return model;
			}
		});
		
		conn.request(mMethodName);
	}
}
