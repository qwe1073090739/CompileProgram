package com.tamgitsun.test;

import java.util.List;

import com.tamgitsun.analysis.Analysis;
import com.tamgitsun.object.WordInfo;
import com.tamgitsun.words.Token;

public class Test {
	
	public static void main(String args[]){
		System.out.println("the program start run");
		
		System.out.println("/*********show token*********/");
		Token token=Token.getInstance();
		token.setParagraph(args[0]);
		WordInfo wi=token.getToken();
		while(wi.getToken()!=WordInfo.END){
			if(wi.getToken()==WordInfo.WRONG){
				System.out.println("wrong!");
				break;
			}
			System.out.println(wi.getToken()+","+wi.getWord());
			wi=token.getToken();
		}
		
		System.out.println("/*********show sentences*********/");
		Analysis.getInstance().setSentences("p->a1&a2\r\nq->b1&b2");
		List<String> sentences=Analysis.getInstance().getSentences();
		if(sentences!=null){
			for(String s:sentences){
				System.out.println(s);
			}
		}else{
			System.out.println("sentences is null");
		}
		
		System.out.println("/*********show sentences*********/");
		Analysis.getInstance().setSentences(args);
		List<String> sentences1=Analysis.getInstance().getSentences();
		if(sentences1!=null){
			for(String s:sentences1){
				System.out.println(s);
			}
		}else{
			System.out.println("sentences is null");
		}
		
		System.out.println("/*********show analysis result*********/");
		Analysis.getInstance().setSentences(args);
		System.out.println(Analysis.getInstance().doAnalysis());

		System.out.println("the program end run");
		
	}

}
