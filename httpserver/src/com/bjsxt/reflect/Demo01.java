package com.bjsxt.reflect;

public class Demo01 {

	public static void main(String[] args) throws ClassNotFoundException {
		String str = "abc";
		//Class对象
		//对象.getClass
		Class<?> clz = str.getClass();
		//类.class
		clz = String.class;
		clz=Class.forName("java.lang.String");
	}

}
