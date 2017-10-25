package com.coderising.litestruts;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import com.coderising.litestruts.xml.read.XmlService;



public class Struts {

	private final static Configuration cfg = new Configuration("struts.xml");
	
    public static View runAction(String actionName, Map<String,String> parameters) throws Exception{

        /*
         
		0. 读取配置文件struts.xml
 		
 		1. 根据actionName找到相对应的class ， 例如LoginAction,   通过反射实例化（创建对象）
		据parameters中的数据，调用对象的setter方法， 例如parameters中的数据是 
		("name"="test" ,  "password"="1234") ,     	
		那就应该调用 setName和setPassword方法
		
		2. 通过反射调用对象的exectue 方法， 并获得返回值，例如"success"
		
		3. 通过反射找到对象的所有getter方法（例如 getMessage）,  
		通过反射来调用， 把值和属性形成一个HashMap , 例如 {"message":  "登录成功"} ,  
		放到View对象的parameters
		
		4. 根据struts.xml中的 <result> 配置,以及execute的返回值，  确定哪一个jsp，  
		放到View对象的jsp字段中。
        
        */
    	
    	new XmlService().readDocument();  //读取xml。把xml信息存入map
    	
    	String clsName = cfg.getClassName(actionName);//获取action对应class对象
    	
    	if(clsName == null){
    		return null;
    	}
    	Class c1 = Class.forName(clsName);
    	Object action = c1.newInstance();
    	RefactUtils.setParameters(action, parameters);
		
		
		Method mt=c1.getMethod("execute");//调用执行方法
		String result = (String)mt.invoke(action);
		Map<String,Object> paraMap = RefactUtils.getParametersMap(action);
		String resultView = cfg.getResultView(actionName, result);
		View view = new View();
		view.setJsp(resultView);
		view.setParameters(paraMap);
    	return view;
    }   
    /**
     * 读取Map中xml信息
     * @param name
     * @param attr
     * @return
     */
    public static String readxml(String name,String attr){
    	
    	Map xmlMap = XmlService.map;

    	return (String) xmlMap.get(name+attr);
    }
    

}
