package com.yixin.nfyh.cloud.bll;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import cn.rui.framework.ui.RuiDialog;

import com.yixin.nfyh.cloud.bll.sign.SignCoreInterface;
import com.yixin.nfyh.cloud.bll.sign.SignCoreListener;
import com.yixin.nfyh.cloud.data.ISignDevice;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.i.ISignServer;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.UserSigns;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.w.NfyhWebserviceFactory;
import com.yixin.nfyh.cloud.w.SoapConnectionCallback;
import com.yixin.nfyh.cloud.w.WebServerException;

public class SignCore implements SignCoreInterface, SoapConnectionCallback<List<UserSigns>> {
	
	private ISignDevice			mDbSignApi;
	private Users				user;		//登陆用户
	private SignCoreListener	listener;
	private ISignServer			mSignApi;
	private Context				mContext;
	
	public SignCore(Context context, Users user) throws SQLException {
		this.mDbSignApi = NfyhCloudDataFactory.getFactory(context).getSignDevice();
		this.mSignApi = NfyhWebserviceFactory.getFactory(context).getSignServer();
		this.user = user;
		this.mSignApi.setOnConnectonCallback(this);
		this.mSignApi.setCookie(user.getCookie());
		this.mContext = context;
	}
	
	@Override
	public void saveUserSign(SignTypes m) throws SQLException {
		// 不是体征的，并且有数组的，转化为索引
		//		if (m.getIsSign() != 1 && m.getDataType() == -1)
		//		{
		//			DialogViewModel entity = new DialogViewModel();
		//			String arr = mDbSignApi.getUserSignRangeArray(m.getTypeId());
		//			entity.setDatas(arr);
		//			entity.setCurrentItem(m.getDefaultValue());
		//			m.setDefaultValue(entity.getCurrentItem() + "");
		//		}
		
		UserSigns usersign = new UserSigns();
		usersign.setGroupid(m.getPTypeid() + "");
		usersign.setIsSync(0);
		usersign.setRecDate(new Date());
		usersign.setSignMark(m.getOrderId());
		usersign.setSignTypes(m);
		usersign.setUsers(user);
		usersign.setSignValue(m.getDefaultValue());
		
		mDbSignApi.addOrUpdateUserSign(usersign);
		//		msg += String.format("正在保存：%s-%s%n", m.getName(), m.getDefaultValue());
		this.listener.onSignCoreSuccess(1, "本地保存成功！");
	}
	
	@Override
	public void upload() {
		if (user.getUid().equals("0")) {
			listener.onSignCoreSuccess(2, "保存成功，登录后云保存您的数据。");
			return;
		} // 离线用户不上传到云。
		converGuestDataToUser();
		uploadToServer();
	}
	
	//上传体征数据到服务器中
	private void uploadToServer() {
		List<UserSigns> datas = this.mDbSignApi.getUserSignsNotSysnc();
		
		if (datas != null && datas.size() > 0) {
			this.mSignApi.upload(datas);
		}
		else {
			this.listener.onSignCoreSuccess(1, "不需要同步。");
		}
	}
	
	/**
	 * 将离线体征数据转同步为当前用户的数据。<br>
	 */
	public void converGuestDataToUser() {
		
		List<UserSigns> datas = this.mDbSignApi.getGuestUserSignsNotSysnc();
		if (datas.size() > 0) {
			new RuiDialog.Builder(mContext).buildLeftButton("不同步", null).buildRight("同步", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mDbSignApi.convertGuestData(user.getUid());
					uploadToServer();
					dialog.dismiss();
				}
			}).buildTitle("离线数据同步").buildMessage("您上次离线记录了" + datas.size() + "条体征数据，是否将所有的数据同步到" + user.getName() + "上？").show();
			
		}
	}
	
	@Override
	public void setSignCoreListener(SignCoreListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void onSoapConnectSuccess(List<UserSigns> data) {
		// 更新状态
		this.mDbSignApi.uploadUserSign(data);
		this.listener.onSignCoreSuccess(1, "上传成功！");
	}
	
	@Override
	public void onSoapConnectedFalid(WebServerException e) {
		this.listener.onSignCoreError(-1, e.getMessage());
		
	}
	
}
