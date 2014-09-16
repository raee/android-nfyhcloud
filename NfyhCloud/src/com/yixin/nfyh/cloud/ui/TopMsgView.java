package com.yixin.nfyh.cloud.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.yixin.nfyh.cloud.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 顶部提示信息
 * 
 * @author MrChenrui
 * 
 */
public class TopMsgView extends LinearLayout implements View.OnClickListener {
	
	private ImageView	imgIcon;
	private TextView	tvMsg;
	private View		contentView;
	
	public TopMsgView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = LayoutInflater.from(context).inflate(R.layout.view_msg, this);
		this.tvMsg = (TextView) this.findViewById(R.id.tv_msg);
		this.imgIcon = (ImageView) this.findViewById(R.id.img_msg_icon);
		this.setBackgroundResource(R.drawable.actionbar_bg_green);
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				Message.obtain(handler).sendToTarget();
			}
		}, 60 * 1000, 60 * 1000); // 每隔60秒后自动隐藏
		view.setOnClickListener(this);
	}
	
	public void setMsg(String msg) {
		tvMsg.setText(msg);
	}
	
	public void setIcon(int resId) {
		imgIcon.setImageResource(resId);
	}
	
	public void anim() {
		Animation animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.fade);
		imgIcon.startAnimation(animation);
	}
	
	public void stopAnim() {
		imgIcon.clearAnimation();
	}
	
	public void show(ViewGroup parent) {
		contentView = parent.findViewById(android.R.id.content);
		if (contentView == null) {
			parent.addView(this, 0);
			int id = this.getId();
			id = id == -1 ? android.R.id.content : id;
			this.setId(id);
			this.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
		}
		
		if (this.contentView != null && this.getVisibility() == View.GONE) {
			this.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_in_right));
			this.setVisibility(View.VISIBLE);
		}
	}
	
	private Handler	handler	= new Handler(new Handler.Callback() {
								
								@Override
								public boolean handleMessage(Message msg) {
									if (contentView != null) {
										contentView.setVisibility(View.GONE);
									}
									return false;
								}
							});
	
	@Override
	public void onClick(View v) {
		this.setVisibility(View.GONE);
	}
}
