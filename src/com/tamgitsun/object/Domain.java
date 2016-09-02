package com.tamgitsun.object;

import java.util.ArrayList;
import java.util.List;

public class Domain {

	private static Object obj=new Object();
	private static Domain domain=null;
	
	private Object []elements;
	private int eleAmounts=0;
	
	private int pos=0;
	
	private Domain(){}
	
	public static Domain getInstance(){
		if(domain==null){
			synchronized(obj){
				if(domain==null){
					domain=new Domain();
				}
			}
		}
		return domain;
	}
	
	public void setDomain(Object []elements){
		eleAmounts=elements.length;
		this.elements=elements;
	}
	
	public Object[] getDomain(){
		return elements;
	}
	
	public int getDomainSize(){
		return eleAmounts;
	}
	
	public boolean isInDomain(Object element){
		for(Object ele:elements){
			if(ele.equals(element)){
				return true;
			}
		}
		return false;
	}
	
	public Object getNext(){
		pos++;
		return elements[pos-1];
	}
	
	/**
	 * 从0开始
	 * @param element
	 * @return
	 */
	public int getIndex(Object element){
		int index=-1;
		for(int i=0;i<elements.length;i++){
			if(elements[i].equals(element)){
				index=i;
				break;
			}
		}
		return index;
	}
	
}
