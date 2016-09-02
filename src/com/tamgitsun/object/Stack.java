package com.tamgitsun.object;

import java.util.ArrayList;
import java.util.List;

public class Stack {

	private List<Object> contents=new ArrayList<Object>();
	private int pos=-1;//下标,-1为没有元素
	
	public void push(Object content){
		contents.add(content);
		pos++;
	}
	
	/**
	 * @return
	 * if empty stack, return null, 
	 * else return the peak Object
	 */
	public Object pop(){
		if(pos<0){
			return null;
		}else{
			pos--;
			return contents.get(pos+1);
		}
	}
	
	public void clear(){
		contents.clear();
		pos=-1;
	}
	
	public int size(){
		return contents.size();
	}
	
	public Object[] toArray(){
		return contents.toArray();
	}
	
}
