package com.yixin.nfyh.cloud.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.rui.framework.ui.RuiDialog;

import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.adapter.ListViewAdapter;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.i.IPushMessage;
import com.yixin.nfyh.cloud.model.Messages;
import com.yixin.nfyh.cloud.model.view.MessageManager;

public class MessageActivity extends BaseActivity {
	private IPushMessage mDbPushMessage;
	private ListView mListView;
	private MessageListAdatepter mAdapter;
	private ArrayList<Messages> mDataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		mDbPushMessage = NfyhCloudDataFactory.getFactory(this).getPushMessage();
		mListView = (ListView) findViewById(android.R.id.list);
		mDataList = mDbPushMessage.getMessage(false);
		mAdapter = new MessageListAdatepter(this, mDataList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(mAdapter);
		mListView.setOnItemLongClickListener(mAdapter);
	}

	private class MessageListAdatepter extends ListViewAdapter<Messages>
			implements OnItemClickListener, OnItemLongClickListener {

		public MessageListAdatepter(Context context, List<Messages> dataList) {
			super(context, dataList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			Messages model = getDataItem(position);
			if (convertView == null) {
				convertView = LayoutInflater.from(MessageActivity.this)
						.inflate(R.layout.item_message_list, null);
				holder = new ViewHolder();
				holder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_message_title);
				holder.tvSummary = (TextView) convertView
						.findViewById(R.id.tv_message_summary);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tvTitle.setText(model.getTitle());
			holder.tvSummary.setText(model.getSummary());
			holder.message = model;

			return convertView;
		}

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long arg3) {
			Object tag = view.getTag();
			if (tag instanceof ViewHolder) {
				ViewHolder holder = (ViewHolder) tag;
				startActivity(MessageManager.getStartIntent(
						MessageActivity.this, holder.message));
			}
		}

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				final int position, long id) {
			new RuiDialog.Builder(MessageActivity.this)
					.buildTitle("删除消息")
					.buildMessage("是否删除该消息，点击窗口外面返回。")
					.buildLeftButton("删除所有",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 删除所有
									for (int i = 0; i < mDataList.size(); i++) {
										Messages model = mDataList.get(i);
										mDbPushMessage.delete(model); // 从数据库删除
										//mDataList.remove(i);
									}
									mDataList.clear();
									mAdapter.notifyDataSetChanged();
									mListView.invalidate();
									dialog.dismiss();
								}
							})
					.buildRight("删除", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 删除一条
							Messages model = mDataList.get(position);
							mDbPushMessage.delete(model);
							mDataList.remove(model);
							mAdapter.notifyDataSetChanged();
							mListView.invalidate();
							dialog.dismiss();
						}
					}).show();
			return true;
		}

	}

	@Override
	protected String getActivityName() {
		return getString(R.string.activity_message);
	}

	class ViewHolder {
		public TextView tvTitle;
		public TextView tvSummary;
		public Messages message;
	}
}
