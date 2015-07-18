package com.omb.stock;

import java.util.Dictionary;
import java.util.Hashtable;

public class PageExtractor {
	private FieldCapturer [] capturers;
	private Dictionary<String, FieldCapturer> dict;
	
	public PageExtractor(FieldCapturer [] capturers){
		this.capturers=capturers;
		int size = capturers.length;
		this.dict = new Hashtable<String, FieldCapturer>(size);
		for(int i=0;i<size;i++){
			this.dict.put(this.capturers[i].getName(), this.capturers[i]);
		}		
	}
	
	public FieldCapturer [] getFieldCapturers(){
		return capturers;
	}
	
	public FieldCapturer getFieldCapturer(String capturerName){
		return dict.get(capturerName);
	}
}
