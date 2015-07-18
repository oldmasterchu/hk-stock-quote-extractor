package com.omb.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	private static String logFile="appLog";
	private final static int MAXFILESIZE=40*1024;
	private final static int MAXFILECOUNT=50;
	private static int count=1;
	
	public static synchronized void write(String message){
		if (count > MAXFILECOUNT)
			return;
		DateFormat dateFormat = new SimpleDateFormat("[dd-MMM-yyyy HH:mm:ss] : ");
		Date date = new Date();
    	String dtStr = dateFormat.format(date);
    	BufferedWriter fw = null;
    	String newline=System.getProperty("line.separator");
    	File f = new File(logFile + String.valueOf(count)+".txt");
    	while(f.exists() && f.length()>MAXFILESIZE){
    		f=new File(logFile + String.valueOf(++count)+".txt");
    		if (count > MAXFILECOUNT)
    			return;
    	}
    	try{
    		fw=new BufferedWriter(new FileWriter(f,true));
    		fw.write(dtStr + message+ newline);
System.out.print(dtStr + message+ newline);
    		fw.flush();
    		fw.close();
    	}catch(IOException ioe){
    		System.out.println(ioe.getMessage());
    	}
	}
	
	public static synchronized void remove(){
		File f ;
		int i=1;
		while(true){
			f= new File(logFile + String.valueOf(i++)+".txt");
			if(f.exists())
				f.delete();		
			else
				break;
		}
	}
}
