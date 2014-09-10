package cn.rui.framework.ui;

import com.yixin.nfyh.cloud.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 对话框控件
 * 
 * @author 睿
 * 
 */
public class RuiDialog extends Dialog {
	/**
	 * 警告
	 */
	public static final int TYPE_ALERT = R.drawable.dialog_title_delete_icon;
	/**
	 * 询问
	 */
	public static final int TYPE_ASK = R.drawable.dialog_title_confirm_icon;
	/**
	 * 默认
	 */
	public static final int TYPE_DEFAULT = R.drawable.dialog_title_default_icon;
	/**
	 * 设置
	 */
	public static final int TYPE_SETTING = R.drawable.dialog_title_setting_icon;

	private View dialogView;
	private TextView tvTitle;
	private ImageView imgIcon;
	private TextView tvMsg;
	private Button btnLeft;
	private Button btnRight;
	private LinearLayout contentView;
	private EditText editText;
	private BaseAdapter adapter;
	private OnClickListener itemSelectListener;
	private Object tag;

	public RuiDialog(Context context) {
		super(context, R.style.RuiDialog);
		dialogView = LayoutInflater.from(context).inflate(
				R.layout.rui_dialog_normal, null);

		contentView = (LinearLayout) dialogView
				.findViewById(R.id.rui_layout_dialog_content);

		editText = (EditText) dialogView.findViewById(R.id.edittext);

		this.tvTitle = (TextView) dialogView
				.findViewById(R.id.rui_tv_dialog_title);
		this.tvMsg = (TextView) dialogView.findViewById(R.id.rui_tv_dialog_msg);
		this.imgIcon = (ImageView) dialogView
				.findViewById(R.id.rui_img_dialog_icon);
		this.btnLeft = (Button) dialogView
				.findViewById(R.id.rui_btn_dialog_cancle);
		this.btnRight = (Button) dialogView
				.findViewById(R.id.rui_btn_dialog_sure);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(dialogView);
		getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		if (this.adapter != null)
			this.initAdapter();

	}

	/**
	 * 设置对话框的标题
	 * 
	 * @param title
	 */
	@Override
	public void setTitle(CharSequence title) {
		this.tvTitle.setText(title);
	}

	/**
	 * 设置提示的内容
	 * 
	 * @param message
	 */
	public void setMessage(CharSequence message) {
		this.tvMsg.setVisibility(View.VISIBLE);
		this.tvMsg.setText(message);
	}

	/**
	 * 设置对话框的图标
	 * 
	 * @param resId
	 *            请参考RuiDialog的静态变量
	 */
	public void setIcon(int resId) {
		this.imgIcon.setImageResource(resId);
	}

	/**
	 * 设置标签，一般的设置一些可携带的数据
	 * 
	 * @param tag
	 */
	public void setTag(Object tag) {
		this.tag = tag;
	}

	/**
	 * 获取标签
	 * 
	 * @return
	 */
	public Object getTag() {
		return tag;
	}

	/**
	 * 设置按钮
	 * 
	 * @param whichButton
	 *            请参考 RuiDialog.BUTTON_NEUTRAL
	 * @param text
	 *            按钮标题
	 * @param listener
	 *            按钮点击的监听者
	 */
	public void setButton(int whichButton, CharSequence text,
			OnClickListener listener) {
		if (listener == null) {
			listener = new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			};
		}

