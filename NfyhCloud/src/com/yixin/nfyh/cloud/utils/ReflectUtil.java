/**
 * 
 */
package com.yixin.nfyh.cloud.utils;

import java.lang.reflect.Field;

/**
 * 反射帮助类
 * 
 * @author MrChenrui
 * 
 */
public class ReflectUtil
{
	/**
	 * 根据资源名字获取资源
	 * 
	 * @param type
	 *            类型
	 * @param name
	 *            名字
	 * @return 资源ID
	 */
	public static int getDrawableId(Class<?> type, String name)
	{
		Field field;
		try
		{
			if (name == null)
				return 0;
			field = type.getField(name);
			return field.getInt(field);
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return 0;

	}
}
