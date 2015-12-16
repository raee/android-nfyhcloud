package com.yixin.nfyh.cloud.activity;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.adapter.AdapterViewHelper;
import com.yixin.nfyh.cloud.adapter.DoctorListAdapter;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.w.IUserDoctorListener;
import com.yixin.nfyh.cloud.w.NfyhWebserviceFactory;

/**
 * 医生列表
 * 
 * @author ChenRui
 * 
 */
public class DoctorListActivity extends FragmentActivity implements IUserDoctorListener, OnClickListener
{
	private ListView					mListView;
	private DoctorListAdapter			mListAdapter;
	private AdapterViewHelper			mAdapterViewHelper;
	
	private View						mDoctorListView;
	private ConversationListFragment	mChatListView;
	private Button						mBtnChatList;
	private Button						mBtnDoctorList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_list);
		mAdapterViewHelper = new AdapterViewHelper(this);
		
		mListView = (ListView) findViewById(android.R.id.list);
		mListAdapter = new DoctorListAdapter(this, null);
		mListView.setAdapter(mListAdapter);
		mListView.setOnItemClickListener(mListAdapter);
		
		mDoctorListView = findViewById(R.id.rl_doctor_list);
		mChatListView = (ConversationListFragment) getSupportFragmentManager().findFragmentById(R.id.conversationlist);
		
		mBtnChatList = (Button) findViewById(R.id.btn_doctor_chat_list);
		mBtnChatList.setOnClickListener(this);
		mBtnDoctorList = (Button) findViewById(R.id.btn_doctor_list);
		mBtnDoctorList.setOnClickListener(this);
		
		initData();
		
		showChatList();
	}
	
	private void initData()
	{
		mAdapterViewHelper.showLoaddingView();
		NfyhWebserviceFactory.getUserDoctorApi(this).getUserGroupDoctor(getNfyhApplication().getCurrentUser().getUid(), this);
	}
	
	private NfyhApplication getNfyhApplication()
	{
		return (NfyhApplication) getApplication();
	}
	
	@Override
	public void onGetUserDoctorSuccess(List<Users> doctors)
	{
		mAdapterViewHelper.dismissLoaddingView();
		mListAdapter.setData(doctors);
		mListAdapter.notifyDataSetChanged();
		
	}
	
	@Override
	public void onGetUserDoctorError(int code, String msg)
	{
		mAdapterViewHelper.dismissLoaddingView();
		mAdapterViewHelper.showEmptyView(msg);
	}
	
	@Override
	public void onNotDoctor()
	{
		mAdapterViewHelper.dismissLoaddingView();
		mAdapterViewHelper.showEmptyView("您还没有任何的医生哦。");
		
	}
	
	private void showChatList()
	{
		// #UI 
		mBtnChatList.setBackgroundResource(R.drawable.tab_single_bg);
		mBtnChatList.setTextColor(getResources().getColor(R.color.tab_selected));
		mBtnDoctorList.setBackgroundResource(R.drawable.list_bottom_bg);
		mBtnDoctorList.setTextColor(getResources().getColor(R.color.gray));
		// #END UI
		
		mChatListView.getView().setVisibility(View.VISIBLE);
		mDoctorListView.setVisibility(View.GONE);
		
		enterFragment();
	}
	
	private void showDoctorList()
	{
		// #UI 
		mBtnDoctorList.setBackgroundResource(R.drawable.tab_single_bg);
		mBtnDoctorList.setTextColor(getResources().getColor(R.color.tab_selected));
		
		mBtnChatList.setBackgroundResource(R.drawable.list_bottom_bg);
		mBtnChatList.setTextColor(getResources().getColor(R.color.gray));
		
		// #END UI
		
		mChatListView.getView().setVisibility(View.GONE);
		mDoctorListView.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 加载 会话列表 ConversationListFragment
	 */
	private void enterFragment()
	{
		
		Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon().appendPath("conversationlist")
				.appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
				.appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
				.appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
				.appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
				.build();
		
		mChatListView.setUri(uri);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btn_doctor_chat_list:
				
				showChatList();
				break;
			case R.id.btn_doctor_list:
				showDoctorList();
				break;
			
			default:
				break;
		}
	}
}
