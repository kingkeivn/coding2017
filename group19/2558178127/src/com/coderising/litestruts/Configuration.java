package com.coderising.litestruts;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class Configuration {

	Map<String,ActionConfig> actionMap = new HashMap<String,ActionConfig>();
	
	public Configuration(String fileName){
		String packagename = this.getClass().getPackage().getName();
		packagename = packagename.replace(".", "/");
		InputStream is = this.getClass().getResourceAsStream("/"+packagename+"/"+fileName);
		parseXml(is);
	}

	/**
	 * 使用JDOM解析XML
	 * @param ins
	 */
	private void parseXml(InputStream ins){
		SAXBuilder reader = new SAXBuilder();
		try {
			Document root = reader.build(ins);
			Element ele = root.getRootElement();
			for(Element e:ele.getChildren("action")){
				String actionName =e.getAttributeValue("name");
				String claName = e.getAttributeValue("class");
				ActionConfig acf = new ActionConfig(actionName,claName);
				for(Element ec:e.getChildren("result")){
					String resultName = ec.getAttributeValue("name");
					String viewResult = ec.getTextTrim();
					acf.addViewResult(resultName, viewResult);
				}
				this.actionMap.put(actionName, acf);
			}
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取action类全称
	 * @param action
	 * @return
	 */
	public String getClassName(String action){
		if("".equals(action) || action == null){
			return null;
		}
		ActionConfig ac = this.actionMap.get(action);
		return ac.getClazName();
	}
	
	/**
	 * 获取JSP返回结果
	 * @param action
	 * @param viewResu
	 * @return
	 */
	public String getResultView(String action,String viewResu){
		if("".equals(action) || action == null || "".equals(viewResu) || viewResu == null){
			return null;
		}
		ActionConfig ac = this.actionMap.get(action);
		return ac.getViewResult(viewResu);
	}
	
	private class ActionConfig{
		private String name;
		private String clazName;
		private Map<String,String> viewResult = new HashMap<String, String>();
		public String getClazName() {
			return clazName;
		}
		public String getViewResult(String resultName) {
			return viewResult.get(resultName);
		}
		public void addViewResult(String name,String result) {
			viewResult.put(name,result);
		}
		
		public ActionConfig(String name, String clazName) {
			this.name = name;
			this.clazName = clazName;
		}
	}
}
