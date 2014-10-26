package com.yixin.nfyh.cloud.ui;

import java.util.Timer;
import java.util.TimerTask;

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

import com.yixin.nfyh.cloud.R;

/**
 * 顶部提示信息
 * 
 * @author MrChenrui
 * 
 */
public class TopMsgView extends LinearLayout implements View.OnClickListener {
	
	private ImageView	imgIcon;
	private TextView	tvMsg;
	private boolean		mKeepShow	= false;	// 保存显示，不自动消失
												
	//	private View		contentView;
	
	public TopMsgView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = LayoutInflater.from(context).inflate(R.layout.view_msg, this);
		this.tvMsg = (TextView) this.findViewById(R.id.tv_msg);
		this.imgIcon = (ImageView) this.findViewById(R.id.img_msg_icon);
		this.setBackgroundResource(R.drawable.actionbar_bg_green);
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				if (mKeepShow) { return; }
				Message.obtain(handler).sendToTarget();
			}
		}, 20 * 1000, 20 * 1000); // 每隔60秒后自动隐藏
		view.setOnClickListener(this);
	}
	
	/**
	 * 不自动消失。
	 * 
	 * @param val
	 */
	public void setKeepShow(boolean val) {
		mKeepShow = val;
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
	
	public ImageView getIconImageView() {
		return imgIcon;
	}
	
	public void stopAnim() {
		imgIcon.clearAnimation();
	}
	
	public void show(ViewGroup parent) {
		if (getId() == -1) {
			parent.addView(this, 0);
			int id = this.getId();
			id = id == -1 ? R.id.msgview : id;
			this.setId(id);
			this.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
			setVisibility(View.VISIBLE);
		}
		else {
			show();
		}
	}
	
	public void show() {
		if (getId() == -1) {
			setId(R.id.msgview);
		}
		this.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
		this.setVisibility(View.VISIBLE);
	}
	
	private Handler	handler	= new Handler(new Handler.Callback() {
								
								@Override
								public boolean handleMessage(Message msg) {
									dismiss();
									return false;
								}
							});
	
	@Override
	public void onClick(View v) {
		this.setVisibility(View.GONE);
	}
	
	public void dismiss() {
		stopAnim();
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
		animation.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				setVisibility(View.GONE);
			}
		});
		
		// 开始动画
		startAnimation(animation);
	}
	
}
