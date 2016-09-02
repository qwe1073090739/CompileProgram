package com.tamgitsun.words;

import com.tamgitsun.object.WordInfo;

public class Token {

	private Token(){}
	
	private final int START=0;
	private final int GO=100;
	private static Token token=null;
	private String paragraph;
	private int pos=0;
	private char[] paraBuf;
	private static Object obj=new Object();
	
	public static Token getInstance(){
		if(token==null){
			synchronized(obj){
				if(token==null){
					token=new Token();
				}
			}
		}
		return token;
	}
	
	public void setParagraph(String paragraph){
		this.pos=0;
		this.paragraph=paragraph;
		paraBuf=this.paragraph.toCharArray();
	}
	
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
	
	public WordInfo getToken(){
		int status=START;
		String tempWord="";
		int tempToken=START;
		WordInfo wordInfo=new WordInfo();
		if(pos>=paragraph.length()){
			wordInfo.setToken(WordInfo.END);
			return wordInfo;
		}
		while(true){
			if(pos>=paragraph.length()){
				wordInfo.setToken(tempToken);
				wordInfo.setWord(tempWord);
				return wordInfo;
			}
			switch(status){
			case START:
				if(paraBuf[pos]>='a'&&paraBuf[pos]<='z'){//variable
					tempWord+=getChar(paraBuf[pos]);
					tempToken=WordInfo.VARIABLE;
					status=GO;
				}else if(paraBuf[pos]>='A'&&paraBuf[pos]<='Z'){//predicate
					tempWord+=getChar(paraBuf[pos]);
					tempToken=WordInfo.PREDICATE;
					status=GO;
				}else if(paraBuf[pos]>='0'&&paraBuf[pos]<='9'){
					tempWord+=getChar(paraBuf[pos]);
					tempToken=WordInfo.DIGITS;
					status=GO;
				}else if(paraBuf[pos]=='('){
					wordInfo.setToken(WordInfo.OP_LEFTBACKET);
					wordInfo.setWord("(");
					pos++;
					return wordInfo;
				}else if(paraBuf[pos]==')'){
					wordInfo.setToken(WordInfo.OP_RIGHTBACKET);
					wordInfo.setWord(")");
					pos++;
					return wordInfo;
				}else if(paraBuf[pos]=='~'){
					wordInfo.setToken(WordInfo.OP_NEGATION);
					wordInfo.setWord("~");
					pos++;
					return wordInfo;
				}else if(paraBuf[pos]=='<'||paraBuf[pos]=='>'||paraBuf[pos]=='='||paraBuf[pos]=='!'){
					if(paraBuf[pos+1]!='-'){
						tempWord+=getChar(paraBuf[pos]);
						tempToken=WordInfo.OP_COMPARE;
						status=GO;
					}else{
						tempWord+=getChar(paraBuf[pos]);
						tempToken=WordInfo.OP_CONNECTIVES2;
						status=GO;
					}
				}else if(paraBuf[pos]=='+'||paraBuf[pos]=='-'||paraBuf[pos]=='*'||paraBuf[pos]=='/'){
					if(paraBuf[pos+1]!='>'){
						wordInfo.setToken(WordInfo.OP_MATH);
						wordInfo.setWord(getChar(paraBuf[pos])+"");
						pos++;
						return wordInfo;
					}else{
						tempWord+=getChar(paraBuf[pos]);
						tempToken=WordInfo.OP_CONNECTIVES2;
						status=GO;
					}
				}else if(paraBuf[pos]=='$'||paraBuf[pos]=='#'){
					wordInfo.setToken(WordInfo.OP_QUANTIFER);
					wordInfo.setWord(getChar(paraBuf[pos])+"");
					pos++;
					return wordInfo;
				}else if(paraBuf[pos]=='&'||paraBuf[pos]=='|'){
					wordInfo.setToken(WordInfo.OP_CONNECTIVES1);
					wordInfo.setWord(getChar(paraBuf[pos])+"");
					pos++;
					return wordInfo;
				}else if(paraBuf[pos]==','){
					wordInfo.setToken(WordInfo.OP_COMMAS);
					wordInfo.setWord(",");
					pos++;
					return wordInfo;
				}else if(paraBuf[pos]==' '){
					wordInfo.setToken(WordInfo.SPACE);
					wordInfo.setWord(" ");
					pos++;
					return wordInfo;
				}else{
					wordInfo.setToken(WordInfo.WRONG);
					return wordInfo;
				}
				break;
			case GO:
				switch(tempToken){
				case WordInfo.VARIABLE:
					if((paraBuf[pos]>='a'&&paraBuf[pos]<='z')||(paraBuf[pos]>='0'&&paraBuf[pos]<='9')){
						tempWord+=getChar(paraBuf[pos]);
					}else{
						wordInfo.setToken(tempToken);
						wordInfo.setWord(tempWord);
						return wordInfo;
					}
					break;
				case WordInfo.PREDICATE:
					if((paraBuf[pos]>='a'&&paraBuf[pos]<='z')||(paraBuf[pos]>='0'&&paraBuf[pos]<='9')){
						tempWord+=getChar(paraBuf[pos]);
					}else{
						wordInfo.setToken(tempToken);
						wordInfo.setWord(tempWord);
						return wordInfo;
					}
					break;
				case WordInfo.DIGITS:
					if(paraBuf[pos]>='0'&&paraBuf[pos]<='9'){
						tempWord+=getChar(paraBuf[pos]);
					}else{
						wordInfo.setToken(tempToken);
						wordInfo.setWord(tempWord);
						return wordInfo;
					}
					break;
				case WordInfo.OP_COMPARE:
					if(tempWord.equals("<")){
						if(paraBuf[pos]=='='){
							tempWord+=getChar(paraBuf[pos]);
							pos++;
						}
						wordInfo.setToken(tempToken);
						wordInfo.setWord(tempWord);
						return wordInfo;
					}else if(tempWord.equals(">")){
						if(paraBuf[pos]=='='){
							tempWord+=getChar(paraBuf[pos]);
							pos++;
						}
						wordInfo.setToken(tempToken);
						wordInfo.setWord(tempWord);
						return wordInfo;
					}else if(tempWord.equals("=")){
						if(paraBuf[pos]=='='){
							tempWord+=getChar(paraBuf[pos]);
							pos++;
							wordInfo.setToken(tempToken);
							wordInfo.setWord(tempWord);
							return wordInfo;
						}else{
							wordInfo.setToken(WordInfo.WRONG);
							return wordInfo;
						}
					}else if(tempWord.equals("!")){
						if(paraBuf[pos]=='='){
							tempWord+=getChar(paraBuf[pos]);
							pos++;
							wordInfo.setToken(tempToken);
							wordInfo.setWord(tempWord);
							return wordInfo;
						}else{
							wordInfo.setToken(WordInfo.WRONG);
							return wordInfo;
						}
					}
					break;
				case WordInfo.OP_CONNECTIVES2:
					if(tempWord.equals("-")||tempWord.equals("<-")){
						if(paraBuf[pos]=='>'){
							tempWord+=getChar(paraBuf[pos]);
							pos++;
							wordInfo.setToken(tempToken);
							wordInfo.setWord(tempWord);
							return wordInfo;
						}else{
							wordInfo.setToken(WordInfo.WRONG);
							return wordInfo;
						}
					}else if(tempWord.equals("<")){
						if(paraBuf[pos]=='-'){
							tempWord+=getChar(paraBuf[pos]);
						}else{
							wordInfo.setToken(WordInfo.WRONG);
							return wordInfo;
						}
					}
					break;
				}
				break;
			}
			pos++;
		}
	}
	
