package com.yixin.nfyh.cloud.bll;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import cn.rui.framework.ui.RuiDialog;

import com.yixin.monitors.sdk.model.SignDataModel;
import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.bll.sign.SignCoreListener;
import com.yixin.nfyh.cloud.data.ISignCompareable;
import com.yixin.nfyh.cloud.data.ISignDevice;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.data.RangeCompareable;
import com.yixin.nfyh.cloud.i.ISignServer;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.UserSigns;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.model.view.SignTipsViewModel;
import com.yixin.nfyh.cloud.w.NfyhWebserviceFactory;
import com.yixin.nfyh.cloud.w.SoapConnectionCallback;
import com.yixin.nfyh.cloud.w.WebServerException;

/**
 * 体征处理
 * 
 * @author ChenRui
 * 
 */
public class SignCore implements SoapConnectionCallback<List<UserSigns>> {

	private ISignDevice mDbSignApi;
	private Users user; // 登陆用户
	private SignCoreListener listener;
	private ISignServer mSignApi;
	private Context mContext;
	private ISignCompareable mSignCompare; // 体征比较接口
	private boolean mIsSyncing = false;

	public SignCore(Context context) {
		this.mDbSignApi = NfyhCloudDataFactory.getFactory(context)
				.getSignDevice();
		this.mSignApi = NfyhWebserviceFactory.getFactory(context)
				.getSignServer();
		this.user = ((NfyhApplication) context.getApplicationContext())
				.getCurrentUser();
		this.mSignApi.setOnConnectonCallback(this);
		this.mSignApi.setCookie(user.getCookie());
		this.mContext = context;
		mSignCompare = new RangeCompareable(mContext); // 范围比较
	}

	/**
	 * 保存体征
	 * 
	 * @param list
	 */
	public void saveUserSign(List<SignTypes> list) {
		try {
			Date date = new Date(); // 统一时间。
			for (SignTypes m : list) {
				mIsSyncing = true;
				UserSigns usersign = new UserSigns();
				usersign.setGroupid(m.getPTypeid() + "");
				usersign.setIsSync(0);
				usersign.setRecDate(date);
				usersign.setSignMark(m.getOrderId());
				usersign.setSignTypes(m);
				usersign.setUsers(user);
				usersign.setSignValue(m.getDefaultValue());
				mDbSignApi.addOrUpdateUserSign(usersign);
			}
			this.listener.onSignCoreSuccess(100, "本地保存成功！");
		} catch (SQLException e) {
			e.printStackTrace();
			if (listener != null) {
				this.listener.onSignCoreError(-1, "数据库出错，保存失败！请重试。");
			}
		}
		mIsSyncing = false;
	}

	// 是否正在同步。
	public boolean isSyncing() {
		return mIsSyncing;
	}

	/**
	 * 同步体征数据
	 * 
	 * @param datas
	 */
	public void sysnc(List<SignTypes> datas, SignCoreListener listener) {
		setSignCoreListener(listener);
		saveUserSign(datas);
		upload();
	}

	public void upload() {
		if (user.getUid().equals("0")) { // 离线用户不上传到云。
			if (listener != null) {
				listener.onSignCoreSuccess(2, "保存成功，登录后云保存您的数据。");
			}
			mIsSyncing = false;
			return;
		}
		converGuestDataToUser();
	}

	// 上传体征数据到服务器中
	private void uploadToServer() {
		if (mIsSyncing) {
			return;
		} // 正在同步中。
		List<UserSigns> datas = this.mDbSignApi.getUserSignsNotSysnc();

		if (datas != null && datas.size() > 0) {
			mIsSyncing = true;
			this.listener.onUploading();
			this.mSignApi.upload(datas);
		} else {
			if (listener != null) {
				this.listener.onSignCoreSuccess(1, "不需要同步。");
			}
			mIsSyncing = false;
		}

	}

	/**
	 * 将离线体征数据转同步为当前用户的数据。<br>
	 */
	public void converGuestDataToUser() {

		List<UserSigns> datas = this.mDbSignApi.getGuestUserSignsNotSysnc();
		if (datas.size() > 0) {
			new RuiDialog.Builder(mContext)
					.buildLeftButton("不同步",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									uploadToServer();
									dialog.dismiss();
								}
							})
					.buildRight("同步", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							mDbSignApi.convertGuestData(user.getUid());
							uploadToServer();
							dialog.dismiss();
						}
					})
					.buildTitle("离线数据同步")
					.buildMessage(
							"您上次离线记录了" + datas.size() + "条体征数据，是否将所有的数据同步到"
									+ user.getName() + "上？").show();

		} else {
			uploadToServer();
		}
	}

	public void setSignCoreListener(SignCoreListener listener) {
		this.listener = listener;
	}

	@Override
	public void onSoapConnectSuccess(List<UserSigns> data) {
		// 更新状态
		this.mDbSignApi.uploadUserSign(data);
		if (listener != null) {
			this.listener.onSignCoreSuccess(200, "上传成功！");
		}
		mIsSyncing = false;
	}

	@Override
	public void onSoapConnectedFalid(WebServerException e) {
		if (listener != null) {
			this.listener.onSignCoreError(-1, e.getMessage());
		}
		mIsSyncing = false;

	}

	/**
	 * 将体征包转换为数据存放的实体。
	 * 
	 * @param models
	 *            体征包
	 * @return
	 * @throws SQLException
	 */
	public List<SignTypes> converSignDataModelToDbModel(
			List<SignDataModel> models) {
		List<SignTypes> result = new ArrayList<SignTypes>();

		try {
			for (SignDataModel m : models) {
				SignTypes type = null;
				String typeName = m.getDataName();

				// 根据名称获取类型
				type = mDbSignApi.getSignTypeByName(typeName);
				if (type.getPTypeid() == 0)
					continue; // 不是体征

				String value = m.getValue().toString();
				type.setDefaultValue(value);
				result.add(type);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 体征数据比较，返回比较结果。
	public SignTipsViewModel compara(SignTypes m) {
		if (m.getIsSign() != 1) {
			return null;
		} // 不是体征，TODO：综合评价，根据不同的体征组合计算。
		return mSignCompare.compare(m);
	}

	public SignTypes getSignType(String type) {
		try {
			return mDbSignApi.getSignType(type);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<SignTypes> getSignTypes(String type) {
		try {
			SignTypes m = mDbSignApi.getSignType(type);
			return mDbSignApi.getGroupSignTypes(m);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
