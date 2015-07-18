package com.omb.window;

import java.awt.Color;

import javax.swing.SwingConstants;


public class StockHeaderRow extends StockRow {

	private static final long serialVersionUID = 7001673050639637102L;
	
	private StockHeaderRow(Color bgColor,int fontSize){
		super(bgColor,fontSize);
	}

	public StockHeaderRow(String [] strings, Color bgColor) {//for testing only
		super(strings,bgColor);
	}
	public void initializeRow(){
		super.initializeRow();
		code= new StockLabel(strings[0],fontSize,45);
		company= new StockLabel(strings[1],fontSize,130);
		lastTrade= new StockLabel(strings[2],fontSize,57);
		change= new StockLabel(strings[3],fontSize,50);
		dayRange= new StockLabel(strings[4],fontSize,115);
		share= new StockLabel(strings[5],fontSize,55);
		bid= new StockLabel(strings[6],fontSize,63);
		value= new StockLabel(strings[7],fontSize,95);
		pAndL= new StockLabel(strings[8],fontSize,90);
		
		
		add(code);
		add(company);
		add(lastTrade);
		add(change);
		add(dayRange);
		add(share);
		add(bid);
		add(value);
		add(pAndL);
		
		code.setHorizontalAlignment(SwingConstants.CENTER);
		company.setHorizontalAlignment(SwingConstants.CENTER);
		lastTrade.setHorizontalAlignment(SwingConstants.CENTER);
		change.setHorizontalAlignment(SwingConstants.CENTER);
		dayRange.setHorizontalAlignment(SwingConstants.CENTER);
		share.setHorizontalAlignment(SwingConstants.CENTER);
		bid.setHorizontalAlignment(SwingConstants.CENTER);
		value.setHorizontalAlignment(SwingConstants.CENTER);
		pAndL.setHorizontalAlignment(SwingConstants.CENTER);
		
	}
}
