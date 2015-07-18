package com.omb.stock;

import java.io.IOException;
import java.io.Reader;

import com.omb.utility.Log;
import java.util.regex.*;

public class ExtractorEngine {
	private SkipFilteredReader reader;
	private FieldCapturer [] capturers;
	
	
	public ExtractorEngine(FieldCapturer [] capturers, char[] skips, Reader rdr){
		this.capturers=capturers;
		this.reader=new SkipFilteredReader( rdr, skips);
	}
	
	public ExtractorEngine(PageExtractor pgExtractor, char[] skips, Reader rdr){
			this(pgExtractor.getFieldCapturers(),skips,rdr);
	}
	
	
	public void fillCapturerContents(){
		int size= capturers.length;
		StringBuilder [] results=new StringBuilder[size];
		int [] endPoss=new int[size];
		int [] markPoss=new int [size];
		boolean [] headFounds= new boolean[size];
		boolean [] endFounds=new boolean[size];
		boolean [] contentFounds = new boolean[size];
		int [] occurences = new int[size];
		int chint;
		
		for (int i=0;i<size;i++){
			endPoss[i]=0;
			markPoss[i]=0;
			headFounds[i]=false;
			endFounds[i]=false;
			contentFounds[i]=false;
			results[i]= new StringBuilder();
			occurences[i]=0;
		}
		try{
			while(true){
				chint= reader.read();
				if(chint==-1)
					break;
				for(int i=0;i<size;i++){
					if(!contentFounds[i]){
						if(!headFounds[i]){
							//locate head i
							if(capturers[i].getDelimiter().getHeadArray()[markPoss[i]]==(char)chint ||
									(capturers[i].getDelimiter().getHeadArray()[markPoss[i]]=='*' && (char)chint!=' ')){
								markPoss[i]++;
								if(markPoss[i]==(capturers[i].getDelimiter().getHeadArray().length)){ //head i found
									headFounds[i]=true;
								}
							}else{
								markPoss[i]=0;
							}
						}else{ //head i found
							if(!endFounds[i]){
								results[i].append((char) chint);
								//locate end i
								if(capturers[i].getDelimiter().getEndArray()[endPoss[i]]==(char)chint){
									endPoss[i]++;
									if(endPoss[i]==(capturers[i].getDelimiter().getEndArray().length)){ //end found
										endFounds[i]=true;
									}
								}else{
									endPoss[i]=0;
								}
							}
						}
						
						if(endFounds[i]){
							occurences[i]++;
							if(occurences[i]==capturers[i].getOccurence()){
								int len = results[i].length();
								int start=len- capturers[i].getDelimiter().getEndmark().length();
								int end = len;
								capturers[i].setContent(results[i].delete(start, end).toString().trim());
								contentFounds[i]=true;
							}else{
								endPoss[i]=0;
								markPoss[i]=0;
								headFounds[i]=false;
								endFounds[i]=false;
								results[i].delete(0, results[i].length());
							}
						}
					}						
				}
			}
		}catch(IOException ioe){
			Log.write("ExtractorEngine-fillCapturerContents() "+ioe.getMessage());
		}
	}

	public void fillCapturerContents2(){
		String stocknamePatternStr=capturers[0].getDelimiter().getHeadmark();
		String lasttradePatternStr=capturers[1].getDelimiter().getHeadmark();
		String changePatternStr=capturers[2].getDelimiter().getHeadmark();
		String changesignPatternStr=capturers[3].getDelimiter().getHeadmark();
		String dayrangePatternStr=capturers[4].getDelimiter().getHeadmark();
		String pageDoc=null;
		
		String stocknameValueStr="";
		String lasttradeValueStr="";
		String changeValueStr="";
		String changesignValueStr="";
		String dayrangeValueStr ="";
		
		try{
		    char[] arr = new char[8 * 1024];
		    StringBuilder buffer = new StringBuilder();
		    int numCharsRead;
		    while ((numCharsRead = reader.read(arr, 0, arr.length)) != -1) {
		        buffer.append(arr, 0, numCharsRead);
		    }
		    reader.close();
		    pageDoc = buffer.toString();
			
		    Pattern stocknamePattern=Pattern.compile(stocknamePatternStr);
		    Matcher stocknameMatcher = stocknamePattern.matcher(pageDoc);
		    if(stocknameMatcher.find()){
		    	capturers[0].setContent(stocknameMatcher.group(1).trim());
		    }
		    
		    Pattern lasttradePattern=Pattern.compile(lasttradePatternStr);
		    Matcher lasttradeMatcher = lasttradePattern.matcher(pageDoc);
		    if(lasttradeMatcher.find()){
		    	capturers[1].setContent(lasttradeMatcher.group(1).trim());
		    }

		    Pattern changePattern=Pattern.compile(changePatternStr);
		    Matcher changeMatcher = changePattern.matcher(pageDoc);
		    if(changeMatcher.find()){
		    	if(changeMatcher.group(2)==null){
		    		capturers[2].setContent("0");
		    	}else{
		    		capturers[2].setContent(changeMatcher.group(2).trim());
		    	}
		    }
		    Pattern changesignPattern=Pattern.compile(changesignPatternStr);
		    Matcher changesignMatcher = changesignPattern.matcher(pageDoc);
		    if(changesignMatcher.find()){
		    	capturers[3].setContent(changesignMatcher.group(1).trim());
		    }
		    Pattern dayrangePattern=Pattern.compile(dayrangePatternStr);
		    Matcher dayrangeMatcher = dayrangePattern.matcher(pageDoc);
		    if(dayrangeMatcher.find()){
		    	capturers[4].setContent(dayrangeMatcher.group(1).trim());
		    }			
		}catch(IOException ioe){
			Log.write("ExtractorEngine-fillCapturerContents() "+ioe.getMessage());
		}
	}
	
}
