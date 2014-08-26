package com.yixin.nfyh.cloud;

import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
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
public class AlarmActivity extends Activity implements OnClickListener
{

	private FrameLayout				flAlarmContent;

	private int						screenHeight;

	private MediaPlayer				mMediaPlayer;

	private Clocks					model;

	private int						lastTop, lastBottom;

	private AlarmBinder				binder;

	private AlarmSericeConnection	conn;

	private boolean					isExcute	= false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		View view = getLayoutInflater().inflate(R.layout.activity_alarm, null);
		setContentView(view);
		Intent intent = getIntent();
		if ( intent.getExtras().containsKey(Intent.EXTRA_TEXT) )
		{
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

	private class AlarmSericeConnection implements ServiceConnection
	{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			binder = (AlarmBinder) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name)
		{
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		if ( hasFocus && flAlarmContent != null )
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
		excute(); // 执行提醒
		unbindService(conn); // 解绑服务
		StopAlarmRing();// 停止提醒音乐
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
					if ( (lastTop - upTop) > (screenHeight / 3) )
					{
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

	private void initPlayer()
	{
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		try
		{
			if ( mMediaPlayer == null )
			{
				mMediaPlayer = new MediaPlayer();
				mMediaPlayer.setDataSource(this, alert);
				AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				if ( audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0 )
				{
					mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC); // 播放流媒体的类型
					mMediaPlayer.setLooping(true); // 设置循环播放
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
	}

	/**
	 * 执行
	 */
	private void excute()
	{
		Log.i("ttt", "执行");
		if ( !isExcute )
		{
			isExcute = true;
			binder.excuteAlarm(model);
			this.finish();
		}
	}

	/**
	 * 播放系统声音
	 */
	private void PlayAlarmRing()
	{
		if ( mMediaPlayer != null && !mMediaPlayer.isPlaying() )
		{
			mMediaPlayer.start();
		}
	}

	/**
	 * 关闭系统声音
	 */
	private void StopAlarmRing()
	{
		if ( mMediaPlayer != null && mMediaPlayer.isPlaying() )
		{
			mMediaPlayer.stop();
			mMediaPlayer.release(); // 回收
		}
		mMediaPlayer = null;
	}

	@Override
	public void onClick(View v)
	{
		StopAlarmRing();
	}
}
