package com.tamgitsun.object;

public class WordAndDomain {
	
	final public static int ANY=0;
	final public static int EXIST=1;
	
	private String word;
	private Domain domain;
	private int way;
	
	public void setWord(String word){
		this.word=word;
	}
	public void setDomain(Domain domain){
		this.domain=domain;
	}
	public void setWay(int way){
		this.way=way;
	}
	public String getWord(){
		return word;
	}
	public Domain getDomain(){
		return domain;
	}
	public int getWay(){
		return way;
	}

}
