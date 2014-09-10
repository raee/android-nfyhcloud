package com.yixin.nfyh.cloud.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.yixin.nfyh.cloud.R;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.rui.framework.ui.ScrollInputView;
import cn.rui.framework.utils.DateUtil;
import cn.rui.framework.widget.RuiSwitch;
import cn.rui.framework.widget.RuiSwitch.OnCheckedChangeListener;

import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.alarm.AlarmControl;
import com.yixin.nfyh.cloud.dialog.DialogManager;
import com.yixin.nfyh.cloud.dialog.DialogPopupWindowListener;
import com.yixin.nfyh.cloud.dialog.WheelViewValueChangeListener;
import com.yixin.nfyh.cloud.model.Clocks;
import com.yixin.nfyh.cloud.model.view.DialogViewModel;

public class AlarmNormalAddActivity extends BaseActivity implements
		WheelViewValueChangeListener, OnCheckedChangeListener
{
	Calendar				calendar	= Calendar.getInstance();
	private TextView		tvEnd;
	private TextView		tvStart;
	private ViewGroup		content;
	private Clocks			model;
	private ScrollInputView	siv;
	private RuiSwitch		swRepeat;
	private View			viewStart;
	private AlarmControl	alarmControl;
	private EditText		tvTitle;
	private EditText		tvContent;
	private String[]		selectTime	= new String[2];			//选择的日期[时，分]
	private TextView		tvSpan;
	private View			viewSpan;
	private View			btnSave;
	private View			btnDelete;
//	private int				type		= 0;						//0：新增，1：更新
																	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_add_normal);
		
		alarmControl = new AlarmControl(this);
		calendar.setTimeInMillis(System.currentTimeMillis());
		findView();
		
		Intent intent = getIntent();
		if (intent != null && intent.getExtras() != null)
		{
			initConfig((Clocks) intent.getSerializableExtra(Intent.EXTRA_TEXT));
		}
		else
		{
			initConfig(null);
			this.btnDelete.setVisibility(View.GONE);//隐藏删除按钮
		}
	}
	
	private void initConfig(Clocks m)
	{
		this.model = m;
		if (model == null)
		{
			model = new Clocks();
			model.setTitle(null);
			model.setContent(null);
			model.setStartDate(new Date());
			model.setRepeatCount(0); //不重复，单次闹钟
		}
		
		calendar.setTime(model.getStartDate()); //设置当前时间
		int[] start = DateUtil.getCurrentDate(calendar);
		selectTime[0] = start[DateUtil.HOUR] + "";
		selectTime[1] = start[DateUtil.MINUTE] + "";
		
		siv = new ScrollInputView(this);
		
		List<DialogViewModel> models = new ArrayList<DialogViewModel>();
		int[] current = DateUtil.getCurrentDate(calendar);
		
		DialogViewModel hour = new DialogViewModel(); //时
		hour.setSubTitle("时");
		hour.enableZero(true);
		hour.setDatas(0, 23);
		hour.setCurrentItem(current[DateUtil.HOUR]);
		hour.setNextCurrentItem(current[DateUtil.MINUTE]);
		hour.setDataType(-2);
		
		models.add(hour);
		
		DialogViewModel min = new DialogViewModel(); //分
		min.setSubTitle("分");
		min.enableZero(true);
		min.setDatas(0, 59);
		min.setCurrentItem(current[DateUtil.MINUTE]);
		min.setDataType(-2);
		
		models.add(min);
		
		siv.enableTitle(false);
		siv.setRepeat(true);
		siv.setValueChangListener(this);
		siv.setup(models);
		siv.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 450));
		
		content.addView(siv, 0);
		calendar.setTime(model.getStartDate());
		
		tvTitle.setText(model.getTitle());
		tvContent.setText(model.getContent());
		if (model.getRepeatCount() > 0)
		{
			this.swRepeat.setChecked(true);
		}
		
		if (model.getEndDate() != null)
		{
			calendar.setTime(model.getEndDate());
			tvEnd.setText(model.getEndDate() == null ? "无" : DateUtil
					.getDateString("yyyy-MM-dd", calendar));
		}
		tvStart.setText(DateUtil.getDateString("yyyy-MM-dd", calendar));
		
		tvSpan.setText(model.getRepeatSpan() > 0 ? model.getRepeatSpan() + "分钟"
				: "不重复");
	}
	
	@Override
	protected void findView()
	{
		content = (ViewGroup) findViewById(R.id.ll_alarm_add_normal_content);
		
		this.viewStart = findViewById(R.id.ll_alram_normal_add_start);
		findViewById(R.id.ll_alram_normal_add_end).setOnClickListener(this);
		findViewById(R.id.ll_alarm_normal_add_repeat).setOnClickListener(this);
		this.viewSpan = findViewById(R.id.ll_alram_normal_add_span);
		this.btnSave = findViewById(R.id.btn_alarm_normal_add_save);
		this.btnDelete = findViewById(R.id.btn_alarm_normal_add_del);
		
		this.viewStart.setOnClickListener(this);
		this.viewSpan.setOnClickListener(this);
		this.btnDelete.setOnClickListener(this);
		this.btnSave.setOnClickListener(this);
		
		this.tvStart = (TextView) findViewById(R.id.tv_alarm_normal_add_start);
		this.tvEnd = (TextView) findViewById(R.id.tv_alarm_normal_add_end);
		this.tvSpan = (TextView) findViewById(R.id.tv_alarm_normal_span);
		this.tvTitle = (EditText) findViewById(R.id.et_alarm_normal_add_title);
		this.tvContent = (EditText) findViewById(R.id.et_alarm_normal_add_content);
		this.swRepeat = (RuiSwitch) findViewById(R.id.sw_alarm_normal_add_enable_repeat);
		this.swRepeat.setOnCheckedChangeListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		
		switch (v.getId())
		{
			case R.id.ll_alram_normal_add_start:
				showDatePickerDialog(0);
				break;
			case R.id.ll_alram_normal_add_end:
				showDatePickerDialog(1);
				break;
			case R.id.ll_alarm_normal_add_repeat:
				swRepeat.performClick(); //触发单击
				break;
			case R.id.ll_alram_normal_add_span: //重复间隔
				showDateSpan();
				break;
			case R.id.btn_alarm_normal_add_save: //保存闹钟
				save();
				break;
			case R.id.btn_alarm_normal_add_del: //删除闹钟
				delete();
				break;
			default:
				break;
		}
	}
	
	private void showDateSpan()
	{
		List<DialogViewModel> models = new ArrayList<DialogViewModel>();
		DialogViewModel m = new DialogViewModel();
		List<String> datas = new ArrayList<String>();
		datas.add("不重复");
		for (int i = 1; i <= 30; i++)
		{
			datas.add(i + "");
		}
		m.setTitle("重复间隔");
		m.setDatas(datas);
		m.setSubTitle("分钟");
		String spantext = tvSpan.getText().toString();
		String currentItem = spantext.equals("不重复") ? spantext : spantext
				.replace("分钟", "");
		m.setCurrentItem(currentItem);
		models.add(m);
		
		DialogManager dm = new DialogManager(this, 0,
				new DialogPopupWindowListener()
				{
					
					@Override
					public void onDialogChange(PopupWindow dialog, int which,
							String values)
					{
						if (values.equals("不重复"))
						{
							tvSpan.setText("不重复");
							model.setRepeatSpan(-1);
						}
						else
						{
							int span = Integer.valueOf(values);
							tvSpan.setText(values + "分钟");
							model.setRepeatSpan(span);
						}
					}
					
					@Override
					public void onDialogCancle(PopupWindow dialog,
							String... values)
					{
						
					}
					
					@Override
					public boolean getdialogValidate(String... values)
					{
						return true;
					}
					
					@Override
					public void onDialogFinsh(PopupWindow dialog, int which,
							String values)
					{
						// TODO Auto-generated method stub
						
					}
				}, models);
		
		dm.show(getWindow().getDecorView());
	}
	
	private void delete()
	{
		alarmControl.deleteAlarm(model);
		this.finish();
	}
	
	private void save()
	{
		String title = tvTitle.getText().toString().trim(); //标题
		if (title.isEmpty())
		{
			showMsg("提醒标题不能为空");
			return;
		}
		model.setTitle(title);
		model.setContent(tvContent.getText().toString().trim()); // 内容
		
		String startDate = tvStart.getText().toString() + " " + selectTime[0]
				+ ":" + selectTime[1];// 开始时间
		
		String endDate = tvEnd.getText().toString();
		endDate = endDate.equals("无") ? null : endDate;
		
		model.setStartDate(DateUtil.parser("yyyy-MM-dd HH:mm", startDate));
		
		alarmControl.setAlarm(model);
		this.finish();
	}
	
	@Override
	protected String getActivityName()
	{
		return getString(R.string.activity_alarm_add);
	}
	
	/**
	 * 显示日期选择对话框
	 * 
	 * @param which
	 *            0：开始日期，1：结束日期
	 */
	private void showDatePickerDialog(final int which)
	{
		if (which == 0) //开始时间
		{
			calendar.setTime(model.getStartDate());
		}
		else if (which == 1 && model.getEndDate() != null)
		{
			calendar.setTime(model.getEndDate());
		}
		
		int start[] = DateUtil.getCurrentDate(calendar);
		int s_year = start[DateUtil.YEAR];
		int s_monthOfYear = start[DateUtil.MONTH];
		int s_dayOfMonth = start[DateUtil.DAY];
		DatePickerDialog dialog = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener()
				{
					
					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth)
					{
						String val = String.format("%s-%s-%s", year,
								monthOfYear + 1, dayOfMonth);
						
						calendar.set(Calendar.YEAR, year);
						calendar.set(Calendar.MONTH, monthOfYear);
						calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
						
						if (which == 0)
						{
							model.setStartDate(calendar.getTime());
							tvStart.setText(val);
						}
						else
						{
							model.setEndDate(calendar.getTime());
							tvEnd.setText(val);
						}
						
					}
				}, s_year, s_monthOfYear, s_dayOfMonth);
		
		if (which == 1)
		{
			dialog.setCancelable(true);
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
			{
				
				@Override
				public void onCancel(DialogInterface dialog)
				{
					model.setEndDate(null);
					tvEnd.setText("无");
				}
			});
		}
		
		dialog.show();
	}
	
	@Override
	public void onValueChange(int which, String oldValues, String newValues)
	{
		if (selectTime.length > which)
		{
			selectTime[which] = newValues;
		}
		Log.i("ttt", newValues);
	}
	
	@Override
	public void onCheckedChanged(RuiSwitch switchView, boolean isChecked)
	{
		if (isChecked) //开启每天重复
		{
			this.viewStart.setVisibility(View.GONE); //隐藏开始时间
			this.viewSpan.setVisibility(View.GONE);
			model.setRepeatCount(1); //设置重复次数为1，每天重复
			model.setRepeatSpan(-1);
		}
		else
		{
			this.viewStart.setVisibility(View.VISIBLE);
			this.viewSpan.setVisibility(View.VISIBLE);
			model.setRepeatCount(0); //设置重复次数为0
			model.setStartDate(DateUtil.parser("yyyy-MM-dd", tvStart.getText()
					.toString()));
		}
	}
	
	@Override
	public void onValueFinsh(int which, String oldValues, String newValues)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancleFinsh()
	{
		
	}
}
