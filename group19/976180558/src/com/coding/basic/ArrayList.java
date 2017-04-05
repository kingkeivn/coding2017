package com.coding.basic;

public class ArrayList implements List {
	
	private int size = 0;
	
	private Object[] elementData = new Object[100];
	
	public void add(Object o){
		size=size();
		elementData[size]=o;
		if (elementData.length<=size) {
			grow();//如果数组的长度小于等于size 则调用grow() 来增长size
		}
		size++;
	}
	
	private void grow(){
		size=size();
		int length=0;
		if (size<1000) {
			length=size*2;
		}else {
			length=(int) (size*1.5);
		}
		size=length;
		Object[] tempdata=new Object[size];
		for (int i = 0; i < tempdata.length; i++) {
			tempdata[i]=elementData[i];
		}
		elementData=tempdata;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.coding.basic.List#add(int, java.lang.Object)
	 * 在第index个元素之前插入元素
	 */
	public void add(int index, Object o){
		if (index>=size()) {
			return;
		}
		size=size();
		if (elementData.length<=size) {//这里还要再加个判断？？？
			grow();
		}
		for (int i = index; i < size+1; i++) {
			elementData[i]=elementData[i-1]; // By liu_qiancheng
		}
		elementData[index]=o;
		size=size+1;
	}
	
	public Object get(int index){
		return elementData[index];
	}
	
	public Object remove(int index){
		if (index>=size()) {
			return null;
		}else{
			Object o=elementData[index];
			for (int i = index; i < size(); i++) {
				elementData[i]=elementData[i+1];
			}
			elementData[size()]=null;
			size--;
			return o;
		}
	}
	
	public int size(){
		return size;
	}
	
	public Iterator iterator(){
		return null;
	}
	
}
