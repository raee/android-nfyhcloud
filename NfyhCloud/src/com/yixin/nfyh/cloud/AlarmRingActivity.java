package com.yixin.nfyh.cloud;

import java.io.IOException;
import java.util.ArrayList;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.rui.framework.ui.WebViewHelper;

import com.rae.alarm.NfyhAlarmEntity;
import com.rae.core.alarm.AlarmEntity;
import com.rae.core.alarm.AlarmUtils;
import com.yixin.nfyh.cloud.activity.SettingDeviceActivity;
import com.yixin.nfyh.cloud.adapter.BaseViewPageAdapter;
import com.yixin.nfyh.cloud.bll.SignCore;
import com.yixin.nfyh.cloud.bll.sign.SignCoreListener;
import com.yixin.nfyh.cloud.ui.InputSignView;
import com.yixin.nfyh.cloud.ui.TopMsgView;

/**
 * 提醒功能
 * 
 * @author Chenrui
 * 
 */
public class AlarmRingActivity extends BaseActivity implements OnClickListener, SignCoreListener
{
	
	private FrameLayout		flAlarmContent;
	
	private int				screenHeight;
	
	private MediaPlayer		mMediaPlayer;
	
	private int				lastTop, lastBottom;
	
	private KeyguardLock	mKeyguardLock;
	
	private WakeLock		mWakeLock;
	
	private Vibrator		mVibrator;
	
	private NfyhAlarmEntity	mAlarmEntity;
	
	private InputSignView	mInputSignView;
	private TopMsgView		mTopMsgView;
	
	private TextView		mRingTipsTextView;
	
	private ImageView		mRingTipsImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 至于锁屏之上
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		
		ViewPager viewPager = (ViewPager) getLayoutInflater().inflate(R.layout.view_viewpage, null);
		setContentView(viewPager);
		
		AlarmEntity entity = getIntent().getParcelableExtra("data");
		mAlarmEntity = new NfyhAlarmEntity(entity);
		
		ArrayList<View> views = new ArrayList<View>();
		View alarmView = getLayoutInflater().inflate(R.layout.activity_alarm, null);
		mRingTipsTextView = (TextView) alarmView.findViewById(R.id.tv_alarm_ring_tips);
		mRingTipsImageView = (ImageView) alarmView.findViewById(R.id.img_alarm_ring_tips);
		alarmView.setOnClickListener(this);
		views.add(alarmView); // 添加View 
		
