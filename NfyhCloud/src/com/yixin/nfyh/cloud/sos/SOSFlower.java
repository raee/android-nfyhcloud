package com.yixin.nfyh.cloud.sos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import cn.rui.framework.ui.IToast;
import cn.rui.framework.ui.RuiDialog;
import cn.rui.framework.utils.BaiduLocation;
import cn.rui.framework.utils.Caller;
import cn.rui.framework.utils.CommonUtil;
import cn.rui.framework.utils.DateUtil;
import cn.rui.framework.utils.IBaiduLocationResult;
import cn.rui.framework.utils.MediaUtil;
import cn.rui.framework.utils.NetWorkTest;
import cn.rui.framework.utils.SoapCallback;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.ConfigServer;
import com.yixin.nfyh.cloud.bll.GlobalSetting;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.service.SoapService;
import com.yixin.nfyh.cloud.ui.TimerToast;

public class SOSFlower implements IToast {

	public static String		DEFAULT_SOS_NUMBER	= "15918716307";	// 默认拨打的电话号码

	private IFlowerResult		callback;

	private Context				context;

	private NetWorkTest			net;

	private static MediaPlayer	player;

	public boolean				isStoped;

	private Caller				caller;

	private ConfigServer		config;

	private Users				user;

	public SOSFlower(Context context, IFlowerResult l) {
		this.context = context;
		this.callback = l;
		this.user = ((NfyhApplication) context.getApplicationContext()).getCurrentUser();
		config = new ConfigServer(context);
		this.net = new NetWorkTest(context);

	}

	@Override
	public void show(String msg, int code) {
		TimerToast.show(context, msg);
	}

	@Override
	public void show(String msg) {
		TimerToast.show(context, msg);
	}

	public void stop() {
		isStoped = true;
		stopTTS();
		caller.stop();
	}

	public void stopTTS() {
		MediaUtil.stopPlayMusic(player);
	}

	/**
	 * 定位流程
	 * 
	 * @author MrChenrui
	 * 
	 */
	public class SOSLocationFlower implements IFower, IBaiduLocationResult {

		private BaiduLocation	baiduLocation;

		private Bundle			result;

		private String			jwd, address;

		public Bundle getData() {
			return result;
		}

		public SOSLocationFlower(BaiduMap map) {
			baiduLocation = new BaiduLocation(context.getApplicationContext(), this);
			baiduLocation.setFromBaiduMap(map);
			this.baiduLocation.start();
			result = new Bundle();
		}

		public BaiduLocation getBaiduLocation() {
			return baiduLocation;
		}

		@Override
		public void start(Bundle data) {
			if (data == null)
				result = new Bundle();
			else
				result = data;
			isStoped = false;
			if (jwd != null) {
				result.putString("address", address);
				result.putString("jwd", jwd);
				callback.onLocation(IFlowerResult.FLOW_STATUS_SUCCESS, result);
				return;
			}
			if (!this.baiduLocation.mLocationClient.isStarted()) this.baiduLocation.start();
		}

		@Override
		public void onReceiveLocationSuccess(BDLocation location) {
			baiduLocation.stop();
			// 已经获取到位置
			if (jwd != null) return;
			jwd = location.getLatitude() + "," + location.getLongitude();
			address = location.getAddrStr();
			result.putString("address", address);
			result.putString("jwd", jwd);
			callback.onLocation(IFlowerResult.FLOW_STATUS_SUCCESS, result);
		}

		@Override
		public void onReceiveLocationFaild(int code, String msg) {
			baiduLocation.stop();
			if (jwd != null) return;
			this.jwd = "null";
			result.putString(IFlowerResult.EXTRA_DATA, msg);
			callback.onLocation(IFlowerResult.FLOW_STATUS_ERROR, result);
		}
	}

	/**
	 * 选择事件流程
	 * 
	 * @author MrChenrui
	 * 
	 */
	public class SOSSelectEventFlower implements IFower {

		private String	eventName;

		private Bundle	result;

		List<String>	datas;