		switch (whichButton) {
		case DialogInterface.BUTTON_NEUTRAL:
			// 右边按钮
			btnRight.setVisibility(View.VISIBLE);
			btnRight.setText(text);
			new DialogClickListener(btnRight, listener, whichButton);
			break;
		case DialogInterface.BUTTON_NEGATIVE:
		case DialogInterface.BUTTON_POSITIVE:
			btnLeft.setVisibility(View.VISIBLE);
			btnLeft.setText(text);
			// 左边按钮
			new DialogClickListener(btnLeft, listener, whichButton);
			// 单个按钮
			break;
		default:
			break;
		}
	}

	public void setLeftButton(CharSequence text, OnClickListener listener) {
		this.setButton(DialogInterface.BUTTON_NEGATIVE, text, listener);
	}

	public void setRightButton(CharSequence text, OnClickListener listener) {
		this.setButton(DialogInterface.BUTTON_NEUTRAL, text, listener);
	}

	public void setSigleButton(CharSequence text, OnClickListener listener) {
		this.setButton(DialogInterface.BUTTON_NEUTRAL, text, listener);
	}

	/**
	 * 设置一个自定义的视图
	 * 
	 * @param view
	 */
	public void setView(View view) {
		this.tvMsg.setVisibility(View.GONE);
		this.editText.setVisibility(View.GONE);
		contentView.addView(view);
	}

	/**
	 * 在对话框中设置一个编辑框
	 * 
	 * @param msg
	 *            消息，相当于setMessage(msg);
	 * @param hintMsg
	 *            编辑框的提示信息
	 */
	public void setEditMessage(String msg, String hintMsg) {
		// 设置消息
		setMessage(msg);
		editText.setVisibility(View.VISIBLE);
		editText.setHint(hintMsg);

	}

	/**
	 * 设置输入框的输入面板
	 * 
	 * @param inputType
	 */
	public void setEditInputtype(int inputType) {
		editText.setInputType(inputType);
	}

	/**
	 * 在对话框中设置一个编辑框
	 * 
	 * @param msg
	 *            消息，相当于setMessage(msg);
	 * @param hintMsg
	 *            编辑框的提示信息
	 */
	public void setEditMessage(String msg) {
		// 设置消息
		setMessage(msg);
		editText.setVisibility(View.VISIBLE);
		editText.setText("");

	}

	/**
	 * 修改编辑框内容
	 * 
	 * @param text
	 * @param hintMsg
	 */
	public void setEditText(String text, String hintMsg) {
		setEditText(text);
		editText.setHint(hintMsg);
	}

	/**
	 * 修改编辑框内容
	 * 
	 * @param text
	 * @param hintMsg
	 */
	public void setEditText(String text) {
		editText.setVisibility(View.VISIBLE);
		editText.setText(text);
	}

	/**
	 * 设置一个数据提供的Adapter
	 * 
	 * @param adapter
	 */
	public void setAdapter(BaseAdapter adapter) {
		this.dialogView.findViewById(R.id.rui_dialog_ll_main).setPadding(0, 0,
				0, 0);
		this.contentView.setPadding(0, 0, 0, 0);
		this.adapter = adapter;
	}

	@Override
	public void show() {
		if (!this.getEditText().equals(""))
			this.editText.setText("");
		super.show();
	}

	/**
	 * 只有设置了Adapter才有，当一个选项被选中的时时候触发
	 * 
	 * @param l
	 */
	public void setOnItemSelectListener(OnClickListener l) {
		this.itemSelectListener = l;
	}

	// 初始化适配器
	private void initAdapter() {
		ScrollView sv = new ScrollView(getContext());
		LinearLayout ll = new LinearLayout(getContext());
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);

		int count = this.adapter.getCount();
		for (int i = 0; i < count; i++) {
			View v = this.adapter.getView(i, null, contentView);
			DialogClickListener dialogl = new DialogClickListener(v,
					this.itemSelectListener, i);
			dialogl.setDismissAble(true);
			ll.addView(v, i);
		}
		this.setView(sv);
	}

	/**
	 * 设置一些列表
	 * 
	 * @param strings
	 */
	public void setItems(final String... strings) {
		BaseAdapter normalAdapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					View v = LayoutInflater.from(getContext()).inflate(
							R.layout.rui_view_dialog_item, null);

					convertView = v;
				}

				TextView tv = (TextView) convertView
						.findViewById(R.id.rui_tv_dialog_item);
				tv.setText(getItem(position).toString());

				return convertView;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				return strings[position];
			}

			@Override
			public int getCount() {
				return strings.length;
			}
		};

		setAdapter(normalAdapter);

	}

	/**
	 * 获取编辑框的内容
	 * 
	 * @return
	 */
	public String getEditText() {
		if (editText != null)
			return editText.getText().toString();
		else
			return "";
	}

	/**
	 * 对话框按钮事件监听者转换为Click事件监听
	 * 
	 * @author 睿
	 * 
	 */
	private class DialogClickListener implements View.OnClickListener {
		private OnClickListener l;
		private int which;
		private boolean dismissable;

		public DialogClickListener(View view, OnClickListener l, int which) {
			this.l = l;
			this.which = which;
			view.setOnClickListener(this);
		}

		public void setDismissAble(boolean value) {
			this.dismissable = value;
		}

		@Override
		public void onClick(View v) {
			l.onClick(RuiDialog.this, which);
			if (dismissable)
				RuiDialog.this.dismiss();
		}

	}

	public final static class Builder {
		private RuiDialog dialog;
		private String msg;

		public Builder(Context context) {
			dialog = new RuiDialog(context);
		}

		public Builder buildTitle(String title) {
			this.dialog.setTitle(title);
			return this;
		}

		public Builder buildMessage(String msg) {
			this.msg = msg;
			this.dialog.setMessage(msg);
			return this;
		}

		public Builder buildEditText(String hintMsg) {
			this.dialog.setEditMessage(msg, hintMsg);
			return this;
		}

		public Builder buildButton(String text, OnClickListener listener) {
			this.dialog.setSigleButton(text, listener);
			return this;
		}

		public Builder buildLeftButton(String text, OnClickListener listener) {
			this.dialog.setLeftButton(text, listener);
			return this;
		}

		public Builder buildRight(String text, OnClickListener listener) {
			this.dialog.setRightButton(text, listener);
			return this;
		}

		public void show() {
			this.dialog.show();
		}

	}

	/**
	 * 设置只能输入数字
	 */
	public void editNumeric() {
		if (editText != null) {
			editText.setKeyListener(new DigitsKeyListener(false, true));
		}
	}

}
