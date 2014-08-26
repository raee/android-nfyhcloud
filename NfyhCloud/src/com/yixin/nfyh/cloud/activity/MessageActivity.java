package com.yixin.nfyh.cloud.activity;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.adapter.ListViewAdapter;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.i.IPushMessage;
import com.yixin.nfyh.cloud.model.Messages;
import com.yixin.nfyh.cloud.model.view.MessageManager;

public class MessageActivity extends BaseActivity
{
	private IPushMessage			mDbPushMessage;
	private ListView				mListView;
	private MessageListAdatepter	mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		mDbPushMessage = NfyhCloudDataFactory.getFactory(this).getPushMessage();
		mListView = (ListView) findViewById(android.R.id.list);
		List<Messages> dataList = mDbPushMessage.getMessage(false);
		mAdapter = new MessageListAdatepter(this, dataList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(mAdapter);
	}

	private class MessageListAdatepter extends ListViewAdapter<Messages>
			implements OnItemClickListener
	{

		public MessageListAdatepter(Context context, List<Messages> dataList)
		{
			super(context, dataList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = null;
			Messages model = getDataItem(position);
			if (convertView == null)
			{
				convertView = LayoutInflater.from(MessageActivity.this)
						.inflate(R.layout.item_message_list, null);
				holder = new ViewHolder();
				holder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_message_title);
				holder.tvSummary = (TextView) convertView
						.findViewById(R.id.tv_message_summary);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tvTitle.setText(model.getTitle());
			holder.tvSummary.setText(model.getSummary());
			holder.message = model;

			return convertView;
		}

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long arg3)
		{
			Object tag = view.getTag();
			if (tag instanceof ViewHolder)
			{
				ViewHolder holder = (ViewHolder) tag;
				startActivity(MessageManager.getIntent(MessageActivity.this,
						holder.message));
			}
		}

	}

	@Override
	protected String getActivityName()
	{
		return getString(R.string.activity_message);
	}

	class ViewHolder
	{
		public TextView	tvTitle;
		public TextView	tvSummary;
		public Messages	message;
	}
}
