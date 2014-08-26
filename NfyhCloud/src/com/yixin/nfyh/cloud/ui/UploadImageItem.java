package com.yixin.nfyh.cloud.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yixin.nfyh.cloud.R;

/**
 * 上传图片单个控件
 * 
 * @author MrChenrui
 * 
 */
public class UploadImageItem extends RelativeLayout
{

	private ImageView	img;
	private TextView	tvPro;
	private Handler		handler	= new Handler(new Handler.Callback()
								{

									@Override
									public boolean handleMessage(Message msg)
									{
										alphaLayout.setVisibility(View.VISIBLE);
										tvPro.setText(msg.obj + "");
										return false;
									}
								});

	private View		alphaLayout;
	private View		rootView;

	public UploadImageItem(Context context)
	{
		super(context);
		rootView = LayoutInflater.from(context).inflate(
				R.layout.view_item_upload_photo, this);
		img = (ImageView) rootView.findViewById(R.id.img_upload_photo);
		tvPro = (TextView) rootView.findViewById(R.id.tv_proccess);
		this.alphaLayout = rootView.findViewById(R.id.rl_alphe_layout);
		hideProgress();
	}

	public void hideProgress()
	{
		this.alphaLayout.setVisibility(View.GONE);
	}

	public void setImage(Drawable img)
	{
		this.img.setImageDrawable(img);
	}

	public void setProgress(float index)
	{
		Message.obtain(handler, 0, index).sendToTarget();
	}

}
