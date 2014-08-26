package cn.rui.framework.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * 多媒体工具类
 * 
 * @author MrChenrui
 * 
 */
public class MediaUtil
{
	/**
	 * 播放音乐
	 * 
	 * @param context
	 * @param uri
	 *            路径
	 * @return
	 */
	public static MediaPlayer playMusic(Context context, Uri uri)
	{
		MediaPlayer player = MediaPlayer.create(context, uri);
		player.setLooping(true); // 循环播放
		if (!player.isPlaying())
		{
			player.start();
		}
		return player;
	}

	/**
	 * 播放音乐
	 * 
	 * @param context
	 * @param resid
	 *            资源ID
	 * @return
	 */
	public static MediaPlayer playMusic(Context context, int resid)
	{
		return playMusic(context, resid, true);
	}

	/**
	 * 播放音乐
	 * 
	 * @param context
	 * @param resid
	 * @param isLooping
	 *            是否重复播放
	 * @return
	 */
	public static MediaPlayer playMusic(Context context, int resid,
			boolean isLooping)
	{
		MediaPlayer player = MediaPlayer.create(context, resid);
		player.setLooping(isLooping); // 循环播放
		player.setVolume(1, 1); // 设置声音为最大
		if (!player.isPlaying())
		{
			player.start();
		}
		return player;
	}

	/**
	 * 停止播放
	 * 
	 * @param player
	 */
	public static void stopPlayMusic(MediaPlayer player)
	{
		try
		{
			if (player != null && player.isPlaying())
			{
				player.stop();
				// player.release();
				player = null;
			}
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 设置外放音量
	 * 
	 * @param on
	 *            打开或关闭
	 */
	public static void openSpeakerphone(Context context, boolean on)
	{
		AudioManager audio = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		if (on)
		{
			audio.setSpeakerphoneOn(true);
			int index = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			audio.setStreamVolume(AudioManager.STREAM_MUSIC, index,
					AudioManager.STREAM_MUSIC);
		}
		else
		{
			audio.setSpeakerphoneOn(false);// 关闭扬声器
			audio.setRouting(AudioManager.MODE_NORMAL,
					AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);
			// 把声音设定成Earpiece（听筒）出来，设定为正在通话中
			audio.setMode(AudioManager.MODE_IN_CALL);
		}
	}

}
