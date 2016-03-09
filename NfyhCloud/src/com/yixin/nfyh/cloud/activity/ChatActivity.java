package com.yixin.nfyh.cloud.activity;

import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.ConversationBehaviorListener;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;

import com.yixin.nfyh.cloud.R;

/**
 * 融云聊天界面
 * 
 * @author ChenRui
 * 
 */
public class ChatActivity extends FragmentActivity implements
		ConversationBehaviorListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayShowHomeEnabled(false); // 不显示LOGO
		setContentView(R.layout.raeim_conversation);

		Uri uri = getIntent().getData();
		if (uri != null && uri.getQueryParameter("title") != null) {
			setTitle("与" + uri.getQueryParameter("title") + "对话");
		}

		getActionBar().setDisplayHomeAsUpEnabled(true);

		RongIM.setConversationBehaviorListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onMessageClick(Context context, View arg1, Message message) {
		MessageContent content = message.getContent();
		if (content instanceof ImageMessage) {
			Intent intent = new Intent(this, PhotoPreviewActivity.class);
			ArrayList<String> data = new ArrayList<String>();

			Uri uri = ((ImageMessage) content).getLocalUri();
			if (uri == null) {
				uri = ((ImageMessage) content).getRemoteUri();
			}
			if (uri == null)
				return false;

			data.add(uri.toString());
			intent.putExtra("type", 1);

			intent.putStringArrayListExtra("data", data);

			startActivity(intent);
			return true;
		}

		return false;
	}

	@Override
	public boolean onMessageLinkClick(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onMessageLongClick(Context arg0, View arg1, Message arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onUserPortraitClick(Context arg0, ConversationType arg1,
			UserInfo arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onUserPortraitLongClick(Context arg0, ConversationType arg1,
			UserInfo arg2) {
		// TODO Auto-generated method stub
		return false;
	}
}
