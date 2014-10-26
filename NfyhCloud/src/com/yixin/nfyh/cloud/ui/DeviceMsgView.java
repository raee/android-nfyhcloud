package com.yixin.nfyh.cloud.ui;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.device.DeviceConnectView;

public class DeviceMsgView extends LinearLayout implements DeviceConnectView,
		View.OnClickListener {

	private ImageView	mLoadingImageView;
	private ImageView	mLoadingBgImageView;
	private TextView	mMsgTextView;
	private TextView	mTipsTextView;
	private TextView	mNameTextView;
	private Button		mConncetButton;
	private ImageView	mIconImageView;
	private Context		mContext;
	private ViewGroup	mContentView;

	// private boolean mShowable = true;

	public DeviceMsgView(Context context) {
		super(context, null);
		mContext = context;
		View view = LayoutInflater.from(context).inflate(
				R.layout.view_device_connect_msg, this);
		this.mNameTextView = (TextView) view
				.findViewById(R.id.tv_device_connet_name);
		this.mTipsTextView = (TextView) view
				.findViewById(R.id.tv_device_connet_tips);
		this.mMsgTextView = (TextView) view
				.findViewById(R.id.tv_device_connect_msg);
		this.mLoadingBgImageView = (ImageView) view
				.findViewById(R.id.img_device_connect_loading_bg);
		this.mLoadingImageView = (ImageView) view
				.findViewById(R.id.img_device_connect_loading);
		this.mIconImageView = (ImageView) view
				.findViewById(R.id.img_device_connect_icon);
		this.mConncetButton = (Button) view
				.findViewById(R.id.btn_device_connect);
		this.mConncetButton.setOnClickListener(this);
		setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		// setWidth(LayoutParams.MATCH_PARENT);
		// setHeight(LayoutParams.MATCH_PARENT);
		// setFocusable(true);
		// setBackgroundDrawable(new BitmapDrawable());
		// setContentView(view);
	}

	@Override
	public void setName(final String name) {
		mNameTextView.post(new Runnable() {

			@Override
			public void run() {
				mNameTextView.setText(name);
			}
		});
	}

	public void setShowable(boolean val) {
		// this.mShowable = val;
	}

	public void setTips(final String msg) {
		if (TextUtils.isEmpty(msg)) {
			return;
		}
		mTipsTextView.post(new Runnable() {

			@Override
			public void run() {
				mTipsTextView.setText(msg);
			}
		});
	}

	public void setMsg(final String msg) {
		mMsgTextView.post(new Runnable() {

			@Override
			public void run() {

				mMsgTextView.setText(msg);
			}
		});
	}

	public void startAnim() {
		mLoadingImageView.post(new Runnable() {

			@Override
			public void run() {
				if (mLoadingImageView.getVisibility() != View.VISIBLE) {
					mLoadingImageView.setVisibility(View.VISIBLE);
				}
				mLoadingImageView.startAnimation(AnimationUtils.loadAnimation(
						getContext(), R.anim.spinning));
			}
		});

	}

	public void stopAnim() {
		mLoadingImageView.post(new Runnable() {

			@Override
			public void run() {
				mLoadingImageView.clearAnimation();
				mLoadingImageView.setVisibility(View.GONE);
			}
		});
	}

	private void initView(final int backgroundId, final int iconId,
			final String btnText) {
		mLoadingBgImageView.post(new Runnable() {

			@Override
			public void run() {
				mLoadingBgImageView.setImageResource(backgroundId);
				mIconImageView.setImageResource(iconId);
				if (btnText.equals("")) {
					mConncetButton.setVisibility(View.GONE);
				} else {
					mConncetButton.setVisibility(View.VISIBLE);
					mConncetButton.setText(btnText);
				}
			}
		});
	}

	private void show() {
		// if (!mShowable) {
		// return;
		// }
		// if (isShowing()) {
		// return;
		// }
		if (mContentView == null) {
			mContentView = (ViewGroup) ((Activity) mContext).getWindow()
					.getDecorView();
		}
		setName(((NfyhApplication) getContext().getApplicationContext())
				.getApiMonitor().getDeviceInfo().getDeviceName());
		// showAtLocation(mContentView, Gravity.TOP, 0, 0);
	}

	@Override
	public void setContentViewGroup(View view) {
		this.mContentView = (ViewGroup) view;
	}

	@Override
	public void dismiss() {
		// super.dismiss();
		stopAnim();
	}

	@Override
	public void onClick(View v) {
		if (mConncetButton.getText().equals("开始测量")) {
			dismiss();
		} else if (mConncetButton.getText().equals("断开连接")) {
			((NfyhApplication) getContext().getApplicationContext())
					.disconnect();
		} else {
			((NfyhApplication) getContext().getApplicationContext()).connect();
		}
	}

	// private Context getContext() {
	// return mContext;
	// }

	@Override
	public void show(String tips, String msg) {
		setMsg(msg);
		setTips(tips);
		startAnim();
		initView(R.drawable.device_connect_search_bg, R.drawable.icon_search,
				"");
		show();
	}

	@Override
	public void showError(String tips, String msg) {
		setMsg(msg);
		setTips(tips);
		stopAnim();
		initView(R.drawable.device_connect_error_bg,
				R.drawable.icon_device_info, "重新连接");
		show();
	}

	@Override
	public void showSuccess(String tips, String msg) {
		setMsg(msg);
		setTips(tips);
		stopAnim();
		initView(R.drawable.device_connect_search_bg,
				R.drawable.icon_device_connected, "开始测量");
		show();
	}
}
