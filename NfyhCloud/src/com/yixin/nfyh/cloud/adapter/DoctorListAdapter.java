package com.yixin.nfyh.cloud.adapter;

import io.rong.imkit.RongIM;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rae.core.image.loader.ImageLoader;
import com.rae.core.image.loader.assist.FailReason;
import com.rae.core.image.loader.listener.ImageLoadingListener;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.model.Users;

/**
 * 医生列表
 * 
 * @author ChenRui
 * 
 */
public class DoctorListAdapter extends BaseListAdapter<Users> {

	public DoctorListAdapter(Context context, List<Users> datas) {
		super(context, datas);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Users m = getDataItem(position);
		if (RongIM.getInstance() != null) {
			RongIM.getInstance().startPrivateChat(getContext(), m.getUid(),
					m.getName());
		}
	}

	@Override
	public void fillViewData(AdapterViewHolder absHolder, Users m) {
		final ViewHolder holder = (ViewHolder) absHolder;
		holder.mPtName.setText(m.getName());
		Log.d("DoctorListAdapter", "用户头像：" + m.getHeadImage());
		ImageLoader.getInstance().displayImage(m.getHeadImage(),
				holder.mPtHead, new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {

					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
						// 设置默认的头像
						holder.mPtHead
								.setImageResource(R.drawable.user_default_header);
					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {

					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {

					}
				});
	}

	@Override
	public AdapterViewHolder getViewHolder(View convertView) {
		ViewHolder holder = new ViewHolder();
		holder.mPtName = (TextView) convertView
				.findViewById(R.id.tv_ptinfo_name);
		holder.mPtHead = (ImageView) convertView
				.findViewById(R.id.img_ptinfo_head);
		// holder.mDongtai = (TextView)
		// convertView.findViewById(R.id.tv_ptinfo_desc);
		// holder.mDongtaiDate = (TextView)
		// convertView.findViewById(R.id.tv_ptinfo_date);
		return holder;
	}

	@Override
	public View getItemView(LayoutInflater inflater) {
		return initView(R.layout.item_doctor_info);
	}

	private class ViewHolder extends AdapterViewHolder {
		TextView mPtName;
		ImageView mPtHead;
		// TextView mDongtai;
		// TextView mDongtaiDate;
	}

}
