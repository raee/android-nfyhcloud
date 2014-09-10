package com.yixin.nfyh.cloud.ui;

import com.yixin.nfyh.cloud.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TimerProgressDialog extends ProgressDialog
{

	private View			view;
	private TextView		tvMsg;
	private Context			context;

	private CharSequence	textMsg;

	public static TimerProgressDialog show(Context context, String message)
	{
		TimerProgressDialog dialog = new TimerProgressDialog(context);
		dialog.setMessage(message);
		dialog.show();
		return dialog;
	}

	public TimerProgressDialog(Context context)
	{
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		view = LayoutInflater.from(context).inflate(
				R.layout.ui_timer_progressdialog, null);
		tvMsg = (TextView) view.findViewById(R.id.toast_msg);

		tvMsg.setText(textMsg);
		setContentView(view);
		setCanceledOnTouchOutside(false);

		ImageView vv = (ImageView) findViewById(R.id.img_icon1);
		AnimationDrawable dw = (AnimationDrawable) vv.getDrawable();
		dw.start();

	}

	@Override
	public void setMessage(CharSequence message)
	{
		textMsg = message;
	}

}