		@Override
		public void start(Bundle data) {
			if (data == null)
				result = new Bundle();
			else
				result = data;
			isStoped = false;
			if (this.eventName != null) {
				success();
				return;
			}
			RuiDialog dialog = new RuiDialog(context);
			dialog.setTitle("您现在发生了什么事？");
			datas = config.getListConfigs(ConfigServer.KEY_DESKTOP_EVENT_LIST);
			if (datas == null || datas.size() == 0) {
				datas = new ArrayList<String>();
				datas.add("没有定义发生事件");
			}
			StringBuilder sb = new StringBuilder();
			// 上传发生的所有的事件
			for (String event : datas) {
				sb.append("\n" + event);
			}
			eventName = sb.toString();
			// if (eventName.length() > 1) eventName = eventName.substring(1);
			success();
		}

		private void success() {
			result.putString(IFlowerResult.EXTRA_DATA, eventName);
			callback.onSelectEvent(IFlowerResult.FLOW_STATUS_SUCCESS, result);
		}

		public void setEventName(String name) {
			this.eventName = name;
		}
	}

	/**
	 * 发送到服务器流程
	 * 
	 * @author MrChenrui
	 * 
	 */
	public class SOSSendSOSToServerFlower implements IFower {

		private GlobalSetting	setting;

		private Bundle			result;

		private SoapService		soap;

		private int				tryTime	= 0;

		@Override
		public void start(Bundle data) {
			if (data == null)
				result = new Bundle();
			else
				result = data;
			isStoped = false;
			this.setting = new GlobalSetting(context);
			// 经纬度
			String jwd = data.getString("jwd");
			// 发生的事件
			String eventName = data.getString(IFlowerResult.EXTRA_DATA);
			String address = data.getString("address");
			send(jwd, eventName, address);
		}

		private void send(final String jwd, final String eventName, final String address) {
			tryTime++;
			Map<String, Object> datas = new HashMap<String, Object>();
			datas.put("cookie", setting.getUser().getCookie());
			datas.put("message", eventName);
			datas.put("jwd", jwd);
			datas.put("address", address);
			datas.put("senddate", DateUtil.getCurrentDateString("yyyy-MM-dd hh:mm:ss"));
			// 网络不可用
			if (!net.isAvailable()) {
				// player.play(R.raw.sos002).start();
				// 关闭wifi
				net.openWIFI(false);
				// 打开移动网络
				net.open3G(true);
			}
			// 开线程上传到服务器
			soap = new SoapService(context);
			soap.setCallbackListener(new SoapCallback() {

				@Override
				public void onSoapResponse(Object response) {
					TimerToast.makeText(context, "位置上传成功！", Toast.LENGTH_SHORT).show();
					result.putString(IFlowerResult.EXTRA_DATA, response.toString());
					callback.onSendToserver(IFlowerResult.FLOW_STATUS_SUCCESS, result);
				}

				@Override
				public void onSoapError(int code, String msg) {
					TimerToast.makeText(context, "位置上传失败！", Toast.LENGTH_SHORT).show();
					callback.onSendToserver(IFlowerResult.FLOW_STATUS_ERROR, result);
					// tts.begin("位置上传失败，这可能导致客服人员无法找到您的地址，我们正在尝试为您打开网络。");
					if (tryTime < 3) {
						// 最多尝试３次
						send(jwd, eventName, address);
						Log.i("test", "尝试再次上传：" + tryTime);
					}
				}
			});
			soap.setParams(datas);
			soap.call("UploadAidMessage");
		}
	}

	/**
	 * 拨打电话流程
	 * 
	 * @author MrChenrui
	 * 
	 */
	public class SOSCallPhoneFlower implements IFower {

		private Bundle			result;

		private int				curCallIndex		= 0;

		private List<String>	watingCallPhoneList;

		private PhoneListener	phoneListener;
		private boolean			isApplicationCall	= false;	// 是否为应用程序发出的急救

		public SOSCallPhoneFlower() {
			phoneListener = new PhoneListener();
			caller = new Caller(context, phoneListener);
			caller.setPhoneStateListener(phoneListener);
		}

