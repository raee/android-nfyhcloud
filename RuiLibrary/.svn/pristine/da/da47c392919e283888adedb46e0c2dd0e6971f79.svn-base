package cn.rui.framework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

/**
 * 电话类
 * 
 * <p>
 * 权限：
 * </p>
 * <p>
 * 更改扬声器：android.permission.MODIFY_AUDIO_SETTINGS
 * </p>
 * 
 * @author MrChenrui
 * 
 * 
 */
public class Caller
{

	private TelephonyManager	telMar;
	private String				phoneNumber;
	private Context				mContext;
	private boolean				isStop	= true;
	private int					callVolume;
	private AudioManager		audio;

	public boolean isStop()
	{
		return isStop;
	}

	public void start()
	{
		isStop = false;
	}

	public void stop()
	{
		// 撤销监听
		telMar.listen(null, PhoneStateListener.LISTEN_CALL_STATE);
		this.isStop = true;
	}

	public Caller(Context context, PhoneStateListener callStateChange)
	{
		this.mContext = context;
		audio = (AudioManager) this.mContext
				.getSystemService(Context.AUDIO_SERVICE);
		telMar = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		phoneNumber = this.telMar.getLine1Number();
		setPhoneStateListener(callStateChange);
	}

	public void setPhoneStateListener(PhoneStateListener l)
	{
		telMar.listen(l, PhoneStateListener.LISTEN_CALL_STATE);
	}

	/**
	 * 拨打电话
	 * 
	 * @param number
	 *            电话号码
	 */
	public void call(String number)
	{

		// 用intent启动拨打电话
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ number));
		this.mContext.startActivity(intent);
	}

	/**
	 * 挂断电话
	 */
	public void endCall()
	{
		try
		{
			Method getITelephonyMethod;
			getITelephonyMethod = this.telMar.getClass().getDeclaredMethod(
					"getITelephony");
			getITelephonyMethod.setAccessible(true);// 私有化函数也能使用
			ITelephony iTelephony = (ITelephony) getITelephonyMethod
					.invoke(this.telMar);
			iTelephony.endCall();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取通话状态
	 */
	public int getCallState()
	{
		return this.telMar.getCallState();
	}

	/**
	 * 通话打开免提
	 */
	public void openSpeakOn()
	{

		try
		{
			// audio.setMode(AudioManager.ROUTE_SPEAKER);

			// 没打开
			if (!audio.isSpeakerphoneOn())
			{
				audio.setMode(AudioManager.MODE_IN_CALL);
				callVolume = audio
						.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
				audio.setSpeakerphoneOn(true);
				audio.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
						callVolume, AudioManager.STREAM_VOICE_CALL);
			}

		}
		catch (Exception e)
		{
			Log.v("cr", "扬声器打开失败");
			e.printStackTrace();
		}
	}

	/**
	 * 关闭扬声器
	 */
	public void closeSpeakOn()
	{
		try
		{

			// 没打开
			if (audio.isSpeakerphoneOn())
			{
				audio.setSpeakerphoneOn(false);
				// audio.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
				// callVolume, AudioManager.STREAM_VOICE_CALL);
			}
		}
		catch (Exception e)
		{
			Log.v("cr", "扬声器关闭失败");
			e.printStackTrace();
		}
	}

	/**
	 * 检查电话是否接通
	 * 
	 * @return
	 */
	public boolean isPhoneOnUse()
	{
		boolean result = false;
		int state = telMar.getCallState();
		long threadStart = System.currentTimeMillis();
		if (state == TelephonyManager.CALL_STATE_OFFHOOK)
		{
			try
			{
				while (true)
				{
					Process process = Runtime.getRuntime().exec(
							"logcat -v time -b radio");

					InputStream inputstream = process.getInputStream();
					if (inputstream.available() < 1)
					{
						continue;
					}
					InputStreamReader inputstreamreader = new InputStreamReader(
							inputstream);
					BufferedReader bufferedreader = new BufferedReader(
							inputstreamreader);

					String str = "";

					while (str != null)
					{
						// 线程运行5分钟自动销毁
						if (System.currentTimeMillis() - threadStart > 5000)
						{
							Log.v("cr", "销毁监听");
							break;
						}

						// 已经通话
						if (str.contains("GET_CURRENT_CALLS")
								&& str.contains("ACTIVE"))
						{
							Log.v("cr", "正在通话中了..");
							result = true;
						}

						str = bufferedreader.readLine();
					}
					break;
				}

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		Log.v("cr", "是否正在通话中：" + result);
		return result;
	}

	/**
	 * 获取本机号码
	 * 
	 * @return
	 */
	public String getOwnPhoneNumber()
	{
		return this.phoneNumber == null ? "" : this.phoneNumber;
	}

}
