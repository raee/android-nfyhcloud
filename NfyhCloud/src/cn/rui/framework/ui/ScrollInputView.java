package cn.rui.framework.ui;

import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.dialog.WheelViewValueChangeListener;
import com.yixin.nfyh.cloud.model.view.DialogViewModel;

/**
 * 单项的滑动滚轮的视图
 * 
 * @author zhulin
 * 
 */
public class ScrollInputView extends RelativeLayout implements
		View.OnClickListener {

	// public interface ScrollInputChangedListener
	// {
	// public void onChanged(View view, String selectValue);
	//
	// public void onScrollFinsh(View view, String value);
	// }

	private Context mContext;
	private TextView tvSiTitle;
	private View titleView;

	private WheelViewValueChangeListener valueChangListener;
	private String[] oldVals, newVals;
	private List<DialogViewModel> model;
	private LinearLayout wvsgroud;
	private int dataType = DialogViewModel.TYPE_NUMBER;
	private int dataIndex;
	private boolean enableRepeat = true;

	private int index;

	/**
	 * 设置标题
	 */
	public void setTitle(String title) {
		if (title != null) {
			tvSiTitle.setText(title);
		}
	}

	/**
	 * 是否显示标题
	 * 
	 * @param val
	 */
	public void enableTitle(boolean val) {
		int visibility = val ? View.VISIBLE : View.GONE;
		titleView.setVisibility(visibility);
	}

	/**
	 * 是否允许重复
	 * 
	 * @param val
	 */
	public void setRepeat(boolean val) {
		this.enableRepeat = val;
	}

	public ScrollInputView(Context context) {
		super(context, null);
		this.mContext = context;
		LayoutInflater.from(mContext).inflate(R.layout.view_scroll_input, this);
		findView();
	}

	private void findView() {
		tvSiTitle = (TextView) findViewById(R.id.tv_si_title);
		wvsgroud = (LinearLayout) findViewById(R.id.wvsgroud);
		findViewById(R.id.tv_scroll_input_custome).setOnClickListener(this);
		titleView = findViewById(R.id.ll_si_title);
		this.findViewById(android.R.id.primary).setOnClickListener(this);
		this.findViewById(android.R.id.closeButton).setOnClickListener(this);
		this.setOnClickListener(this);
	}

	/**
	 * 初始化，必须调用。
	 * 
	 * @param model
	 */
	public void setup(List<DialogViewModel> model) {
		// 根据列表的大小装载
		this.model = model;
		oldVals = new String[model.size()];
		newVals = new String[model.size()];

		for (int index = 0; index < model.size(); index++) {
			DialogViewModel m = model.get(index); // 获取模型
			if (index == 0) {
				this.setTitle(m.getTitle()); // 设置主标题
				dataType = m.getDataType(); // 获取数据类型
			}
			dataIndex = m.getDataIndex();
			View view = getWheelView(m, index); // 获取试图

			// 设置布局
			android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			params.weight = 1;
			view.setLayoutParams(params);
			wvsgroud.addView(view); // 添加到视图
		}

	}

	/**
	 * 获取当前的View
	 * 
	 * @param m
	 * @param index
	 * @return
	 */
	private View getWheelView(DialogViewModel m, int index) {
		// 生成一个View
		View root = LayoutInflater.from(mContext).inflate(
				R.layout.view_wheelview_item, null);
		WheelView view = (WheelView) root.findViewById(R.id.wheel);
		WheelViewAdapter adapter = new WheelViewAdapter(this.mContext,
				m.getDatas());
		TextView tvUnit = (TextView) root.findViewById(R.id.tv_unit);
		tvUnit.setText(m.getSubTitle());
		view.setViewAdapter(adapter);
		view.addChangingListener(adapter);
		view.addScrollingListener(adapter);
		view.setTag(index);
		view.setCyclic(this.enableRepeat); // 循环

		// 如果设置了上一个设置了下一个，则获取上一个的值
		if (index - 1 >= 0 && index < model.size()) {
			String after = this.model.get(index - 1).getNextCurrentItem() + "";
			m.setCurrentItem(after);
			int currentItem = m.getCurrentItem();
			view.setCurrentItem(currentItem);
		} else {
			view.setCurrentItem(m.getCurrentItem());
		}

		return root;
	}

	/**
	 * 滚轮视图的适配器
	 * 
	 * @author 陈睿
	 * 
	 */
	private class WheelViewAdapter extends AbstractWheelTextAdapter implements
			OnWheelChangedListener, OnWheelScrollListener {
		private List<String> list;

		public WheelViewAdapter(Context context, List<String> list) {
			super(context, R.layout.view_scroll_input_item, R.id.tv_sit_content);
			this.list = list;
		}

		@Override
		public View getItem(int index, View convertView, ViewGroup parent) {
			View view = super.getItem(index, convertView, parent);
			return view;
		}

		// /**
		// * 设置单位
		// *
		// * @param val
		// */
		// public void setUnit(String val)
		// {
		// this.mUnit = val;
		// }

		@Override
		public int getItemsCount() {
			if (list != null && list.size() > 0) {
				return list.size();
			}
			return 0;
		}

		@Override
		public CharSequence getItemText(int arg0) {
			if (list != null && list.size() > 0) {
				return list.get(arg0);
			}
			return null;
		}

		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			select();
			View v = wheel.getItemView(wheel.getCurrentItem());
			TextView tv = (TextView) v.findViewById(R.id.tv_sit_content);
			tv.setText("--》选择");
			tv.setTextColor(context.getResources().getColor(R.color.red));
			notifyDataChangedEvent();
			notifyDataInvalidatedEvent();
		}

		@Override
		public void onChanged(WheelView view, int oldValue, int newValue) {
			index = Integer.parseInt(view.getTag().toString());
			if (index < oldVals.length) {
				oldVals[index] = model.get(index).getDatas().get(oldValue);
				newVals[index] = model.get(index).getDatas().get(newValue);
			}

		}
	}

	public void setValueChangListener(
			WheelViewValueChangeListener valueChangListener) {
		this.valueChangListener = valueChangListener;
	}

	/**
	 * 选择
	 */
	private String select() {
		String result = "";

		if (dataType == DialogViewModel.TYPE_FLOAT
				&& (newVals != null || newVals.length > 0)) {
			try {
				// 浮点类型，赋值为第一个为新的值
				double frist = Double.valueOf(newVals[0]);
				double second = Integer.valueOf(newVals[1]) * 0.1;
				newVals[0] = ((int) frist + second) + "";
				valueChangListener.onValueChange(dataIndex, oldVals[0],
						newVals[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (dataType == -2 && newVals.length > index) {
			valueChangListener.onValueChange(index, oldVals[index],
					newVals[index]);

			result = newVals[index];
		} else {
			valueChangListener.onValueChange(dataIndex, oldVals[0], newVals[0]);
		}

		result = newVals[0];
		return result;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case android.R.id.primary:
			this.valueChangListener.onValueFinsh(dataIndex, oldVals[0],
					select());
			break;
		case R.id.tv_scroll_input_custome: // 手动输入
			showInputDialog();
			break;
		default:
			this.valueChangListener.onCancleFinsh();
			break;
		}

	}

	/**
	 * 显示手动输入对话框
	 */
	private void showInputDialog() {
		valueChangListener.onCancleFinsh(); // 通知取消

		String name = tvSiTitle.getText().toString();
		new RuiDialog.Builder(mContext).buildTitle(name)
				.buildMessage("请输入" + name).buildEditText("请输入" + name)
				.buildLeftButton("返回", null)
				.buildRight("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						RuiDialog d = (RuiDialog) dialog;
						valueChangListener.onValueFinsh(index, d.getEditText(),
								d.getEditText());
						dialog.dismiss();
					}
				}).show();
	}

}