		String signName = mAlarmEntity.getSignName();
		if (!TextUtils.isEmpty(signName) && !signName.equals("不测量"))
		{
			
			mRingTipsImageView.setVisibility(View.VISIBLE);
			mRingTipsTextView.setVisibility(View.VISIBLE);
			
			mRingTipsImageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.move_left_repeat));
			
			// url 类型处理
			if (signName.toLowerCase().equals("url"))
			{
				View view = loadWebView(mAlarmEntity.getUrl());
				if (view != null) views.add(view);
			}
			else
			{
				View signView = getLayoutInflater().inflate(R.layout.alarm_tab_sign, null);
				mTopMsgView = (TopMsgView) signView.findViewById(R.id.msgview);
				mInputSignView = (InputSignView) signView.findViewById(R.id.sign_alarm_tab);
				mInputSignView.showUploadButton(false);
				mInputSignView.setDataList(new SignCore(this).getSignTypes(signName));
				signView.findViewById(R.id.btn_alarm_tab_connet).setOnClickListener(this);
				signView.findViewById(R.id.btn_alarm_tag_upload).setOnClickListener(this);
				views.add(signView);
			}
		}
		
		viewPager.setAdapter(new BaseViewPageAdapter(views));
		viewPager.setOnPageChangeListener(new OnPageChangeListener()
		{
			
			@Override
			public void onPageSelected(int arg0)
			{
				unVibarte();
				StopAlarmRing();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		// 键盘管理器
		KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		// 电源管理
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		
		//键盘锁
		mKeyguardLock = mKeyguardManager.newKeyguardLock("unlock");
		
		//唤醒锁
		mWakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "PowerManager");
		
		//键盘解锁
		mKeyguardLock.disableKeyguard();
		mWakeLock.setReferenceCounted(false); //设置超时锁
		// 点亮屏幕
		mWakeLock.acquire();
		
		// 开启震动
		mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibarte();
		
		TextView tvContent = (TextView) alarmView.findViewById(R.id.tv_alarm_content);
		TextView tvTtitle = (TextView) alarmView.findViewById(R.id.tv_next_alarm_content);
		TextView tvTime = (TextView) alarmView.findViewById(R.id.tv_alarm_time);
		ImageView imgRingUpImageView = (ImageView) alarmView.findViewById(R.id.img_alarm_ring_up);
		AnimationDrawable drawable = (AnimationDrawable) imgRingUpImageView.getDrawable();
		drawable.start();
		
		tvTtitle.setText(mAlarmEntity.getTitle());
		tvTime.setText(AlarmUtils.dateToString("HH:mm", mAlarmEntity.getTime()));
		viewPager.setOnClickListener(this);
		
		tvContent.setText(mAlarmEntity.getContent());
		
		flAlarmContent = (FrameLayout) alarmView.findViewById(R.id.fl_alarm_content);
		flAlarmContent.setClickable(true);
		flAlarmContent.setOnTouchListener(new flOnTouchListener());
		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenHeight = dm.heightPixels;
		
		initPlayer();
		
	}
	
	// 加载网页
	private View loadWebView(String url)
	{
		if (TextUtils.isEmpty(url)) { return null; }
		
		// 替换参数
		
		url = url.replace("@uid", app.getCurrentUser().getUid());
		url = url.replace("@cookie", app.getCurrentUser().getCookie());
		
		View view = getLayoutInflater().inflate(R.layout.xr_layout_webviewer, null);
		final WebView webView = (WebView) view.findViewById(R.id.xr_webviewer_webView);
		final View layoutView = view.findViewById(R.id.ll_setting_webview_loadding);
		final ImageView loadView = (ImageView) layoutView.findViewById(R.id.img_xr_webview_loadding);
		((AnimationDrawable) loadView.getDrawable()).start();
		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebViewClient(new WebViewClient()
		{
			
			@Override
			public void onPageFinished(WebView view, String url)
			{
				Log.i("rae", "加载Url：" + url);
				
				webView.setVisibility(View.VISIBLE);
				
				((AnimationDrawable) loadView.getDrawable()).stop();
				layoutView.setVisibility(View.GONE);
				super.onPageFinished(view, url);
			}
			
		});
		webView.addJavascriptInterface(new AndroidAlaramRingInterface(), "android"); // 添加Android接口
		WebViewHelper.setDefaultOption(webView); //设置默认
		webView.loadUrl(url);
		return view;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		if (hasFocus && flAlarmContent != null)
		{
			lastTop = flAlarmContent.getTop();
			lastBottom = flAlarmContent.getBottom();
		}
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		StopAlarmRing();// 停止提醒音乐
		unVibarte(); //停止震动
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		// 重新锁上屏幕
		mKeyguardLock.disableKeyguard();
		mWakeLock.release();
	}
	
	/**
	 * 监听touch事件
	 * 
	 * @author zhulin
	 * 
	 */
	private class flOnTouchListener implements View.OnTouchListener
	{
		
		int	lastY;
		
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			switch (event.getAction())
			{
				case MotionEvent.ACTION_DOWN: // 手指按下记录坐标
					lastY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:// 手指移动改变坐标
					int dy = (int) event.getRawY() - lastY;
					int left = v.getLeft();
					int top = v.getTop() + dy;
					int right = v.getRight();
					int bottom = v.getBottom() + dy;
					v.layout(left, top, right, bottom);
					lastY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_UP: // 手指抬起
					int upTop = v.getTop();
					if ((lastTop - upTop) > (screenHeight / 3))
					{
						// 完成
						finish();
					}
					v.layout(v.getLeft(), lastTop, v.getRight(), lastBottom);
					break;
				default:
					break;
			}
			return true;
		}
	}
	
	// 震动
	private void vibarte()
	{
		//等待1秒，震动2秒，等待1秒，震动3秒 
		long[] pattern = { 1000, 1000, 1000, 1000 };
		//-1表示不重复, 如果不是-1, 比如改成0, 表示从前面这个long数组的下标为0的元素开始重复.
		mVibrator.vibrate(pattern, 0);
	}
	
	// 取消震动
	private void unVibarte()
	{
		if (mVibrator.hasVibrator())
		{
			mVibrator.cancel();
		}
	}
	
	private void initPlayer()
	{
		Uri alert = Uri.parse(mAlarmEntity.getRing());
		try
		{
			if (mMediaPlayer == null)
			{
				mMediaPlayer = new MediaPlayer();
				mMediaPlayer.setDataSource(this, alert);
				AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC); // 播放流媒体的类型
				mMediaPlayer.setLooping(true); // 设置循环播放
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_PLAY_SOUND);
				mMediaPlayer.prepare(); // 异步装载音乐流
				mMediaPlayer.setOnPreparedListener(new OnPreparedListener()
				{
					@Override
					public void onPrepared(MediaPlayer mp)
					{
						PlayAlarmRing();
					}
				});
			}
		}
		catch (IllegalStateException e)
		{
			Toast.makeText(this, "闹钟播放失败！", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		catch (IOException e)
		{
			Toast.makeText(this, "闹钟播放失败！", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		catch (Exception e)
		{
			Toast.makeText(this, "闹钟播放失败！" + e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	
	/**
	 * 播放系统声音
	 */
	private void PlayAlarmRing()
	{
		if (mMediaPlayer != null && !mMediaPlayer.isPlaying())
		{
			mMediaPlayer.start();
		}
	}
	
	/**
	 * 关闭系统声音
	 */
	private void StopAlarmRing()
	{
		if (mMediaPlayer != null && mMediaPlayer.isPlaying())
		{
			mMediaPlayer.stop();
			mMediaPlayer.release(); // 回收
		}
		mMediaPlayer = null;
	}
	
	@Override
	public void onClick(View v)
	{
		
		switch (v.getId())
		{
			case R.id.btn_alarm_tab_connet:
				getNfyhApplication().connect();
				startActivity(new Intent(this, SettingDeviceActivity.class));
				finish();
				break;
			case R.id.btn_alarm_tag_upload:
				if (mInputSignView != null)
				{
					mInputSignView.upload(this);
				}
				break;
			
			default:
				StopAlarmRing();
				unVibarte();
				break;
		}
		
	}
	
	@Override
	public void onSignCoreSuccess(int code, String msg)
	{
		mTopMsgView.setMsg(msg);
		mTopMsgView.show();
	}
	
	@Override
	public void onSignCoreError(int code, String msg)
	{
		mTopMsgView.setBackgroundColor(getResources().getColor(R.color.mihuang));
		mTopMsgView.setMsg(msg);
		mTopMsgView.show();
	}
	
	@Override
	public void onUploading()
	{
		
	}
	
	// WebView 接口
	class AndroidAlaramRingInterface
	{
		@JavascriptInterface
		public void close()
		{
			finish();
		}
	}
}
