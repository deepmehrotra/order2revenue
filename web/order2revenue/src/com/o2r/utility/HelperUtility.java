package com.o2r.utility;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import com.o2r.bean.SellerBean;

public class HelperUtility {
	public static Object convertor(Object fromObject, Class className)
	{
		PropertyDescriptor pd;
		Object toObject=new Object();
		
		try {
			
			 for (Field f : SellerBean.class.getDeclaredFields()) {
				 pd = new PropertyDescriptor(f.getName(), className.getClass());
				 if(!pd.getPropertyType().isInstance(Collection.class))
				 {
				 Method getter = pd.getReadMethod();
					Method setter = pd.getWriteMethod();
					
					Object y = setter.invoke(toObject,getter.invoke(fromObject));
					
				 }
			 }
			
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return toObject;
	}

}
