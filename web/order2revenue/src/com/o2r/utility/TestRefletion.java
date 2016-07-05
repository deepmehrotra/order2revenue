package com.o2r.utility;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import com.o2r.bean.SellerBean;

public class TestRefletion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SellerBean seller=new SellerBean();
		SellerBean seller2=new SellerBean();
		seller.setName("TestNAme");
		seller.setEmail("email");
		seller.setAddress("address");
		/*Set<Method> getters = ReflectionUtils.getAllMethods(someClass,
			      ReflectionUtils.withModifier(Modifier.PUBLIC), ReflectionUtils.withPrefix("get"));*/
		PropertyDescriptor pd;
		try {
			
			 for (Field f : SellerBean.class.getDeclaredFields()) {
				 pd = new PropertyDescriptor(f.getName(), SellerBean.class);
				 if(!pd.getPropertyType().isInstance(Collection.class))
				 {
				 Method getter = pd.getReadMethod();
					Method setter = pd.getWriteMethod();
					
					Object z = getter.invoke(seller);
					Object y = setter.invoke(seller2,getter.invoke(seller));
					
				 }
			 }
			 System.out.println("Seller 1  : "+seller);
				System.out.println("seller2 : "+seller2);
			
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
	/**
	 * Find a getter method for the specified property. A getter is defined as a method whose name start with the prefix
	 * 'get' and the rest of the name is the same as the property name (with the first character uppercased).
	 */
	/*protected Method findGetterForProperty(String propertyName, Class<?> clazz, boolean mustBeStatic) {
		Method[] ms = clazz.getMethods();
		// Try "get*" method...
		String getterName = "get" + StringUtils.capitalize(propertyName);
		for (Method method : ms) {
			if (method.getName().equals(getterName) && method.getParameterTypes().length == 0 &&
					(!mustBeStatic || Modifier.isStatic(method.getModifiers()))) {
				return method;
			}
		}
		// Try "is*" method...
		getterName = "is" + StringUtils.capitalize(propertyName);
		for (Method method : ms) {
			if (method.getName().equals(getterName) && method.getParameterTypes().length == 0 &&
					boolean.class.equals(method.getReturnType()) &&
					(!mustBeStatic || Modifier.isStatic(method.getModifiers()))) {
				return method;
			}
		}
		return null;
	}

	*//**
	 * Find a setter method for the specified property.
	 *//*
	protected Method findSetterForProperty(String propertyName, Class<?> clazz, boolean mustBeStatic) {
		Method[] methods = clazz.getMethods();
		String setterName = "set" + StringUtils.capitalize(propertyName);
		for (Method method : methods) {
			if (method.getName().equals(setterName) && method.getParameterTypes().length == 1 &&
					(!mustBeStatic || Modifier.isStatic(method.getModifiers()))) {
				return method;
			}
		}
		return null;
	}*/
}
