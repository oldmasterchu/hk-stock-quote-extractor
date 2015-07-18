package com.omb.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import com.omb.utility.GeneralUtilities;

public class StockLabel extends BlinkLabel {

	private static final long serialVersionUID = 8971604661146860876L;
	private static final int DEFAULT_SIZE=13;
	private static final int ROW_HEIGHT=25;
	private static final String DEFAULT_FONT="Sans Serif";
	
	public StockLabel(int fontSize, int width){
		this(null,fontSize,width);
	}
	
	public StockLabel(String text, String fontFamily, Boolean isBold, int fontSize){
		super.setText(text);
		Font labelFont = null;
		if(isBold)
			labelFont=new Font(fontFamily,Font.BOLD,fontSize);
		else
			labelFont=new Font(fontFamily,Font.PLAIN,fontSize);			
		this.setFont(labelFont);
	}
	
	public StockLabel(String text, String fontFamily, Boolean isBold, int fontSize, Color fgColor){
		this(text,fontFamily,isBold,fontSize);
		this.setForeground(fgColor);		
	}
	public StockLabel(String text, String fontFamily, Boolean isBold, int fontSize, Dimension rect){
		this(text,fontFamily,isBold,fontSize);
		this.setPreferredSize(rect);
		this.setMaximumSize(rect);
		this.setMinimumSize(rect);
	}
	
	public StockLabel(String text, String fontFamily, Boolean isBold, int fontSize, int width){
		this(text,fontFamily,isBold,fontSize);
		setWidth(width);
	}
	public StockLabel(String text){
		this(text,DEFAULT_FONT,true,DEFAULT_SIZE);
	}
	public StockLabel(String text, int width){
		this(text,DEFAULT_FONT,true,DEFAULT_SIZE,width);
	}
	public StockLabel(String text, int fontSize, int width){
		this(text,DEFAULT_FONT,true,fontSize,width);
	}
	public StockLabel(String text, int width, Color fgColor){
		this(text,DEFAULT_SIZE,width,fgColor);
	}
	public StockLabel(String text, int fontSize, int width, Color fgColor){
		this(text,DEFAULT_FONT,true,fontSize,width);
		this.setForeground(fgColor);		
	}
	
	public StockLabel(String text,  Boolean isBold, int fontSize){
		this(text,DEFAULT_FONT, isBold,fontSize);
	}
	
	public StockLabel(String text,  Boolean isBold){
		this(text,DEFAULT_FONT, isBold,DEFAULT_SIZE);
	}
	
	public void setWidth(int width){
		Dimension rect = new Dimension(width, ROW_HEIGHT);
		GeneralUtilities.setComponentFixSize(this, rect);
	}
	
	public void setFontSize(int size){
		Font oldFont = this.getFont();
		Font newFont = new Font(oldFont.getName(),oldFont.getStyle(),size);
		this.setFont(newFont);
	}
}
