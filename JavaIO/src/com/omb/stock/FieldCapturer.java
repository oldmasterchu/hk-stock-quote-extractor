package com.omb.stock;

public class FieldCapturer {
	private Delimiter delimiter;
	private String name;
	private String content;
	private int occurence;
	
	
	public FieldCapturer(String name, Delimiter delimiter, int occurence){
		this.name=name;
		this.delimiter=delimiter;
		this.occurence=occurence;
	}
	
	public FieldCapturer(String name, Delimiter delimiter){
		this(name,delimiter,1);
	}
	
	public String getName(){
		return name;		
	}
	
	public Delimiter getDelimiter(){
		return delimiter;
	}
	
	public String getContent(){
		if(content!=null)
			return content;
		else
			return "";
	}
	
	public void setContent(String content){
		this.content=content;
	}
	
	public int getOccurence(){
		return occurence;
	}
}
