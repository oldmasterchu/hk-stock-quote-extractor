package com.omb.stock;

public class Delimiter {
	private String headMark;
	private String endMark;
	private char [] headArray;
	private char [] endArray;
	
	public Delimiter(String head, String end){
		this.headMark=head;
		this.endMark=end;
		headArray=this.headMark.toCharArray();
		endArray=this.endMark.toCharArray();
	}
	
	public String getHeadmark(){
		return headMark;
	}
	
	public String getEndmark(){
		return endMark;
	}

	public char [] getHeadArray(){
		return headArray;
	}
	
	public char [] getEndArray(){
		return endArray;
	}
}
