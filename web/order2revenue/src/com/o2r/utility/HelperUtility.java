package com.o2r.utility;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import com.o2r.bean.SellerBean;

public class HelperUtility {
	public static void main(String arg[])
	{
		SellerBean seller=new SellerBean();
		SellerBean newObj=new SellerBean();
		java.util.List<com.o2r.model.PaymentUpload> abc = new ArrayList();
	System.out.println((abc instanceof Collection));
		seller.setName("Fromname");
		seller.setAddress("fromaddress");
		seller.setId(2);
		//convertor(seller,newObj,SellerBean.class);
		System.out.println(" newObj : "+newObj.toString());
		
	}
	public  static void convertor(Object fromObject,Object toObject, Class className , ArrayList<String> arrayList)
	{
		PropertyDescriptor pd;
		
		try {
			
			 for (Field f : className.getDeclaredFields()) {
				 pd = new PropertyDescriptor(f.getName(), className);
				/* System.out.println(" CanonicalName:  "+(f.getType().getCanonicalName())+
						 " Class "+(f.getType().getClass()+" NAMe : "+f.getType().getName().contains("List")));
					*/
				 if(!(f.getType().getName().contains("List"))&&(arrayList==null||!arrayList.contains(f.getName())))
				 {
					 //System.out.println(" Filed "+f.getType()+" gerneric "+f.getGenericType());
						 Method getter = pd.getReadMethod();
					Method setter = pd.getWriteMethod();
					setter.invoke(toObject,getter.invoke(fromObject));
					
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
		
	}

}
