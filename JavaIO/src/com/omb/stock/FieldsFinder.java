package com.omb.stock;


import java.io.Reader;
import java.util.Hashtable;

public class FieldsFinder {
	private Reader inputRdr;
	private char [] skips;
	private PageExtractor pageExtractor;
	private Hashtable<String, String> dict;
	
	public FieldsFinder(Reader htmlStreamRdr, char [] skips, PageExtractor pageExtractor){
		this.inputRdr=htmlStreamRdr;
		this.skips=skips;
		this.pageExtractor=pageExtractor;
	}
	
	public FieldsFinder(Reader htmlStreamRdr,  PageExtractor pageExtractor){
		char [] skips ={'\r','\n'};
		this.inputRdr=htmlStreamRdr;
		this.skips=skips;
		this.pageExtractor=pageExtractor;
	}
	public void goFind(){
		SkipFilteredReader fr=new SkipFilteredReader(inputRdr, skips);
		FieldCapturer [] capturers = pageExtractor.getFieldCapturers();

		ExtractorEngine engine= new ExtractorEngine(capturers,skips,fr);
		engine.fillCapturerContents();
		dict=new Hashtable<String, String>(capturers.length);
		for(int i=0;i<capturers.length;i++){
			dict.put(capturers[i].getName(), capturers[i].getContent());
		}		
	}
	public void goFind2(){
		SkipFilteredReader fr=new SkipFilteredReader(inputRdr, skips);
		FieldCapturer [] capturers = pageExtractor.getFieldCapturers();

		ExtractorEngine engine= new ExtractorEngine(capturers,skips,fr);
		engine.fillCapturerContents2();
		dict=new Hashtable<String, String>(capturers.length);
		for(int i=0;i<capturers.length;i++){
			dict.put(capturers[i].getName(), capturers[i].getContent());
		}		
	}
	
	public String getFieldValue(String field){
		return dict.get(field);
	}
}
