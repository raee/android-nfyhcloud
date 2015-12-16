package com.yixin.nfyh.cloud.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

/**
 * 父类适配器
 * 
 * @author ChenRui
 * 
 * @param <T>
 */
public abstract class BaseListAdapter<T> extends BaseAdapter implements
		OnItemClickListener {

	private List<T> mDatas;
	private Context mContext;
	private LayoutInflater mInflater;

	public BaseListAdapter(Context context, List<T> datas) {
		this.mDatas = datas;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {

		if (mDatas == null) {
			return 0;
		}
		return mDatas.size();
	}

	/**
	 * 获取数据项
	 * 
	 * @param position
	 *            索引
	 * @return
	 */
	public T getDataItem(int position) {
		if (mDatas == null)
			throw new NullPointerException("数据为空，getDataItem() 出错。");
		return mDatas.get(position);
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		T m = mDatas.get(position);
		AdapterViewHolder holder;

		if (convertView == null) {
			// 初始化
			convertView = getItemView(mInflater);
			holder = getViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (AdapterViewHolder) convertView.getTag();
		}

		// 填充视图数据
		fillViewData(holder, m);

		return convertView;
	}

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	public List<T> getData() {
		return mDatas;
	}

	/**
	 * 设置数据源
	 * 
	 * @param data
	 */
	public void setData(List<T> data) {
		mDatas = data;
	}

	/**
	 * 往数据源添加数据
	 * 
	 * @param data
	 */
	public void addData(T data) {
		if (mDatas == null) {
			mDatas = new ArrayList<T>();
		}
		mDatas.add(data);
	}

	/**
	 * 填充视图数据
	 * 
	 * @param holder
	 * @param m
	 */
	public abstract void fillViewData(AdapterViewHolder absHolder, T m);

	/**
	 * 获取视图实体
	 * 
	 * @param view
	 * @return
	 */
	public abstract AdapterViewHolder getViewHolder(View view);

	/**
	 * 第一次加载视图
	 * 
	 * @param inflater
	 * @return
	 */
	public abstract View getItemView(LayoutInflater inflater);

	protected View initView(int layoutId) {
		return mInflater.inflate(layoutId, null);
	}

	protected Context getContext() {
		return mContext;
	}

}
