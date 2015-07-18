package com.omb.stock;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class SkipFilteredReader extends FilterReader {
	private char[] skips;
	public SkipFilteredReader(Reader reader, char [] skips){
		super(reader);
		this.skips=skips;
	}
	
	public int read()throws IOException{
		int c= in.read();
		while(inSkips(c))
			c=in.read();
		return c;
	}
	
	private boolean inSkips(int ch){
		boolean isIn=false;
		char chr = (char) ch;
		for(int i=0; i<skips.length; i++){
			if(chr==skips[i])
				return true;
		}
		return isIn;
	}
}
