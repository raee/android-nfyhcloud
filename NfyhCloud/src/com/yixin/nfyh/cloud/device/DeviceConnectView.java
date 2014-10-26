package com.yixin.nfyh.cloud.device;

import android.view.View;

public interface DeviceConnectView {
	void show(String tips, String msg);

	void showError(String tips, String msg);

	void showSuccess(String tips, String msg);

	void dismiss();

	void setContentViewGroup(View view);

	void setName(String name);
}
