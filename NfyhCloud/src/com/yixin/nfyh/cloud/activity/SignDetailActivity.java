package com.yixin.nfyh.cloud.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import cn.rui.framework.ui.RuiDialog;

import com.yixin.monitors.sdk.model.PackageModel;
import com.yixin.monitors.sdk.model.SignDataModel;
import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.adapter.SignTypeItemGridViewAdapter;
import com.yixin.nfyh.cloud.bll.SignCore;
import com.yixin.nfyh.cloud.bll.sign.SignCoreInterface;
import com.yixin.nfyh.cloud.bll.sign.SignCoreListener;
import com.yixin.nfyh.cloud.data.ISignCompareable;
import com.yixin.nfyh.cloud.data.ISignDevice;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.data.RangeCompareable;
import com.yixin.nfyh.cloud.dialog.DialogManager;
import com.yixin.nfyh.cloud.dialog.DialogPopupWindowListener;
import com.yixin.nfyh.cloud.model.SignTips;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.model.view.DialogViewModel;
import com.yixin.nfyh.cloud.model.view.SignTipsViewModel;
import com.yixin.nfyh.cloud.ui.ActionbarUtil;
import com.yixin.nfyh.cloud.ui.ResultDialog;
import com.yixin.nfyh.cloud.ui.TopMsgView;

public class SignDetailActivity extends BaseActivity implements OnItemClickListener, DialogPopupWindowListener, SignCoreListener {
	private ISignDevice					apiSign				= null;
	private ISignCompareable			compare				= null;
	private SignTypes					currentSignTypes	= null;
	private boolean						isSync				= false;	//是否正在同步数据
	private ListView					lvSigns				= null;
	//	private ResultDialog				mResultDialog		= null;
	private boolean						mIsCreated;
	private ViewGroup					rootView			= null;
	private int							showType			= 0;		//0为正常，-1为蓝牙数据
	private SignCoreInterface			signInterface		= null;
	private SignTypeItemGridViewAdapter	signTypeAdapter		= null;
	private List<SignTypes>				signTypes			= null;	// 二级的分类
	private Users						user				= null;
	private TopMsgView					viewMsg				= null;
	
	/**
	 * 自动比较数据
	 */
	private void autoCompare() {
		for (int i = 0; i < signTypes.size(); i++) {
			SignTypes type = signTypes.get(i);
			compareData(i, type.getDefaultValue());
		}
	}
	
