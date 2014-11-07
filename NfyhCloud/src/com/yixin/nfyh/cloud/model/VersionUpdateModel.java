package com.yixin.nfyh.cloud.model;

/**
 * 版本更新
 * @author ChenRui
 *
 */
public class VersionUpdateModel {
	private int		versionCode;
	private String	updateContent;
	private String	downloadUrl;
	private String versionName;
	
	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getVersionCode() {
		return versionCode;
	}
	
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	
	public String getUpdateContent() {
		return updateContent;
	}
	
	public void setUpdateContent(String updateContent) {
		this.updateContent = updateContent;
	}
	
	public String getDownloadUrl() {
		return downloadUrl;
	}
	
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	
}
