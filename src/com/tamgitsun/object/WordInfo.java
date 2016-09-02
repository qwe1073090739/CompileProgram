package com.tamgitsun.object;

public class WordInfo {

	/*
	 *lowercase -> [a-z]
	 *capital -> [A-Z]
	 *digit -> [0-9]
	 *variable -> lowercase(lowercase|digit)*
	 *predicate -> capital(lowercase|digit)*
	 *op_leftbacket -> (
	 *op_rightbacket ->)
	 *op_negation -> ~
	 *op_compare -> <|>|<=|>=|==|!=
	 *op_math ->　+|-|*|/
	 *op_quantifer -> $|#
	 *op_connectives1 -> &||
	 *op_connectives2 -> ->|<-> 
	 */
	
	public final static int VARIABLE=1;
	public final static int PREDICATE=2;
	public final static int DIGITS=3;
	public final static int OP_LEFTBACKET=4;
	public final static int OP_RIGHTBACKET=5;
	public final static int OP_NEGATION=6;
	public final static int OP_COMPARE=7;
	public final static int OP_MATH=8;
	public final static int OP_QUANTIFER=9;
	public final static int OP_CONNECTIVES1=10;
	public final static int OP_CONNECTIVES2=11;
	public final static int OP_COMMAS=12;
	public final static int SPACE=13;
	public final static int WRONG=14;
	public final static int END=15;
	
	
	private int token;
	private String word;
	
	public int getToken(){
		return token;
	}
	public String getWord(){
		if(word==null){
			word="";
		}
		return word;
	}
	public void setToken(int token){
		this.token=token;
	}
	public void setWord(String word){
		this.word=word;
	}
	
}
