package com.yixin.nfyh.cloud.adapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.activity.SignDetailActivity;
import com.yixin.nfyh.cloud.data.ISignCompareable;
import com.yixin.nfyh.cloud.data.ISignDevice;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.data.RangeCompareable;
import com.yixin.nfyh.cloud.dialog.DialogManager;
import com.yixin.nfyh.cloud.dialog.DialogPopupWindowListener;
import com.yixin.nfyh.cloud.model.SignTips;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.view.DialogViewModel;
import com.yixin.nfyh.cloud.model.view.SignTipsViewModel;
import com.yixin.nfyh.cloud.ui.ResultDialog;

/**
 * 具体的体征类型适配器
 * 
 * @author Chenrui
 * @param <T>
 * 
 */
public class SignTypeItemGridViewAdapter extends SignTypeItemBaseAdapterView implements OnItemClickListener {
	private ISignCompareable			compare;
	private int							showType;
	private DialogPopupWindowListener	listener;
	private ISignDevice					apiSign	= null;
	private ResultDialog				mResultDialog;
	
	public SignTypeItemGridViewAdapter(Context context, DialogPopupWindowListener listener) {
		super(context);
		this.listener = listener;
		compare = new RangeCompareable(this.context);
		mResultDialog = new ResultDialog(context);
		this.apiSign = NfyhCloudDataFactory.getFactory(context).getSignDevice();//体征接口
	}
	
	@Override
	public View getView(int location, View v, ViewGroup vg) {
		ViewHolder holder = null;
		SignTypes m = datas.get(location);
		if (v == null) {
			v = LayoutInflater.from(this.context).inflate(R.layout.ui_sign_detail_item, null);
			holder = new ViewHolder();
			
			holder.tvValue = (TextView) v.findViewById(R.id.tv_ui_sign_detail_subtitle);
			
			holder.tvTitle = (TextView) v.findViewById(R.id.tv_ui_sign_detail_title);
			holder.tvUnit = (TextView) v.findViewById(R.id.tv_ui_sign_detail_unit);
			holder.imgRight = (ImageView) v.findViewById(R.id.img_ui_sign_detail_right);
			
			v.setTag(holder);
		}
		else {
			holder = (ViewHolder) v.getTag();
		}
		
		// 比较数据。
		if (m.getIsSign() == 1) {
			holder.imgRight.setVisibility(View.GONE);
			SignTipsViewModel model = compare.compare(m);
			holder.tvValue.setTextColor(model.getLevelColor());
		}
		
		holder.tvTitle.setText(m.getName());
		holder.tvUnit.setText(m.getTypeUnit());
		holder.tvValue.setText(m.getDefaultValue());
		//		viewList.add(v);
		return v;
	}
	
	class ViewHolder {
		public TextView		tvTitle, tvValue, tvUnit;
		public ImageView	imgRight;
	}
	
	public void showTipsWindow(SignTypes signtype) {
		SignTipsViewModel model = compare.compare(signtype); //比较结果
		mResultDialog.reset();
		mResultDialog.setTitle(signtype.getName() + model.getLevelName());
		mResultDialog.setAutoCloseTime(3);
		mResultDialog.setStar(model.getStarNumber());
		mResultDialog.setTagLevel(model.getLevel());
		mResultDialog.setBackgournd(model.getLevelColor());
		
		for (SignTips tip : model.getSignTipsList()) {
			mResultDialog.setMessage(tip.getTipsComment()); // 提示信息。
		}
		
		mResultDialog.show();
	}
	
	public SignTypes getDataItem(int position) {
		return datas.get(position);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		SignTypes m = datas.get(position);
		
		if (showType == -1) { // 跳转查看
			Intent intent = new Intent(context, SignDetailActivity.class);
			Bundle extra = new Bundle();
			extra.putString(Intent.EXTRA_TEXT, m.getPTypeid() + "");
			intent.putExtras(extra);
			context.startActivity(intent);
			return;
		}
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
			DialogManager dialog = new DialogManager(context, type, listener, viewModel);
			Activity at = (Activity) context;
			dialog.show(at.getWindow().getDecorView());
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setShowType(int type) {
		this.showType = type;
	}
	
	public List<SignTypes> getDataList() {
		return datas;
	}
}
