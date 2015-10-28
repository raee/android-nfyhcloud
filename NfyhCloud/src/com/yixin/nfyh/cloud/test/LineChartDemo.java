package com.yixin.nfyh.cloud.test;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.yixin.nfyh.cloud.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

public class LineChartDemo extends Activity
{
	private LineChart				mChart;
	
	// X 坐标轴值
	private ArrayList<String>		mxVals		= new ArrayList<String>();
	
	// Y 坐标轴值
	private ArrayList<Entry>		myVals		= new ArrayList<Entry>();
	
	// 数据列
	private ArrayList<LineDataSet>	mdataSets	= new ArrayList<LineDataSet>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_linechart);
		mChart = (LineChart) findViewById(R.id.chart1);
		
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
		
		// if disabled, scaling can be done on x- and y-axis separately
		mChart.setPinchZoom(true);
		
		// 图表背景颜色
		mChart.setBackgroundColor(Color.LTGRAY);
		
		// 设置数据
		addData("2015-10-26 10:30:60",
				"0.2004,0.2004,0.2004,0.2,0.2,0.2,0.2004,0.2004,0.2004,0.2004,0.2004,0.2008,0.2008,0.2012,0.2012,0.2012,0.2012,0.2012,0.2012,0.2012,0.2016,0.2016,0.2016,0.2016,0.202,0.202,0.2016,0.2016,0.2016,0.2016,0.2016,0.202,0.202,0.202,0.202,0.202,0.2024,0.2024,0.2024,0.2028,0.2028,0.2028,0.2028,0.2028,0.2028,0.2028,0.2028,0.2024,0.202,0.202,0.202,0.2016,0.2016,0.2012,0.2016,0.2016,0.202,0.202,0.2024,0.2024,0.2024,0.202,0.202,0.202,0.202,0.2016,0.2016,0.2016,0.2016,0.2016,0.202,0.202,0.202,0.202,0.202,0.202,0.202,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.202,0.202,0.202,0.202,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.202,0.2024,0.2024,0.2024,0.202,0.202,0.2016,0.2016,0.2016,0.2016,0.202,0.202,0.202,0.202,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.202,0.202,0.2024,0.2028,0.2036,0.2044,0.2048,0.2052,0.206,0.2068,0.2076,0.208,0.2084,0.2088,0.2096,0.21,0.2108,0.212,0.2132,0.2148,0.216,0.2176,0.2188,0.22,0.2204,0.2204,0.2196,0.2184,0.2172,0.2156,0.214,0.2128,0.212,0.2112,0.2104,0.21,0.2088,0.208,0.2068,0.2056,0.2048,0.204,0.2032,0.2024,0.2016,0.2008,0.2,0.1996,0.1992,0.1992,0.1988,0.1988,0.1988,0.1988,0.1992,0.1992,0.1992,0.1988,0.1988,0.1984,0.1984,0.198,0.1976,0.1972,0.1968,0.1968,0.1968,0.1972,0.198,0.2,0.2028,0.2064,0.2112,0.2176,0.2244,0.2324,0.2408,0.2504,0.2608,0.2712,0.2804,0.2868,0.2892,0.2872,0.2808,0.27,0.256,0.2408,0.2256,0.2116,0.1992,0.1884,0.1796,0.1736,0.1696,0.168,0.1676,0.1688,0.1708,0.1732,0.176,0.1788,0.1824,0.1864,0.1904,0.1936,0.1964,0.1984,0.1996,0.1996,0.1988,0.198,0.1972,0.1968,0.1968,0.1976,0.1984,0.1992,0.2,0.2004,0.2004,0.2,0.1996,0.1996,0.1996,0.2,0.2004,0.2012,0.2016,");
		
		//addData("2015-10-26 20:00:00",
		//		"0.3004,0.3004,0.3004,0.3,0.2,0.2,0.2004,0.2004,0.2004,0.2004,0.2004,0.2008,0.2008,0.2012,0.2012,0.2012,0.2012,0.2012,0.2012,0.2012,0.2016,0.2016,0.2016,0.2016,0.202,0.202,0.2016,0.2016,0.2016,0.2016,0.2016,0.202,0.202,0.202,0.202,0.202,0.2024,0.2024,0.2024,0.2028,0.2028,0.2028,0.2028,0.2028,0.2028,0.2028,0.2028,0.2024,0.202,0.202,0.202,0.2016,0.2016,0.2012,0.2016,0.2016,0.202,0.202,0.2024,0.2024,0.2024,0.202,0.202,0.202,0.202,0.2016,0.2016,0.2016,0.2016,0.2016,0.202,0.202,0.202,0.202,0.202,0.202,0.202,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.202,0.202,0.202,0.202,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.202,0.2024,0.2024,0.2024,0.202,0.202,0.2016,0.2016,0.2016,0.2016,0.202,0.202,0.202,0.202,0.2016,0.2016,0.2016,0.2016,0.2016,0.2016,0.202,0.202,0.2024,0.2028,0.2036,0.2044,0.2048,0.2052,0.206,0.2068,0.2076,0.208,0.2084,0.2088,0.2096,0.21,0.2108,0.212,0.2132,0.2148,0.216,0.2176,0.2188,0.22,0.2204,0.2204,0.2196,0.2184,0.2172,0.2156,0.214,0.2128,0.212,0.2112,0.2104,0.21,0.2088,0.208,0.2068,0.2056,0.2048,0.204,0.2032,0.2024,0.2016,0.2008,0.2,0.1996,0.1992,0.1992,0.1988,0.1988,0.1988,0.1988,0.1992,0.1992,0.1992,0.1988,0.1988,0.1984,0.1984,0.198,0.1976,0.1972,0.1968,0.1968,0.1968,0.1972,0.198,0.2,0.2028,0.2064,0.2112,0.2176,0.2244,0.2324,0.2408,0.2504,0.2608,0.2712,0.2804,0.2868,0.2892,0.2872,0.2808,0.27,0.256,0.2408,0.2256,0.2116,0.1992,0.1884,0.1796,0.1736,0.1696,0.168,0.1676,0.1688,0.1708,0.1732,0.176,0.1788,0.1824,0.1864,0.1904,0.1936,0.1964,0.1984,0.1996,0.1996,0.1988,0.198,0.1972,0.1968,0.1968,0.1976,0.1984,0.1992,0.2,0.2004,0.2004,0.2,0.1996,0.1996,0.1996,0.2,0.2004,0.2012,0.2016,");
		
		loadChart();
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		
		// 设置图例样式
		Legend l = mChart.getLegend();
		l.setForm(LegendForm.LINE);
		l.setTypeface(tf);
		l.setTextSize(11f);
		l.setTextColor(Color.WHITE);
		l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		
		// 设置X轴样式
		XAxis xAxis = mChart.getXAxis();
		xAxis.setTypeface(tf);
		xAxis.setTextSize(12f);
		xAxis.setTextColor(Color.RED);
		xAxis.setDrawGridLines(true);
		xAxis.setDrawAxisLine(true);
		xAxis.setSpaceBetweenLabels(1);
		
		// 设置左侧坐标轴样式
		YAxis leftAxis = mChart.getAxisLeft();
		leftAxis.setTypeface(tf);
		leftAxis.setTextColor(ColorTemplate.getHoloBlue());
		//leftAxis.setAxisMaxValue(1f); // 坐标最大值
		//leftAxis.setAxisMinValue(0); // 坐标最小值
		leftAxis.setDrawGridLines(true);
		
		// 设置右侧坐标轴样式
		YAxis rightAxis = mChart.getAxisRight();
		rightAxis.setTypeface(tf);
		rightAxis.setTextColor(ColorTemplate.getHoloBlue());
		//rightAxis.setAxisMaxValue(1f);
		rightAxis.setStartAtZero(true);
		rightAxis.setDrawGridLines(true);
		
	}
	
	// 加载图表
	private void loadChart()
	{
		// 创建数据
		LineDataSet set1 = new LineDataSet(myVals, "心电");
		set1.setAxisDependency(AxisDependency.LEFT);
		set1.setColor(Color.GREEN); // 线条颜色
		set1.setCircleColor(Color.WHITE); // 数据点颜色
		set1.setLineWidth(2f); // 线的宽度
		set1.setCircleSize(3f); // 数据点大小
		set1.setFillAlpha(65); // 填充透明度
		set1.setFillColor(Color.GREEN); // 填充颜色
		set1.setHighLightColor(Color.rgb(244, 117, 117)); // 设置点击时高亮颜色
		set1.setDrawCircleHole(false);
		set1.setDrawFilled(true); // 是否填充显示
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
	}
	
	/**
	 * 设置数据
	 * 
	 * @param date
	 *            时间
	 * @param range
	 *            数据，如：0.2004,0.2004,0.2,0.1996,0.1996,0.1996,0.2,0.2004,0.2012,
	 */
	private void addData(String date, String range)
	{
		
		// 分解坐标
		String[] datas = range.split(",");
		int count = datas.length - 1;
		if (count <= 0) return; // 没有数据返回
		
		for (int i = 0; i < count; i++)
		{
			if (i == 0)
			{
				mxVals.add(date); // 显示日期
			}
			else
			{
				mxVals.add("");
			}
		}
		
		
		for (int i = 0; i < count; i++)
		{
			float val = Float.parseFloat(datas[i]);
			
			myVals.add(new Entry(val, myVals.size() + i));
		}
		
		
	}
}
