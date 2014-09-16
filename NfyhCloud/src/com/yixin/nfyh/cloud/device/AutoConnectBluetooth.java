//package com.yixin.nfyh.cloud.device;
//
//import java.lang.reflect.Method;
//
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//
///**
// * 蓝牙自动配对
// * 
// * @author MrChenrui
// * 
// */
//public class AutoConnectBluetooth
//{
//	BluetoothDevice	device;
//
//	public AutoConnectBluetooth()
//	{
//
//		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
//
//		if (adapter != null)
//		{
//			if (!adapter.isEnabled())
//			{
//				adapter.enable();// 强制开启蓝牙
//			}
//		}
//
//		adapter.startDiscovery(); //
//
//	}
//
//	static public BluetoothDevice connect(BluetoothDevice device, String strPin)
//	{
//
//		if (device.getBondState() != BluetoothDevice.BOND_BONDED)
//		{// 判断给定地址下的device是否已经配对
//			try
//			{
//				AutoConnectBluetooth
//						.autoBond(device.getClass(), device, strPin);// 设置pin值
//				AutoConnectBluetooth.createBond(device.getClass(), device);
//			}
//			catch (Exception e)
//			{
//				// TODO: handle exception
//				System.out.println("配对不成功");
//			}
//		}
//		return device;
//	}
//
//	/**
//	 * 自动配对设置Pin值
//	 * 
//	 * @param btClass
//	 * @param device
//	 * @param strPin
//	 * @return
//	 * @throws Exception
//	 */
//	static public boolean autoBond(Class<?> btClass, BluetoothDevice device,
//			String strPin) throws Exception
//	{
//		Method autoBondMethod = btClass.getMethod("setPin",
//				new Class[] { byte[].class });
//		Boolean result = (Boolean) autoBondMethod.invoke(device,
//				new Object[] { strPin.getBytes() });
//		return result;
//	}
//
//	/**
//	 * 开始配对
//	 * 
//	 * @param btClass
//	 * @param device
//	 * @return
//	 * @throws Exception
//	 */
//	static public boolean createBond(Class<?> btClass, BluetoothDevice device)
//			throws Exception
//	{
//		Method createBondMethod = btClass.getMethod("createBond");
//		Boolean returnValue = (Boolean) createBondMethod.invoke(device);
//		return returnValue.booleanValue();
//	}
//}
