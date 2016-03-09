package com.yixin.nfyh.cloud.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.model.GanyuInfo;
import com.yixin.nfyh.cloud.ui.TimerToast;

/**
 * 体征干预
 * 
 * @author ChenRui
 * 
 */
public class AddGanyuActivity extends BaseActivity {
	private TextView mContentEditText;
	private TextView mTitleEditText;
	private TextView mSignTypeNameTextView;
	private TextView mSignDateTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View view = getLayoutInflater().inflate(R.layout.fm_ptinfo_ganyu_add,
				null);
		setContentView(view);
		showHomeAsUp();

		mContentEditText = (TextView) view.findViewById(R.id.et_ganyu_content);

		mTitleEditText = (TextView) view.findViewById(R.id.et_ganyu_title);

		view.findViewById(R.id.ll_ptinfo_ganyu_add_sign).setOnClickListener(
				this);
		view.findViewById(R.id.ll_ptinfo_ganyu_add_date).setOnClickListener(
				this);
		mSignTypeNameTextView = (TextView) view
				.findViewById(R.id.tv_ptinfo_ganyu_add_sign_name);
		mSignDateTextView = (TextView) view
				.findViewById(R.id.tv_ptinfo_ganyu_add_sign_date);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null && bundle.containsKey("data")) {
			GanyuInfo m = bundle.getParcelable("data");
			mContentEditText.setText(m.getContent());
			mTitleEditText.setText(m.getTitle());
			mSignTypeNameTextView.setText(m.getSignTypeName());
			mSignDateTextView.setText(m.getDate());

			setTitle(m.getTitle());
		} else {
			TimerToast.makeText(this, "data 参数错误！", Toast.LENGTH_SHORT).show();
			finish();
		}

	}

}
