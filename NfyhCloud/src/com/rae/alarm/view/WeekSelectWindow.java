package com.rae.alarm.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

import com.yixin.nfyh.cloud.R;

/**
 * 星期选择框
 * 
 * @author ChenRui
 * 
 */
public class WeekSelectWindow extends SelectPopupWindow implements OnClickListener {
	
	OnCheckedChangeListener		mListener;
	ArrayList<Integer>			mSelectItems;	// 选择项
	private List<CheckBox>		mCheckBoxs;
	private View				mReturnButton;
	private SelectPopupWindow	mReturnWindow;
	
	public WeekSelectWindow(Context context) {
		super(context);
	}
	
	@Override
	protected View initView() {
		LinearLayout contentView = (LinearLayout) mInflater.inflate(R.layout.view_wheelview_week, null);
		mListener = new CheckChangeListener();
		mCheckBoxs = new ArrayList<CheckBox>();
		
		mSelectItems = new ArrayList<Integer>();
		
		findCheckBoxView(contentView);
		mReturnButton = contentView.findViewById(android.R.id.empty);
		mReturnButton.setOnClickListener(this);
		contentView.findViewById(android.R.id.closeButton).setOnClickListener(this);
		
		return contentView;
	}
	
	private void findCheckBoxView(LinearLayout contentView) {
		
		CheckBox cb1 = (CheckBox) contentView.findViewById(R.id.cb_wheelview_week_01);
		CheckBox cb2 = (CheckBox) contentView.findViewById(R.id.cb_wheelview_week_02);
		CheckBox cb3 = (CheckBox) contentView.findViewById(R.id.cb_wheelview_week_03);
		CheckBox cb4 = (CheckBox) contentView.findViewById(R.id.cb_wheelview_week_04);
		CheckBox cb5 = (CheckBox) contentView.findViewById(R.id.cb_wheelview_week_05);
		CheckBox cb6 = (CheckBox) contentView.findViewById(R.id.cb_wheelview_week_06);
		CheckBox cb7 = (CheckBox) contentView.findViewById(R.id.cb_wheelview_week_07);
		
		mCheckBoxs.add(cb1);
		mCheckBoxs.add(cb2);
		mCheckBoxs.add(cb3);
		mCheckBoxs.add(cb4);
		mCheckBoxs.add(cb5);
		mCheckBoxs.add(cb6);
		mCheckBoxs.add(cb7);
		
		for (CheckBox checkBox : mCheckBoxs) {
			boolean checked = isChecked(Integer.valueOf(checkBox.getTag().toString()));
			// 初始化选择状态
			checkBox.setChecked(checked);
			checkBox.setOnCheckedChangeListener(mListener);
		}
	}
	
	/**
	 * 是否选择
	 * 
	 * @param week
	 * @return
	 */
	private boolean isChecked(int week) {
		if (mSelectItems == null || mSelectItems.size() <= 0) { return false; }
		return mSelectItems.contains(week);
	}
	
	private void setChecked(int week, boolean checked) {
		// 保证最后一条
		
		int location = Integer.valueOf(week) - 1;
		if (location < 0 || location > mCheckBoxs.size() - 1) {
			location = 0;
		}
		CheckBox checkBox = mCheckBoxs.get(location);
		
		boolean exist = isChecked(week); //是否已经存在在已经选择列表中。
		if (!checked) { // 移除选择
			if (mSelectItems.size() <= 1) {  // 保证至少选择一项
				checkBox.setChecked(true);
				return;
			}
			int index = mSelectItems.indexOf(week);
			mSelectItems.remove(index);
			checkBox.setChecked(checked);
			
		}
		else if (!exist) { // 添加到选择列表中
			mSelectItems.add(week);
			checkBox.setChecked(checked);
		}
	}
	
	public void setSelectItems(int[] items) {
		if (items.length <= 0 && mSelectItems.size() <= 0) { // 没有任何东西。
			mSelectItems.add(1); //默认保留星期一
		}
		for (int item : items) {
			setChecked(item, true);
		}
	}
	
	class CheckChangeListener implements OnCheckedChangeListener {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			setChecked(Integer.valueOf(buttonView.getTag().toString()), isChecked);
		}
	}
	
	/**
	 * 获取当前选择的项
	 * 
	 * @return
	 */
	public int[] getSelectItems() {
		int[] result = new int[mSelectItems.size()];
		for (int i = 0; i < mSelectItems.size(); i++) {
			result[i] = mSelectItems.get(i);
		}
		Arrays.sort(result);
		
		return result;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case android.R.id.empty: //返回
				if (mReturnWindow != null) {
					mReturnWindow.show();
				}
				break;
			case android.R.id.closeButton: // 确定
				mSelectListener.onSelectChange(this, "请使用getSelectItems()方法获取结果。");
				break;
			
			default:
				break;
		}
		dismiss();
	}
	
	public void setReturnWindow(SelectPopupWindow mSelectWindow) {
		this.mReturnWindow = mSelectWindow;
	}
	
}
