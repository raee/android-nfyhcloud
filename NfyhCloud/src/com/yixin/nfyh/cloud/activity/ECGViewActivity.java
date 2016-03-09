package com.yixin.nfyh.cloud.activity;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.data.ISignDevice;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.UserSigns;
import com.yixin.nfyh.cloud.ui.MyMarkerView;
import com.yixin.nfyh.cloud.ui.TimerToast;

/**
 * 心电查看，调用说明：<br>
 * 先通过{@link ECGViewActivity#addData(String, String)} 来设置数据，设置完成后调用
 * {@link ECGViewActivity#loadChart()} 来加载心电图
 * 
 * @author ChenRui
 * 
 */
@SuppressLint("SimpleDateFormat")
public class ECGViewActivity extends BaseActivity
{
	private LineChart				mChart;
	
	// X 坐标轴值
	private ArrayList<String>		mxVals		= new ArrayList<String>();
	
	// Y 坐标轴值
	private ArrayList<Entry>		myVals		= new ArrayList<Entry>();
	
	// 数据列
	private List<ILineDataSet>	mdataSets	= new ArrayList<ILineDataSet>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		mChart = new LineChart(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mChart.setLayoutParams(params);
		setContentView(mChart);
		
		// 初始化图表
		initChart();
		
		// 获取数据
		ISignDevice api = NfyhCloudDataFactory.getFactory(this).getSignDevice();
		
		try
		{
			SignTypes signType = api.getSignTypeByName("心电");
			List<UserSigns> datas = api.getUserSignByType(app.getCurrentUser().getUid(), signType); // 获取到心电数据
			
			for (UserSigns data : datas)
			{
				SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
				addData(df.format(data.getRecDate()), data.getSignValue());
			}
			
			// 加载图表
			loadChart();
		}
		catch (SQLException e)
		{
			TimerToast.show(this, "加载心电数据失败！");
			finish();
			e.printStackTrace();
		}
		
	}
	
	// 初始图表
	private void initChart()
	{
		mChart.setTouchEnabled(true);
		
		// 图示
		mChart.setDescription("心电图");
		mChart.setNoDataTextDescription("没有心电数据，请多多测量。");
		mChart.setDragDecelerationFrictionCoef(0.9f);
		
		// 启用缩放和点击
		mChart.setDragEnabled(true);
		mChart.setScaleEnabled(true);
		mChart.setDrawGridBackground(false);
		mChart.setHighlightPerDragEnabled(true);
		
		mChart.setPinchZoom(true);
		
		// 图表背景颜色
		mChart.setBackgroundColor(Color.BLACK);
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		
		// 设置图例样式
		Legend l = mChart.getLegend();
		l.setEnabled(false); // 不显示图例
		
		// 设置X轴样式
		XAxis xAxis = mChart.getXAxis();
		xAxis.setTypeface(tf);
		xAxis.setTextSize(12f);
		xAxis.setTextColor(Color.WHITE);
		xAxis.setDrawGridLines(true);
		xAxis.setDrawAxisLine(true);
		xAxis.setSpaceBetweenLabels(1);
		
		// 设置左侧坐标轴样式
		YAxis leftAxis = mChart.getAxisLeft();
		leftAxis.setTypeface(tf);
		leftAxis.setTextColor(Color.WHITE);
		leftAxis.setGridColor(ColorTemplate.getHoloBlue()); // 辅助线颜色
		leftAxis.setDrawGridLines(true); // 辅助线
		
		// 设置右侧坐标轴样式
		YAxis rightAxis = mChart.getAxisRight();
		rightAxis.setTypeface(tf);
		rightAxis.setTextColor(Color.WHITE);
		rightAxis.setGridColor(ColorTemplate.getHoloBlue()); // 辅助线颜色
		rightAxis.setStartAtZero(true);
		rightAxis.setDrawGridLines(true);
		
		// 点击时显示的
		MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
		
		// set the marker to the chart
		mChart.setMarkerView(mv);
	}
	
	// 加载图表
	public void loadChart()
	{
		// 创建数据
		LineDataSet set1 = new LineDataSet(myVals, "心电");
		set1.setAxisDependency(AxisDependency.LEFT);
		set1.setColor(Color.GREEN); // 线条颜色
		set1.setCircleColor(Color.WHITE); // 数据点颜色
		set1.setLineWidth(2f); // 线的宽度
		
		set1.setCircleSize(3f); // 数据点大小
		set1.setFillAlpha(65); // 填充透明度
		
		set1.setHighlightLineWidth(1f); // 设置高亮线条大小
		//set1.setHighLightColor(Color.rgb(244, 117, 117)); // 设置点击时高亮颜色
		set1.setDrawCircleHole(false);
		//		set1.setFillColor(Color.GREEN); // 填充颜色
		//set1.setDrawFilled(true); // 是否填充显示
		set1.setDrawValues(false); // 是否显示文字数据
		set1.setDrawCircles(false); // 是否显示数据点
		
		mdataSets.add(set1); // 添加数据
		
		// 根据数据创建
		LineData data = new LineData(mxVals, mdataSets);
		data.setValueTextColor(Color.WHITE);
		data.setValueTextSize(9f);
		
		// 设置数据
		mChart.setData(data);
		
		// 动画显示
		mChart.animateX(1000);
		
		mChart.moveViewTo(data.getXValCount() - 7, 50f, AxisDependency.RIGHT); // 移动到显示到最后
	}
	
	/**
	 * 设置数据
	 * 
	 * @param date
	 *            时间
	 * @param range
	 *            数据，如：0.2004,0.2004,0.2,0.1996,0.1996,0.1996,0.2,0.2004,0.2012,
	 */
	public void addData(String date, String range)
	{
		
		// 分解坐标
		String[] datas = range.split(",");
		int count = datas.length - 1;
		if (count <= 0) return; // 没有数据返回
		
		for (int i = 0; i < count; i++)
		{
			mxVals.add(date);
		}
		
		int size = myVals.size();
		
		for (int i = 0; i < count; i++)
		{
			float val = Float.parseFloat(datas[i]);
			myVals.add(new Entry(val, size + i));
		}
		
	}
}
