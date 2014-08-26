//package com.yixin.nfyh.cloud.sos;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//import android.content.Context;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.media.MediaPlayer.OnCompletionListener;
//
//public class MusicPlayer
//{
//	private Context			context;
//	private MediaPlayer		mp;
//	private boolean			isStoped;
//	private AudioManager	audio;
//
//	public MusicPlayer(Context context)
//	{
//		this.context = context;
//		audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//
//	}
//
//	public void setPlayListener(OnCompletionListener listener)
//	{
//		try
//		{
//			this.mp.setOnCompletionListener(listener);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//
////	private void openSound()
////	{
////		try
////		{
////			new Timer().schedule(new TimerTask()
////			{
////				@Override
////				public void run()
////				{
////
////					setSpeakerphoneOn(!audio.isSpeakerphoneOn());
////				}
////			}, 1000);
////		}
////		catch (Exception e)
////		{
////			e.printStackTrace();
////		}
////	}
//
//	@SuppressWarnings("deprecation")
//	private void setSpeakerphoneOn(boolean on)
//	{
//		if (on)
//		{
//			audio.setSpeakerphoneOn(true);
//		}
//		else
//		{
//			audio.setSpeakerphoneOn(false);// 关闭扬声器
//			audio.setRouting(AudioManager.MODE_NORMAL,
//					AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);
//			// 把声音设定成Earpiece（听筒）出来，设定为正在通话中
//			audio.setMode(AudioManager.MODE_IN_CALL);
//		}
//	}
//
//	public MusicPlayer play(int resid)
//	{
//		try
//		{
//			this.isStoped = false;
//			this.mp = MediaPlayer.create(context, resid);
//			this.mp.setVolume(1, 1);
//			this.mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//		return this;
//	}
//
//	public void startRepeat()
//	{
//		try
//		{
//			start();
//			this.mp.setOnCompletionListener(new OnCompletionListener()
//			{
//
//				@Override
//				public void onCompletion(MediaPlayer mp)
//				{
//					if (!isStoped)
//						start();
//				}
//			});
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	public void start()
//	{
//		try
//		{
//			this.mp.start();
//			audio.setMode(AudioManager.MODE_NORMAL);
//			if (!audio.isSpeakerphoneOn())
//			{
//				audio.setSpeakerphoneOn(true);
//				int index = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//				audio.setStreamVolume(AudioManager.STREAM_MUSIC, index,
//						AudioManager.STREAM_MUSIC);
//			}
//
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	public void stop()
//	{
//		try
//		{
//			this.isStoped = true;
//			if (this.mp != null)
//				this.mp.stop();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//}
