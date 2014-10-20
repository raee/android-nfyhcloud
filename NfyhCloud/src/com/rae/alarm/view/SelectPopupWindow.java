package com.rae.alarm.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yixin.nfyh.cloud.R;

/**
 * 弹出选择框
 * 
 * @author ChenRui
 * 
 */
public class SelectPopupWindow extends PopupWindow {
	/**
	 * 选择框选择事件。
	 * 
	 * @author ChenRui
	 * 
	 */
	public interface onSelectListener {
		void onSelectChange(SelectPopupWindow window, String value);
	}
	
	protected Context			mContext;
	protected LayoutInflater	mInflater;
	private ListView			mListView;
	private OnItemClickListener	mItemClickListener;
	private DefaultAdapter		mDefaultAdapter;
	private List<String>		mSelectItems	= new ArrayList<String>();	// 已经选择的项
	private String				name;
	private String				mCurrentItem;								//当前选择的项，最终想要的结果。
	protected onSelectListener	mSelectListener;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public SelectPopupWindow(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		setUp();
		setContentView(initView());
		update();
	}
	
	protected void setUp() {
		
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.DialogAimation);
		
		this.setOutsideTouchable(true);
		
		this.setBackgroundDrawable(new ColorDrawable(android.R.color.transparent)); // 透明背景
	}
	
	/**
	 * 作为字符串的形式
	 */
	protected View initView() {
		View contentView = mInflater.inflate(R.layout.view_window_select, null);
		this.mListView = (ListView) contentView.findViewById(android.R.id.list);
		contentView.findViewById(R.id.btn_select_window_cancle).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		mDefaultAdapter = new DefaultAdapter();
		mListView.setAdapter(mDefaultAdapter);
		setOnItemClickListener(null);
		
		return contentView;
	}
	
	public void setAdapter(ListAdapter adapter) {
		this.mListView.setAdapter(adapter);
	}
	
	/**
	 * 设置点击项监听
	 * 
	 * @param listener
	 */
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mItemClickListener = listener;
		mListView.setOnItemClickListener(mDefaultAdapter);
	}
	
	/**
	 * 设置适配器
	 * 
	 * @param dataList
	 */
	public void setAdapter(List<String> dataList) {
		setDataList(dataList);
		setAdapter(mDefaultAdapter);
	}
	
	/**
	 * 显示到底部
	 * 
	 * @param parent
	 */
	public void show() {
		View view = ((Activity) mContext).getWindow().getDecorView(); // 添加到顶部视图
		showAtLocation(view, Gravity.BOTTOM, 0, 0);
	}
	
	/**
	 * 默认适配器
	 * 
	 * @author ChenRui
	 * 
	 */
	class DefaultAdapter extends BaseAdapter implements OnItemClickListener {
		
		@Override
		public int getCount() {
			return mSelectItems.size();
		}
		
		@Override
		public Object getItem(int position) {
			return mSelectItems.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView view;
			if (convertView == null) {
				view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.menu_list_item, null);
			}
			else {
				view = (TextView) convertView;
			}
			view.setText(getItem(position).toString());
			
			return view;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mCurrentItem = getItem(position).toString();
			if (mItemClickListener != null) {
				mItemClickListener.onItemClick(parent, view, position, id);
			}
			mSelectListener.onSelectChange(SelectPopupWindow.this, mCurrentItem);
		}
		
	}
	
	public void setDataList(List<String> dataList) {
		this.mSelectItems = dataList;
		mListView.invalidate();
		mDefaultAdapter.notifyDataSetChanged();
	}
	
	/**
	 * 获取当前选择的项
	 */
	public String getCurrentItem() {
		return mCurrentItem;
	}
	
	public void setOnSelectListener(onSelectListener listener) {
		this.mSelectListener = listener;
	}
	
}
