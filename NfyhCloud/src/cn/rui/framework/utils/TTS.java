//package cn.rui.framework.utils;
//
//import android.content.Context;
//import android.util.Log;
//import cn.rui.framework.ui.IToast;
//
//import com.iflytek.speech.SpeechError;
//import com.iflytek.speech.SynthesizerPlayer;
//import com.iflytek.speech.SynthesizerPlayerListener;
//
///**
// * 讯飞语音合成 API
// * 
// * @author MrChenrui
// * 
// */
//public class TTS implements SynthesizerPlayerListener
//{
//	public static final String			APP_ID					= "522355c7";
//	public static final String			TTS_ROLE				= "xiaoyan";
//
//	// 缓存对象.
//	// private SharedPreferences mSharedPreferences;
//	private Context						mContext;
//
//	// 合成对象.
//	private SynthesizerPlayer			mSynthesizerPlayer;
//
//	private String						mTTSMsg					= "测试合成语音";
//
//	private IToast						toast;
//
//	// 缓冲进度
//	private int							mPercentForBuffering	= 0;
//
//	private SynthesizerPlayerListener	mListener;
//
//	private NetWorkTest					mNetWork;
//
//	public TTS(Context context, IToast imsg, String ttsMsg)
//	{
//		this.mContext = context;
//
//		this.mNetWork = new NetWorkTest(this.mContext);
//		this.mTTSMsg = ttsMsg;
//		this.toast = imsg;
//
//		initTTS();
//	}
//
//	public SynthesizerPlayer getmSynthesizerPlayer()
//	{
//		return mSynthesizerPlayer;
//	}
//
//	private synchronized void initTTS()
//	{
//
//		// // 缓存对象
//		// mSharedPreferences = this.mContext.getSharedPreferences(
//		// this.mContext.getPackageName(), Context.MODE_PRIVATE);
//
//		if (null == mSynthesizerPlayer)
//		{
//			// 创建合成对象.
//			mSynthesizerPlayer = SynthesizerPlayer.createSynthesizerPlayer(
//					this.mContext, "appid=" + APP_ID);
//		}
//
//		// 设置合成发音人.
//		mSynthesizerPlayer.setVoiceName(TTS_ROLE);
//
//		// 设置发音人语速
//		int speed = 40;
//		mSynthesizerPlayer.setSpeed(speed);
//
//		// 设置音量.
//		int volume = 100;
//		mSynthesizerPlayer.setVolume(volume);
//
//	}
//
//	public void setListener(SynthesizerPlayerListener listener)
//	{
//		this.mListener = listener;
//	}
//
//	public synchronized void begin()
//	{
//		mSynthesizerPlayer.isAvaible();
//		this.begin(this.mTTSMsg);
//	}
//
//	public synchronized void begin(String ttsMSg)
//	{
//
//		// 检查网络
//		if (!this.mNetWork.isAvailable())
//		{
//			if (this.mListener != null)
//				this.mListener.onEnd(null);
//			return;
//		}
//
//		if (null != this.mListener)
//		{
//			// 进行语音合成.
//			mSynthesizerPlayer.playText(ttsMSg, null, this.mListener);
//		}
//		else
//		{
//			// 进行语音合成.
//			mSynthesizerPlayer.playText(ttsMSg, null, this);
//		}
//
//	}
//
//	void show(String s)
//	{
//		Log.v("cr", s);
//	}
//
//	@Override
//	public void onBufferPercent(int jingdu, int arg1, int arg2)
//	{
//		show("b:" + jingdu);
//	}
//
//	@Override
//	public void onEnd(SpeechError arg0)
//	{
//		if (null != arg0)
//		{
//			show("end:" + arg0.getErrorCode());
//			if (arg0.getErrorCode() == 10114)
//			{
//				this.toast.show("语音发生错误，手机没有连接到网络。");
//			}
//			else
//			{
//				this.toast.show("语音发生错误！code:" + arg0.getErrorCode());
//			}
//
//		}
//		else
//		{
//			show("end");
//		}
//
//	}
//
//	@Override
//	public void onPlayBegin()
//	{
//		show("begin");
//
//	}
//
//	@Override
//	public void onPlayPaused()
//	{
//		show("paused");
//	}
//
//	@Override
//	public void onPlayPercent(int jingdu, int arg1, int arg2)
//	{
//		show("play:" + jingdu);
//	}
//
//	@Override
//	public void onPlayResumed()
//	{
//		show("resumed");
//	}
//
//	public void stop()
//	{
//		this.mSynthesizerPlayer.cancel();
//
//	}
// }
