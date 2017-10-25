package com.coderising.litestruts.xml.read;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlService {

	private File file;

	public static Map map = new HashMap();

	/**
	 * 传入文档
	 */
	public XmlService() {
		file = new File(
				"E:/2017learn_1/coding2017/group19/2558178127/src/com/coderising/litestruts/struts.xml");
	}

	/**
	 * 获取文档
	 * @return
	 */
	public Document getDocument() {
		SAXReader reader = new SAXReader();

		Document document = null;

		try {
			document = reader.read(file);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return document;
	}

	// 读取xml
	public void readDocument() {

		List<Element> sonElementList = getDocument().getRootElement()
				.elements();

		Element root = getDocument().getRootElement();
		
		getElement(sonElementList);
	}

	private List list = new ArrayList();

	private void getElement(List<Element> sonElemetList) {
		for (Element sonElement : sonElemetList) {
			if (sonElement.elements().size() != 0) {
				getNodes(sonElement, list);
				getElement(sonElement.elements());
			} else {
				getNodes(sonElement, list);
			}

		}
	}

	public void getNodes(Element node, List pname) {
		// 当前节点的名称、文本内容和属性
		List<Attribute> listAttr = node.attributes();// 当前节点的所有属性的list
		String classValue = "";
		for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
			String name = attr.getName();// 属性名称
			String value = attr.getValue();// 属性的值
			if ("login".equals(value) || "logout".equals(value)) {
				pname.add(0, value);
			} else {
				map.put(pname.get(0) + value, node.getTextTrim());
			}
			if ("class".equals(name) && pname != null) {
				classValue = value;
				map.put(pname.get(0) + "class", value);
			}
		}
	}
}
