package com.tamgitsun.test;

import com.hbl.object.TreeNode;
import com.tamgitsun.analysis.AnalysisTree;
import com.tamgitsun.object.Result;
import com.tamgitsun.object.WordInfo;

public class TreeTest {

	public static void main(String args[]){
		
		System.out.println("-------------test tree start----------------");
		TreeNode tn = new TreeNode();
		tn.root = new WordInfo();
		tn.root.setToken(1);
		tn.root.setWord("1");;
		TreeNode tn1 = new TreeNode();
		tn1.root = new WordInfo();
		tn1.root.setToken(2);
		tn1.root.setWord("2");;
		TreeNode tn2 = new TreeNode();
		tn2.root = new WordInfo();
		tn2.root.setToken(3);
		tn2.root.setWord("3");;
		TreeNode tn3 = new TreeNode();
		tn3.root = new WordInfo();
		tn3.root.setToken(4);
		tn3.root.setWord("4");;
		tn.child.add(tn1);
		tn.child.add(tn2);
		tn1.child.add(tn3);
		tn.displayTree(0);
		System.out.println("--------------test tree end--------------------");
		
		System.out.println("--------------test syntax tree start-----------");
		AnalysisTree at = AnalysisTree.getInstance();
		at.setSentences(args);
		Result result = at.doAnalysis();
		if(result.result == Result.RESULT_TRUE){
			System.out.println("correct");
		}else{
			System.out.println("incorrect");
		}
		TreeNode tree = result.tree;
		System.out.println("tree size:" + tree.child.size());
		for(TreeNode temp : tree.child){
			System.out.println(temp.root.getWord());
		}
		tree.displayTree(0);
		System.out.println("--------------test syntax tree end-------------");
	}
	
}
