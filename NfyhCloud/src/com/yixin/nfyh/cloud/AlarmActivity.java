package com.yixin.nfyh.cloud;

import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.rui.framework.utils.DateUtil;

import com.yixin.nfyh.cloud.alarm.AlarmBinder;
import com.yixin.nfyh.cloud.alarm.AlarmServer;
import com.yixin.nfyh.cloud.model.Clocks;

/**
 * 提醒功能
 * 
 * @author Chenrui
 * 
 */
public class AlarmActivity extends Activity implements OnClickListener {
	
	private FrameLayout				flAlarmContent;
	
	private int						screenHeight;
	
	private MediaPlayer				mMediaPlayer;
	
	private Clocks					model;
	
	private int						lastTop, lastBottom;
	
	private AlarmBinder				binder;
	
	private AlarmSericeConnection	conn;
	
	private boolean					isExcute	= false;
	
	private KeyguardLock			mKeyguardLock;
	
	private WakeLock				mWakeLock;
	
	private Vibrator				mVibrator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);//没有标题栏的
		super.onCreate(savedInstanceState);
		// 至于锁屏之上
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		
		View view = getLayoutInflater().inflate(R.layout.activity_alarm, null);
		setContentView(view);
		Intent intent = getIntent();
		
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
		
		if (intent.getExtras().containsKey(Intent.EXTRA_TEXT)) {
			model = (Clocks) intent.getExtras().getSerializable(Intent.EXTRA_TEXT);
			TextView tvContent = (TextView) findViewById(R.id.tv_alarm_content);
			TextView tvTtitle = (TextView) findViewById(R.id.tv_next_alarm_content);
			TextView tvTime = (TextView) findViewById(R.id.tv_alarm_time);
			tvContent.setText(model.getContent());
			tvTtitle.setText(model.getTitle());
			Calendar calander = Calendar.getInstance();
			calander.setTime(model.getStartDate());
			tvTime.setText(DateUtil.getDateString("HH:mm", calander));
			view.setOnClickListener(this);
		}
		flAlarmContent = (FrameLayout) findViewById(R.id.fl_alarm_content);
		flAlarmContent.setClickable(true);
		flAlarmContent.setOnTouchListener(new flOnTouchListener());
		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenHeight = dm.heightPixels;
		initPlayer();
		PlayAlarmRing();
		Intent service = new Intent(this, AlarmServer.class);
		conn = new AlarmSericeConnection();
		bindService(service, conn, Context.BIND_AUTO_CREATE); // 绑定闹钟服务
	}
	
	private class AlarmSericeConnection implements ServiceConnection {
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (AlarmBinder) service;
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (hasFocus && flAlarmContent != null) {
			lastTop = flAlarmContent.getTop();
			lastBottom = flAlarmContent.getBottom();
		}
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		excute(); // 执行提醒
		unbindService(conn); // 解绑服务
		StopAlarmRing();// 停止提醒音乐
		unVibarte(); //停止震动
		
	}
	
	@Override
	protected void onPause() {
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
	private class flOnTouchListener implements View.OnTouchListener {
		
		int	lastY;
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
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
					if ((lastTop - upTop) > (screenHeight / 3)) {
						excute();
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
	private void vibarte() {
		//等待1秒，震动2秒，等待1秒，震动3秒 
		long[] pattern = { 1000, 1000, 1000, 1000 };
		//-1表示不重复, 如果不是-1, 比如改成0, 表示从前面这个long数组的下标为0的元素开始重复.
		mVibrator.vibrate(pattern, 0);
	}
	
	// 取消震动
	private void unVibarte() {
		if (mVibrator.hasVibrator()) {
			mVibrator.cancel();
		}
	}
	
	private void initPlayer() {
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		try {
			if (mMediaPlayer == null) {
				mMediaPlayer = new MediaPlayer();
				mMediaPlayer.setDataSource(this, alert);
				AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
					mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC); // 播放流媒体的类型
					mMediaPlayer.setLooping(true); // 设置循环播放
					audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_PLAY_SOUND);
					mMediaPlayer.prepare(); // 异步装载音乐流
					mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
						
						@Override
						public void onPrepared(MediaPlayer mp) {
							PlayAlarmRing();
						}
					});
				}
			}
		}
		catch (IllegalStateException e) {
			Toast.makeText(this, "闹钟播放失败！", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		catch (IOException e) {
			Toast.makeText(this, "闹钟播放失败！", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行
	 */
	private void excute() {
		Log.i("ttt", "执行");
		if (!isExcute) {
			isExcute = true;
			binder.excuteAlarm(model);
			this.finish();
		}
	}
	
	/**
	 * 播放系统声音
	 */
	private void PlayAlarmRing() {
		if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
			mMediaPlayer.start();
		}
	}
	
	/**
	 * 关闭系统声音
	 */
	private void StopAlarmRing() {
		if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
			mMediaPlayer.stop();
			mMediaPlayer.release(); // 回收
		}
		mMediaPlayer = null;
	}
	
	@Override
	public void onClick(View v) {
		StopAlarmRing();
		unVibarte();
	}
}
