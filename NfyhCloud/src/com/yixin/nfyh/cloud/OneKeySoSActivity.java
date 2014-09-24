package com.yixin.nfyh.cloud;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yixin.nfyh.cloud.sos.FloweDirector;
import com.yixin.nfyh.cloud.sos.OnekeySosCallback;

/**
 * 一键呼救 <br>
 * 自动呼救：<br>
 * 
 * @author MrChenrui
 * 
 */
public class OneKeySoSActivity extends BaseActivity implements OnekeySosCallback
{

	public static final String	EXTRA_EVENT_TYPE		= "Event";

	public static final String	EXTRA_EVENT_DATA_AUTO	= "EXTRA_EVENT_DATA_AUTO";

	private TextView			tvLocation, tvMsg;

	// private ISOS sos; // 一键急救主要类
	private ImageView			imgSosRotate;

	private Animation			operatingAnim;

	private ImageView			imgLocation;

	private TextView			tvErrorMsg;

	private boolean				isAnim					= false;

	private FloweDirector		sos;

	private View				fl;

	private ViewGroup			curView;

	private boolean				isShowRed				= true;

	// implements IToast
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		curView = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_onekeysos, null);
		setContentView(curView);
		// this.sos = new OneKeySos(this, this);
		this.sos = new FloweDirector(this, this);
		fl = new FrameLayout(this);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   // 应用运行时，保持屏幕高亮，不锁屏
		findView();
		String evenName = getIntent().getStringExtra(EXTRA_EVENT_TYPE);
		if (evenName != null)
		{
			if (evenName.equals(EXTRA_EVENT_DATA_AUTO)) this.sos.start();
			else this.sos.start(evenName);
		}
	}

	/**
	 * 开始动画，安全的
	 */
	private void startAnim()
	{
		if (operatingAnim != null && !operatingAnim.hasEnded())
		{
			// 已经开始动画
			return;
		}
		operatingAnim = AnimationUtils.loadAnimation(this, R.anim.spinning);
		LinearInterpolator lin = new LinearInterpolator(); // LinearInterpolator为匀速效果，Accelerateinterpolator为加速效果、DecelerateInterpolator为减速效果，具体可见下面android:interpolator的介绍。
		operatingAnim.setInterpolator(lin);
		this.imgSosRotate.startAnimation(operatingAnim);
		this.isAnim = true;
	}

	/**
	 * 停止动画，安全的
	 */
	private void stopAnim()
	{
		if (operatingAnim == null)
		{
			// 没有动画
			return;
		}
		operatingAnim.cancel();
		this.imgSosRotate.clearAnimation();
		this.isAnim = false;
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.img_sos_rotate:
				if (!isAnim)
				{
					// 第一次按下急救按钮
					this.startAnim();
					this.sos.start();
					this.tvErrorMsg.setVisibility(View.GONE);
				} else
				{
					// 再次按下急救按钮
					this.sos.stop();
					this.stopAnim();
					this.sosError(0, "用户主动放弃呼救");
				}
				break;
			default:
				break;
		}
	}

	@Override
	protected void findView()
	{
		this.imgSosRotate = (ImageView) findViewById(R.id.img_sos_rotate);
		this.tvMsg = (TextView) findViewById(R.id.tv_sos_msg);
		this.tvLocation = (TextView) findViewById(R.id.tv_sos_address);
		this.imgLocation = (ImageView) findViewById(R.id.img_sos_location);
		this.tvErrorMsg = (TextView) findViewById(R.id.tv_sos_error);
		this.imgSosRotate.setOnClickListener(this); // 点击呼救
	}

	public void setLocation(String address)
	{
		if (address == null || address.equals("")) address = "未获取到地址信息";
		this.imgLocation.setVisibility(View.VISIBLE);
		this.tvLocation.setVisibility(View.VISIBLE);
		this.tvLocation.setText(address);
	}

	public void show(String msg)
	{
		this.tvMsg.setText(msg);
		this.tvMsg.setTextColor(getResources().getColor(R.color.green));
	}

	@Override
	public void sosStarted()
	{
		startAnim();
		show("正在准备定位");
	}

	@Override
	public void sosLocated(String jwd, String address)
	{
		show("定位成功，轻敲按钮呼救");
		setLocation(address);
	}

	@Override
	public void sosUploadCloudSuccess(String response)
	{
	}

	@Override
	public void sosCalled(String num)
	{
		show("正在准备拨打电话：" + num);
	}

	@Override
	public void sosFinsh(String msg)
	{
		stopAnim();
		if (msg == null) show("轻敲上面按钮继续呼救");
		else
		{
			show(msg);
			showSos();
		}
	}

	/**
	 * 闪屏显示
	 */
	private void showSos()
	{
		final Handler h = new Handler(new Handler.Callback()
		{

			@Override
			public boolean handleMessage(Message msg)
			{
				switch (msg.what)
				{
					case 0:
						fl.setBackgroundResource(R.color.white);
						break;
					case 21:
						try
						{
							curView.removeView(fl);
							sos.stop();
							isShowRed = false;
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						break;
					default:
						fl.setBackgroundResource(R.color.red);
						break;
				}
				return false;
			}
		});
		try
		{
			fl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			fl.setBackgroundResource(R.color.red);
			curView.addView(fl);
			isShowRed = true;
			fl.setOnLongClickListener(new View.OnLongClickListener()
			{

				@Override
				public boolean onLongClick(View v)
				{
					Message.obtain(h, 21).sendToTarget();
					return true;
				}
			});
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		new Thread(new Runnable()
		{

			private long	time	= 150;

			private int		what	= 0;

			@Override
			public void run()
			{
				try
				{
					while (isShowRed)
					{
						if (what == 0) what = 1;
						else what = 0;
						Message.obtain(h, what).sendToTarget();
						Thread.sleep(time);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void sosError(int code, String msg)
	{
		showError(msg);
		if (msg.startsWith("用户") || msg.startsWith("您没有"))
		{
			sosFinsh(null);
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		this.sos.stop();
	}

	private void showError(String msg)
	{
		this.tvErrorMsg.setVisibility(View.VISIBLE);
		this.tvErrorMsg.setText(msg);
	}
}
