package com.yixin.nfyh.cloud.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.rui.framework.utils.MediaUtil;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.ConfigServer;

/**
 * 对话框控件
 * 
 * @author 睿
 * 
 */
public class ResultDialog extends Dialog implements View.OnClickListener {
	private static int DefaultAutoCloseTime = 5;
	private View mContentView;
	private LinearLayout mStarView; // 星星
	private LinearLayout mTagViewground;
	private LinearLayout mTitleViewground;
	private LinearLayout mMessageView;
	private int mAutoCloseTime = DefaultAutoCloseTime; // 自动关闭窗口，0为不关闭
	private TextView mTitleView;
	private ImageView mImgTag; // 标签图像
	private int[] mImageRes; // 等级图片
								// private int[] mMusicLevelRes; // 等级音效
	private Handler mHandler;
	private TextView mTextTips;
	private Timer mTimer;
	private List<MediaPlayer> mMediaList; // 播放列表
	private int[] mMusicSays; // 人声提示
	private List<Integer> mBufferMusicRes; // 待播放的声音缓存
	private boolean mHasFocus;
	private ConfigServer mConfig = null;

	public ResultDialog(Context context) {
		super(context, R.style.ResultDialog);
		mConfig = new ConfigServer(context);
		this.mHandler = new Handler(new Handler.Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					int count = msg.arg1;
					mTextTips.setText(count + "秒后自动关闭，或者点击屏幕关闭");
					break;
				case 1:
					if (mHasFocus) {
						setAutoCloseTime(mAutoCloseTime); // 重新计时
						for (int i = 0; i < mBufferMusicRes.size(); i++) {
							mMediaList.add(MediaUtil.playMusic(getContext(),
									mBufferMusicRes.get(i), false));
						}
					}
					break;
				default:
					break;
				}

				return false;
			}
		});

		this.mBufferMusicRes = new ArrayList<Integer>();

		this.mMediaList = new ArrayList<MediaPlayer>();

		this.mImageRes = new int[] { R.drawable.sign_tag_01,
				R.drawable.sign_tag_02 }; // 标签资源

		// this.mMusicLevelRes = new int[] { R.raw.win, R.raw.over };
		// //声音资源，注意要保持云标签资源的数量一至

		this.mMusicSays = new int[] { R.raw.nvli, R.raw.zaishiyixia,
				R.raw.jiayou, R.raw.piaoliang, R.raw.good };

		this.mContentView = LayoutInflater.from(context).inflate(
				R.layout.dialog_result, null);

		this.mContentView.findViewById(android.R.id.closeButton)
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						dismiss();
					}
				});
		this.mContentView.findViewById(android.R.id.content)
				.setOnClickListener(this);

		this.mStarView = (LinearLayout) this.mContentView
				.findViewById(R.id.ll_dialog_star);
		this.mTitleView = (TextView) this.mContentView
				.findViewById(R.id.tv_dialog_result_level);
		this.mMessageView = (LinearLayout) this.mContentView
				.findViewById(R.id.ll_dialog_message);

		this.mTagViewground = (LinearLayout) this.mContentView
				.findViewById(R.id.ll_dialog_tagview);
		this.mTitleViewground = (LinearLayout) this.mContentView
				.findViewById(R.id.ll_dialog_titleview);
		this.mImgTag = (ImageView) this.mContentView
				.findViewById(R.id.img_dialog_tag);

		this.mTextTips = (TextView) this.mContentView
				.findViewById(R.id.tv_dialog_tips);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(mContentView);
		getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		setTagLevel(1);
	}

	/**
	 * 设置自动关闭时间，默认为：0不关闭
	 * 
	 * @param time
	 *            关闭时间
	 */
	public void setAutoCloseTime(int time) {
		this.mAutoCloseTime = time;

		// 自动关闭处理
		if (mAutoCloseTime > 0) {
			if (this.mTimer != null) {
				this.mTimer.cancel(); // 取消之前的定时器
				this.mTimer = null;
			}
			this.mTimer = new Timer();
			this.mTimer.schedule(new TimerTask() {
				private int count = 0;

				@Override
				public void run() {
					if (count >= mAutoCloseTime) {
						dismiss();
						this.cancel();
					}
					int less = mAutoCloseTime - count; // 还剩多少秒
					Message.obtain(mHandler, 0, less, 0).sendToTarget();
					count++;
				}
			}, 1000, 1000);
		}
	}

	@Override
	public void dismiss() {
		if (this.mTimer != null)
			this.mTimer.cancel();

		stopMusic();
		super.dismiss();
	}

	/**
	 * 播放声音
	 * 
	 * @param resid
	 */
	public void playMusic(int resid) {
		mBufferMusicRes.add(resid);
		// this.mMediaList.add(MediaUtil.playMusic(getContext(), resid, false));
	}

	/**
	 * 停止播放
	 */
	public void stopMusic() {
		for (MediaPlayer player : mMediaList) {
			MediaUtil.stopPlayMusic(player); // 停止播放音乐
		}
	}

	/**
	 * 设置星星数量
	 * 
	 * @param num
	 */
	public void setStar(int num) {
		if (mStarView.getChildCount() > 0) {
			mStarView.removeAllViews(); // 移除所有的星星
		}
		num = (num > 0 && num < 6) ? num : 1; // 最小为1个，最大为5个，超出的都作为1

		if (this.mMusicSays.length > num - 1) {
			playMusic(mMusicSays[num - 1]);
		}
		for (int i = 0; i < num; i++) {
			ImageView img = new ImageView(this.getContext());
			img.setImageResource(R.drawable.icon_star);
			mStarView.addView(img);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		this.mTitleView.setText(title);
	}

	/**
	 * 显示消息
	 * 
	 * @param msg
	 */
	public void setMessage(String msg) {
		TextView tv = new TextView(this.getContext());
		tv.setBackgroundResource(R.drawable.dialog_textview_bg);
		tv.setTextColor(Color.parseColor("#8d4e0b"));
		tv.setTextSize(24);
		tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		tv.setText(msg);
		this.mMessageView.addView(tv);
	}

	/**
	 * 设置标签级别,默认为1
	 * 
	 * @param level
	 *            取值范围1-3：好、中等、差
	 */
	public void setTagLevel(int level) {
		int index = level - 1;
		index = (index > 0 && index < 4) ? index : 0;

		if (this.mImageRes.length < index) {
			index = 0;
		}
		// if (this.mMusicLevelRes.length > index)
		// {
		// this.playMusic(mMusicLevelRes[index]);
		// }

		int resid = this.mImageRes[index];
		this.mImgTag.setImageResource(resid);
	}

	/**
	 * 设置警告级别的颜色
	 * 
	 * @param resId
	 *            颜色
	 */
	public void setBackgournd(int resId) {
		this.mTagViewground.setBackgroundColor(resId);
		this.mTitleViewground.setBackgroundColor(resId);
	}

	@Override
	public void show() {
		if (!mConfig.getBooleanConfig(ConfigServer.KEY_AUTO_TIPS)) {
			dismiss();
			return; // 配置告警不提示
		}
		super.show();
		setAutoCloseTime(mAutoCloseTime);
	}

	/**
	 * 重置
	 */
	public void reset() {
		mBufferMusicRes.clear();
		this.mAutoCloseTime = DefaultAutoCloseTime;
		this.mMessageView.removeAllViews();
	}

	/**
	 * 清除消息
	 */
	public void clear() {
		this.mMessageView.removeAllViews();
	}

	@Override
	public void onClick(View v) {
		if (this.mTimer != null) // 停止计时
		{
			this.mTimer.cancel();
			this.mTimer = null;
			this.mTextTips.setText("点击屏幕关闭");
			return;
		}
		this.dismiss();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		Log.i("ttt", this.mTitleView.getText() + "是否有焦点：" + hasFocus);
		this.mHasFocus = hasFocus;

		if (hasFocus) {
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					Message.obtain(mHandler, 1).sendToTarget();
				}
			}, 500);

		} else {
			if (mTimer != null) {
				mTimer.cancel();
				mTimer = null;
			}
			stopMusic();
		}
	}

	/**
	 * 体征比较结果View视图Model
	 * 
	 * @author ChenRui
	 * 
	 */
	public class SignResultModel {
		public String Title, Message;
		public int StarNumber;
	}

}
