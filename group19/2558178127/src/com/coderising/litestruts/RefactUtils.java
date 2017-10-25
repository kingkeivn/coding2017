package com.coderising.litestruts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefactUtils {

	/**
	 * 获取目标类中方法
	 * @param clz
	 * @param startWithName
	 * @return
	 */
    private static List<Method> getMethods(Class<?> clz,String startWithName){
    	List<Method> methods = new ArrayList();
    	for(Method m:clz.getDeclaredMethods()){
    		if(m.getName().startsWith(startWithName)){
    			methods.add(m);
    		}
    	}
    	return methods;
    }
    
    public static List<Method> getSetterMethods(Class<?> clz){
    	return getMethods(clz,"set");
    }
    
    /**
     * 给action中的属性赋值
     * @param o
     * @param paraMap
     */
    public static void setParameters(Object o,Map<String,String> paraMap){
    	List<Method> methods = getSetterMethods(o.getClass());
    	for(String name:paraMap.keySet()){
    		String methodName = "set"+name;
    		for(Method m:methods){
    			if(m.getName().equalsIgnoreCase(methodName)){
    				try {
						m.invoke(o,paraMap.get(name));
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
    			}
    		}
    	}
    	
    }
    
    public static List<Method> getGetterMethods(Class<?> clz){
    	return getMethods(clz,"get");
    }
    
    /**
     * 获取参数值
     * @param o
     * @return
     */
    public static Map<String,Object> getParametersMap(Object o){
    
    	Map params = new HashMap();
    	List<Method> methods = getGetterMethods(o.getClass());
    	for(Method m:methods){
    		String methodName = m.getName();
    		String name = methodName.replaceFirst("get", "").toLowerCase();
    		Object value;
			try {
				value = m.invoke(o);
	    		params.put(name, value);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}

    	}
    	
    	return params;
    }
}
