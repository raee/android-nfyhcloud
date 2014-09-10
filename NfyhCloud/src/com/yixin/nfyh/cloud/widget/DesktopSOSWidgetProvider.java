package com.yixin.nfyh.cloud.widget;

import com.yixin.nfyh.cloud.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.yixin.nfyh.cloud.OneKeySoSActivity;

public class DesktopSOSWidgetProvider extends AppWidgetProvider
{
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds)
	{
		// TODO 触发的时候
		RemoteViews view = new RemoteViews(context.getPackageName(),
				R.layout.widget_sos);
		Intent intent = new Intent(context, OneKeySoSActivity.class);
		intent.putExtra(OneKeySoSActivity.EXTRA_EVENT_TYPE,
				OneKeySoSActivity.EXTRA_EVENT_DATA_AUTO);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		view.setOnClickPendingIntent(R.id.ll_widget_sos, pendingIntent);

		// 更新Widget
		ComponentName provider = new ComponentName(context,
				DesktopSOSWidgetProvider.class);
		appWidgetManager.updateAppWidget(provider, view);

		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
}
