package com.omb.window;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;

public class StockRow extends JPanel {

	private static final long serialVersionUID = -2159132503482759543L;
	protected StockLabel code;
	protected StockLabel company;
	protected StockLabel lastTrade;
	protected StockLabel change;
	protected StockLabel dayRange;
	protected StockLabel share;
	protected StockLabel bid;
	protected StockLabel value;
	protected StockLabel pAndL;
	protected String [] strings;
	protected Color bgColor;
	protected int fontSize;
	protected static final int DEFAULT_FONTSIZE=16;
	protected static final int DEFAULT_HGAP=8;
	protected static final int DEFAULT_VGAP=3;
	protected static Color incColor = new Color(0,153,0);
	protected static Color uncColor = new Color(102,102,102);
	protected static Color decColor = new Color(204,0,0);
	
	public StockRow(Color bgColor, int fontSize)	{
		super();
		this.bgColor=bgColor;
		this.fontSize=fontSize;
		initializeRow();
	}
	public StockRow(String [] strings, Color bgColor){ 
		this(strings,bgColor,DEFAULT_FONTSIZE);
	}	
	public StockRow(String [] strings, Color bgColor, int fontSize){
		super();
		this.strings=strings;
		this.bgColor=bgColor;
		this.fontSize=fontSize;
		initializeRow();
	}
	
	public void initializeRow(){
		setBackground(bgColor);
		this.setMinimumSize(new Dimension(600,30));
		//this.setPreferredSize(new Dimension(600,30));
		FlowLayout rowLayout = (FlowLayout)getLayout();
		rowLayout.setAlignment(FlowLayout.LEFT);
		rowLayout.setHgap(DEFAULT_HGAP);
		rowLayout.setVgap(DEFAULT_VGAP);
	}
	

	protected Color getChangeColor(int change){
		Color fgColor;
		if(change >0){
			fgColor=incColor;			
		}else{
			if(change==0)
				fgColor=uncColor;
			else
				fgColor=decColor;
		}
		return fgColor;
	}
}