		@Override
		public void start(Bundle data) {
			if (data == null) {
				result = new Bundle();
			}
			else {
				result = data;
			}

			this.watingCallPhoneList = getSosPhoneNumber();
			isStoped = false;
			isApplicationCall = true;
			call();
		}

		public void sendMsg() {
		}

		public void call() {
			// 是否为程序发出的急救，Bug：普通拨打电话也会发生。
			if (!isApplicationCall) {
				Log.e("sos", "不是程序发出的急救，不拨打电话！");
				return;
			}

			if (isStoped) {
				result.putString(IFlowerResult.EXTRA_DATA, "用户放弃呼救");
				caller.endCall(); // 停止拨打电话
				callback.onFlowError(IFlowerResult.FLOW_STATUS_ERROR, result);
				callback.onFinsh(IFlowerResult.FLOW_STATUS_ERROR, result);
				this.curCallIndex = 0;
				return;
			}
			// 急救完成
			if (this.curCallIndex >= this.watingCallPhoneList.size()) {
				sosFinsh();
				return;
			}
			try {
				String num = this.watingCallPhoneList.get(this.curCallIndex);
				if (!DEFAULT_SOS_NUMBER.equals(num) // 不是120的电话才发送短信
						&& result.containsKey("address")) {
					// 发送短信到监护人手机上
					String address = result.getString("address");
					String userName = user == null ? "无名氏" : user.getName();
					String event = result.getString(IFlowerResult.EXTRA_DATA);
					String msg = String.format("【%s】发出紧急信息！他可能发生以下情况：%s\n位置：【%s】", userName, event, address);
					CommonUtil.sendSms(context, num, msg);
					Log.i("msg", "发送短信：" + msg);
				}
				// 延迟3秒拨打电话
				new Timer().schedule(new TimerTask() {

					@Override
					public void run() {
						caller.call(watingCallPhoneList.get(curCallIndex)); // 拨打
						// 延迟5秒打开扬声器
						new Timer().schedule(new TimerTask() {

							@Override
							public void run() {
								caller.openSpeakOn(); // 打开扬声器
							}
						}, 5000);
					}
				}, 3000);
				result.putString("number", num);
				callback.onCallingPhone(IFlowerResult.FLOW_STATUS_SUCCESS, result);
			}
			catch (Exception e) {
				e.printStackTrace();
				callback.onCallingPhone(IFlowerResult.FLOW_STATUS_ERROR, result);
			}
		}

		/**
		 * 呼救完成
		 */
		private void sosFinsh() {
			callback.onFinsh(IFlowerResult.FLOW_STATUS_SUCCESS, result);
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					MediaUtil.openSpeakerphone(context, true);
					player = MediaUtil.playMusic(context, R.raw.sos001, false);
					player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							MediaUtil.stopPlayMusic(player);
							player = MediaUtil.playMusic(context, R.raw.sos003);
						}
					});
				}
			}, 3000);
			this.curCallIndex = 0;
			caller.stop();
			isApplicationCall = false;
		}

		protected List<String> getSosPhoneNumber() {
			List<String> result = config.getListConfigs(ConfigServer.KEY_DESKTOP_PHONE_LIST);
			if (result == null || result.size() <= 0) {
				result = new ArrayList<String>();
			}
			// 获取客服电话
			String number = config.getConfig(ConfigServer.KEY_PHONE_NUMBER);
			if (!TextUtils.isEmpty(number)) {
				DEFAULT_SOS_NUMBER = number;
			}
			result.add(DEFAULT_SOS_NUMBER);
			return result;
		}

		/**
		 * 拨打电话监听
		 * 
		 * @author MrChenrui
		 * 
		 */
		private class PhoneListener extends PhoneStateListener {

			private int	lastState	= 0;

			@Override
			public void onCallStateChanged(int state, String incomingNumber) {

				// 急救完了没有
				if (!isStoped && lastState == 2 && state == 0) {
					// 关闭扬声器
					caller.closeSpeakOn();
					// 停止语音
					stopTTS();
					curCallIndex++;
					call();
				}
				lastState = state;
			}
		}
	}
}
