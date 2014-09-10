///**
// * 
// */
//package com.yixin.nfyh.cloud.adapter;
//
//import android.content.Context;
//import cn.rui.framework.ui.RuiDialog;
//
///**
// * 对话框适配器
// * 
// * @author MrChenrui
// * 
// */
//public class DialogAdapter
//{
//	
//	
//	private int				type			= 0;
//	
//	private Context			context;
//	private IDialogAdapter	dialogAdapter;
//	
//	public interface IDialogAdapterLisenter
//	{
//		void onSelected(DialogAdapter adapter, int index, Object resultData);
//	}
//	
//	/**
//	 * @param context
//	 * @param type
//	 *            类型，参考静态变量
//	 */
//	public DialogAdapter(Context context, int type, IDialogAdapter dialogAdapter)
//	{
//		this.context = context;
//		this.type = type;
//		this.dialogAdapter = dialogAdapter;
//	}
//	
//	public void show()
//	{
//		
//	}
//	
//	/**
//	 * 
//	 */
//	private void builderSelector()
//	{
//		// TODO Auto-generated method stub
//		
//	}
//	
//	/**
//	 * 
//	 */
//	private void builderNumber()
//	{
//		// TODO Auto-generated method stub
//		
//	}
//	
//	/**
//	 * 
//	 */
//	private void builderCustomer()
//	{
//		// TODO Auto-generated method stub
//		
//	}
//	
//	/**
//	 * 
//	 */
//	private void builderDate()
//	{
//		// TODO Auto-generated method stub
//		
//	}
//	
//	/**
//	 * 
//	 */
//	private void builderText()
//	{
//		RuiDialog dialog = new RuiDialog(context);
//		dialog.setTitle(dialogAdapter.getTitle());
//		dialog.setMessage(dialogAdapter.getMessage());
//		
//		// 设置按钮
//		dialog.setRightButton(dialogAdapter.getConfirmText(),
//				dialogAdapter.getConfirmClickListener());
//		dialog.setLeftButton(dialogAdapter.getCancleText(),
//				dialogAdapter.getCancleClickListener());
//		dialog.show();
//	}
//	
// }