	/**
	 * 开始比较数据
	 * 
	 * @param which
	 * @param values
	 */
	private void compareData(int which, String values) {
		try {
			
			if (values == null || values.trim().length() <= 0) { return; }
			
			SignTypes signtype = this.signTypes.get(which);
			signtype.setDefaultValue(values);
			this.signTypeAdapter.setValue(which, signtype.getDefaultValue());
			
			// 更新体征数据
			if (signtype.getIsSign() == 1) {
				ResultDialog mResultDialog = new ResultDialog(this);
				SignTipsViewModel model = this.compare.compare(signtype);
				String color = model.getLevelColor();
				this.signTypeAdapter.setColor(which, color);
				
				// 体征数据个性化提醒 - 弹出对话框
				
				int colorResid = Color.parseColor(color);
				int taglevel = 1;
				if (model.getLevel() != 5) {
					taglevel = 2;
				}
				for (SignTips tip : model.getSignTipsList()) {
					mResultDialog.setMessage(tip.getTipsComment());
				}
				
				mResultDialog.setTitle(signtype.getName() + model.getLevelName());
				mResultDialog.setAutoCloseTime(3);
				mResultDialog.setStar(getStarNumber(model.getLevel()));
				mResultDialog.setTagLevel(taglevel);
				mResultDialog.setBackgournd(colorResid);
				mResultDialog.show();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void findView() {
		try {
			this.apiSign = NfyhCloudDataFactory.getFactory(this).getSignDevice();//体征接口
			signInterface = new SignCore(this, user);//业务体征接口
			signInterface.setSignCoreListener(this);//设置体征回调监听
			
			lvSigns = (ListView) this.findViewById(R.id.lv_sign_detail_types);
			lvSigns.setOnItemClickListener(this); // 体征项点击
			
			initSign();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean getdialogValidate(String... values) {
		return true;
	}
	
	/**
	 * 获取星星数量
	 * 
	 * @param level
	 * @return
	 */
	private int getStarNumber(int level) {
		if (level >= 8 || level <= 2) return 1;
		else if (level == 7 || level == 3) return 2;
		else if (level == 6 || level == 4) return 3;
		else if (level == 5) return 5;
		else return 3;
	}
	
	// 初始化体征
	@SuppressWarnings("unchecked")
	private void initSign() throws SQLException {
		// 获取体征类型
		String type = getIntent().getStringExtra(Intent.EXTRA_TEXT);
		
		if (type.equals("-1")) {
			// 蓝牙接收的数据
			PackageModel models = getIntent().getParcelableExtra("data");
			signTypes = parserModel(models.getSignDatas());
			getActionBar().setTitle(user.getName() + "的测量数据");
			showType = -1;
			
		}
		else {
			
			type = type == null ? "1000" : type;
			currentSignTypes = apiSign.getSignType(type); //获取当前的体征类型
			
			// 获取体征参数
			signTypes = apiSign.getGroupSignTypes(currentSignTypes);
			
			ActionbarUtil.setTitleAsUpHome(this, getActionBar(), currentSignTypes.getName()); //返回栏
		}
		
		// 适配器
		signTypeAdapter = new SignTypeItemGridViewAdapter(this, signTypes);
		lvSigns.setAdapter(signTypeAdapter);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_sign_detail, null);
		setContentView(rootView);
		
		//		mResultDialog = new ResultDialog(this);
		
		user = ((NfyhApplication) getApplication()).getCurrentUser(); //获取用户
		
		try {
			// 初始化比较接口
			this.compare = new RangeCompareable(this);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		findView();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_sign_detail, menu); //操作栏菜单
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public void onDialogCancle(PopupWindow dialog, String... values) {
		
	}
	
	@Override
	public void onDialogChange(PopupWindow dialog, int which, String values) {
		
	}
	
	@Override
	public void onDialogFinsh(PopupWindow dialog, int which, String values) {
		compareData(which, values);
		dialog.dismiss();
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		if (showType == -1) {
			Intent intent = new Intent(this, SignDetailActivity.class);
			Bundle extra = new Bundle();
			extra.putString(Intent.EXTRA_TEXT, this.signTypes.get(position).getPTypeid() + "");
			intent.putExtras(extra);
			startActivity(intent);
			return;
		}
		SignTypes m = this.signTypes.get(position);
		int type = m.getDataType(); // 体征的数据类型
		
		ArrayList<DialogViewModel> viewModel = new ArrayList<DialogViewModel>();
		try {
			// 模型
			DialogViewModel entity = new DialogViewModel();
			if (m.getDataType() == -1) // 数组类型
			{
				String arr = apiSign.getUserSignRangeArray(m.getTypeId()); //获取体征的数组类型数据
				entity.setDatas(arr);
			}
			else {
				double[] range = apiSign.getUserSignRange(m.getTypeId());
				entity.setDatas((int) range[0], (int) range[1]);
			}
			entity.setDataIndex(position);
			entity.setDataType(type);
			entity.setTitle(m.getName());
			entity.setSubTitle(m.getTypeUnit());
			entity.setCurrentItem(m.getDefaultValue());
			
			viewModel.add(entity);
			
			//显示弹出选择栏
			DialogManager dialog = new DialogManager(this, type, this, viewModel);
			dialog.show(this.rootView);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_sign_detail_sync:
				if (isSync) {
					showMsg("数据正在同步,请稍后...");
					break;
				}
				showDataDialog();
				break;
			
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// 上传失败	
	@Override
	public void onSignCoreError(int code, String msg) {
		if (viewMsg == null) return;
		viewMsg.setMsg(msg);
		viewMsg.anim();
		viewMsg.setIcon(R.drawable.icon_write_faild);
		viewMsg.setBackgroundColor(getResources().getColor(R.color.mihuang));
		isSync = false;
		findViewById(R.id.menu_sign_detail_sync).clearAnimation();
	}
	
	// 上传成功
	@Override
	public void onSignCoreSuccess(int code, String msg) {
		if (viewMsg == null) return;
		viewMsg.setMsg(msg);
		viewMsg.stopAnim();
		viewMsg.setIcon(R.drawable.icon_write_success);
		isSync = false;
		findViewById(R.id.menu_sign_detail_sync).clearAnimation();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus && !this.mIsCreated && showType == -1) {
			autoCompare(); //自动比较数据
			this.mIsCreated = true;
		}
	}
	
	private List<SignTypes> parserModel(List<SignDataModel> models) throws SQLException {
		List<SignTypes> result = new ArrayList<SignTypes>();
		
		for (SignDataModel m : models) {
			SignTypes type = null;
			String typeName = m.getDataName();
			
			// 根据名称获取类型
			type = apiSign.getSignTypeByName(typeName);
			if (type.getPTypeid() == 0) continue; //不是体征
			
			String value = m.getValue().toString();
			type.setDefaultValue(value);
			currentSignTypes = type;
			result.add(type);
		}
		return result;
		
	}
	
	/**
	 * 显示数据确认对话框
	 */
	private void showDataDialog() {
		String source = "<p>您确定这些数据是：</p><p>" + user.getName() + "</p>";
		for (SignTypes m : signTypes) {
			source += "<p>";
			source += m.getName();
			source += "：";
			source += m.getDefaultValue();
			source += "</p>";
		}
		
		String html = Html.fromHtml(source).toString();
		
		new RuiDialog.Builder(this).buildTitle("数据确认").buildMessage(html).buildLeftButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (showType == -1) {
					finish();
				}
				dialog.dismiss();
			}
		}).buildRight("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				sync();
				dialog.dismiss();
			}
		}).show();
	}
	
	/**
	 * 同步数据
	 */
	private void sync() {
		isSync = true;
		viewMsg = new TopMsgView(this, null);
		viewMsg.setIcon(R.drawable.view_browser_web_update);
		viewMsg.setMsg("正在上传数据...");
		viewMsg.anim();
		viewMsg.show((ViewGroup) this.rootView.getChildAt(0));
		
		findViewById(R.id.menu_sign_detail_sync).startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade)); // start Animation
		
		for (SignTypes m : this.signTypes) {
			try {
				signInterface.saveUserSign(m); // 本地保存
			}
			catch (SQLException e) {
				viewMsg.setMsg("同步失败：" + m.getName());
				e.printStackTrace();
				isSync = false;
			}
		}
		
		signInterface.upload();
	}
}
