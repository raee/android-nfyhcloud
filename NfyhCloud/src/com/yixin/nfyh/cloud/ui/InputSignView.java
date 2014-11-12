package com.yixin.nfyh.cloud.ui;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.adapter.SignTypeItemGridViewAdapter;
import com.yixin.nfyh.cloud.bll.SignCore;
import com.yixin.nfyh.cloud.bll.sign.SignCoreListener;
import com.yixin.nfyh.cloud.dialog.DialogPopupWindowListener;
import com.yixin.nfyh.cloud.model.SignTypes;

/**
 * 体征输入视图
 * 
 * @author ChenRui
 * 
 */
public class InputSignView extends LinearLayout {
	public static final int TYPE_NORMAL = 1;
	public static final int TYPE_BLUETOOTH = -1;

	private SignTypeItemGridViewAdapter mSignTypeAdapter;
	private ListView mSignListView;
	private boolean mHasDataChange = true; // 数据是否发生改变

	private int mShowType = TYPE_NORMAL;
	private SignCore mSignCore;
	private SignCoreListener mListener;
	private Button mUploadButton;

	public InputSignView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public InputSignView(Context context) {
		super(context);
		initView();
	}

	public InputSignView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public int getShowType() {
		return mShowType;
	}

	public void setShowType(int type) {
		mShowType = type;
		mSignTypeAdapter.setShowType(type);
	}

	/**
	 * 数据是否发生改变
	 * 
	 * @return
	 */
	public boolean hasChanage() {
		return mHasDataChange;
	}

	public void setChange(boolean val) {
		mHasDataChange = val;
		if (mHasDataChange) {
			mUploadButton.setEnabled(true);
		}
	}

	public void setUp() {
		mSignTypeAdapter.loadLastValues(); // 加载上次体征
	}

	// 初始化视图
	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.view_sign, this);
		mSignTypeAdapter = new SignTypeItemGridViewAdapter(this.getContext(),
				new DialogListener());// 适配器
		mSignListView = (ListView) this.findViewById(R.id.lv_sign_detail_types);
		mUploadButton = (Button) findViewById(R.id.btn_sign_upload);
		mSignListView.setAdapter(mSignTypeAdapter);
		mSignListView.setOnItemClickListener(mSignTypeAdapter); // 体征项点击
		mUploadButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				upload(mListener);
			}
		});
	}

	// 设置体征数据，初始化必须调用。
	public void setDataList(List<SignTypes> datas) {
		mSignTypeAdapter.setDataList(datas);
		mSignTypeAdapter.notifyDataSetChanged();
	}

	public List<SignTypes> getDataList() {
		return mSignTypeAdapter.getDataList();
	}

	/**
	 * 上传体征
	 * 
	 * @param listener
	 *            结果回调
	 */
	public void upload(SignCoreListener listener) {
		if (!mHasDataChange) {
			listener.onSignCoreError(-1, "数据没发生改变不需要上传。");
			return;
		}

		if (mSignCore == null) {
			mSignCore = new SignCore(getContext());
			this.mListener = listener;
		}
		mHasDataChange = false; // 上传后不再上传。
		mSignCore.sysnc(getDataList(), mListener);
	}

	/**
	 * 是否显示上传按钮
	 * 
	 * @param show
	 */
	public void showUploadButton(boolean show) {
		mUploadButton.setEnabled(show);
		mUploadButton.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	public void showTipsWindow(DialogInterface.OnDismissListener l) {
		if (l != null) {
			mSignTypeAdapter.getDialog().setOnDismissListener(l);
		}
		mSignTypeAdapter.showTipsWindowList(getDataList());

	}

	class DialogListener implements DialogPopupWindowListener {

		@Override
		public boolean getdialogValidate(String... values) {
			return true;
		}

		@Override
		public void onDialogCancle(PopupWindow dialog, String... values) {

		}

		@Override
		public void onDialogChange(PopupWindow dialog, int which, String values) {

		}

		@Override
		public void onDialogFinsh(PopupWindow dialog, int which, String values) {
			SignTypes m = mSignTypeAdapter.getDataItem(which);
			m.setDefaultValue(values);
			mSignTypeAdapter.notifyDataSetChanged();
			setChange(true);
			mSignTypeAdapter.showTipsWindow(m); // 显示提示
			dialog.dismiss();
		}
	}
}