	/*
	public WordInfo getToken(){
		int status=START;
		String tempWord="";
		int tempToken=START;
		WordInfo wordInfo=new WordInfo();
		if(pos>=paragraph.length()){
			wordInfo.setToken(WordInfo.END);
			return wordInfo;
		}
		while(true){
			if(pos>=paragraph.length()){
				wordInfo.setToken(tempToken);
				wordInfo.setWord(tempWord);
				return wordInfo;
			}
			switch(status){
			case START:
				if(paraBuf[pos]<='9'&&paraBuf[pos]>='0'){
					tempToken=WordInfo.DIGIT;
					tempWord+=getChar(paraBuf[pos]);
					status=GO;
				}else if((paraBuf[pos]>='a'&&paraBuf[pos]<='z')||(paraBuf[pos]>='A'&&paraBuf[pos]<='Z')){
					tempToken=WordInfo.LETTER;
					tempWord+=getChar(paraBuf[pos]);
					status=GO;
				}else if(paraBuf[pos]=='~'){
					wordInfo.setToken(WordInfo.LOGIC_OP0);
					wordInfo.setWord("~");
					pos++;
					return wordInfo;
				}else if((paraBuf[pos]=='&')||(paraBuf[pos]=='|')){
					wordInfo.setToken(WordInfo.LOGIC_OP1);
					wordInfo.setWord(""+getChar(paraBuf[pos]));
					pos++;
					return wordInfo;
				}else if((paraBuf[pos]=='$')||(paraBuf[pos]=='#')){
					wordInfo.setToken(WordInfo.LOGIC_OP2);
					wordInfo.setWord(""+getChar(paraBuf[pos]));
					pos++;
					return wordInfo;
				}else if(paraBuf[pos]=='-'||paraBuf[pos]=='<'){
					tempToken=WordInfo.LOGIC_OP3;
					tempWord+=getChar(paraBuf[pos]);
					status=GO;
				}else if(paraBuf[pos]==' '){
					status=START;
				}else{
					wordInfo.setToken(WordInfo.WRONG);
					return wordInfo;
				}
				break;
			case GO:
				switch(tempToken){
				case WordInfo.DIGIT:
					if(paraBuf[pos]>='0'&&paraBuf[pos]<='9'){
						tempWord+=getChar(paraBuf[pos]);
					}else{
						wordInfo.setToken(tempToken);
						wordInfo.setWord(tempWord);
						return wordInfo;
					}
					break;
				case WordInfo.IDEN:
					if((paraBuf[pos]>='0'&&paraBuf[pos]<='9')||
						(paraBuf[pos]>='a'&&paraBuf[pos]<='z')||
						(paraBuf[pos]>='A'&&paraBuf[pos]<='Z')){
						tempWord+=getChar(paraBuf[pos]);
					}else{
						wordInfo.setToken(tempToken);
						wordInfo.setWord(tempWord);
						return wordInfo;
					}
					break;
				case WordInfo.LETTER:
					if((paraBuf[pos]>='0'&&paraBuf[pos]<='9')||
							(paraBuf[pos]>='a'&&paraBuf[pos]<='z')||
							(paraBuf[pos]>='A'&&paraBuf[pos]<='Z')){
							tempWord+=getChar(paraBuf[pos]);
							tempToken=WordInfo.IDEN;
						}else{
							wordInfo.setToken(tempToken);
							wordInfo.setWord(tempWord);
							return wordInfo;
						}
					break;
				case WordInfo.LOGIC_OP3:
					if(paraBuf[pos]=='>'&&(tempWord.equals("-")||tempWord.equals("<-"))){
						tempWord+=getChar(paraBuf[pos]);
						wordInfo.setToken(tempToken);
						wordInfo.setWord(tempWord);
						pos++;
						return wordInfo;
					}else if(paraBuf[pos]=='-'&&tempWord.equals("<")){
						tempWord+=getChar(paraBuf[pos]);
					}else{
						wordInfo.setToken(WordInfo.WRONG);
						return wordInfo;
					}
					break;
				}
				break;
			}
			pos++;
		}
	}*/
	
	private char getChar(char c){
		return c;
	}
	
	public void returnLastToken(WordInfo wi){
		pos=pos-wi.getWord().length();
		if(pos<0){
			pos=0;
		}
	}
	
}
