package com.omb.window;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import com.omb.stock.Portfolio;

public class StockEntryRow extends StockRow {

	private static final long serialVersionUID = 6100368514617717525L;
	private RowButton btnDetails;
	
	public static void setChangeColors(Color inc, Color unc, Color dec){
		incColor=inc;
		uncColor=unc;
		decColor=dec;
	}
 	
	public StockEntryRow( Color bgColor, int fontSize, Portfolio portfolio){
		super(bgColor,fontSize);
	}

	private StockEntryRow(String [] strings, Color bgColor, int fontSize) {//for testing only
		super(strings,bgColor,fontSize);
	}
	public void initializeRow(){  // formatting only
		super.initializeRow();
		code= new StockLabel(fontSize,45);
		company= new StockLabel(fontSize+3,130);
		lastTrade= new StockLabel(fontSize,57);
		change= new StockLabel(fontSize,50);
		dayRange= new StockLabel(fontSize,115);
		share= new StockLabel(fontSize,55);
		bid= new StockLabel(fontSize,63);
		value= new StockLabel(fontSize,95);
		pAndL= new StockLabel(fontSize,90);
		

		
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
		company.setHorizontalAlignment(SwingConstants.LEFT);
		lastTrade.setHorizontalAlignment(SwingConstants.RIGHT);
		change.setHorizontalAlignment(SwingConstants.RIGHT);
		dayRange.setHorizontalAlignment(SwingConstants.RIGHT);
		share.setHorizontalAlignment(SwingConstants.RIGHT);
		bid.setHorizontalAlignment(SwingConstants.RIGHT);
		value.setHorizontalAlignment(SwingConstants.RIGHT);
		pAndL.setHorizontalAlignment(SwingConstants.RIGHT);
		

		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		code.setBorder(raisedetched);
	    company.setBorder(raisedetched);
	    lastTrade.setBorder(raisedetched);
		change.setBorder(raisedetched);
	    dayRange.setBorder(raisedetched);
	    share.setBorder(raisedetched);
		bid.setBorder(raisedetched);
	    value.setBorder(raisedetched); 
	    pAndL.setBorder(raisedetched);
		
		add(btnDetails=new RowButton("打開相關網頁", this.getBackground(),"res","getinfo.png", "res/getinfo.png"));
	}
	public void setDetailAction(ActionListener l){
		btnDetails.addActionListener(l);
	}
	public void setCode(String code) {
		this.code.setText(code);		
	}
	public void setCompany(String company) { 
		this.company.setText(company);
	}
	public void setLastTrade(double lastTrade, int change) {
		this.lastTrade.setForegroundTimer(getChangeColor(change));
		NumberFormat formatter = new DecimalFormat("0.000");
		String lastTradeStr=formatter.format(lastTrade);
		this.lastTrade.setBlinking(true);
		this.lastTrade.setText(lastTradeStr) ;
		//System.out.println(code.getText() + " - change last");
	}
	public void setChange(double valueChange, int change) {
		this.change.setForeground(getChangeColor(change));
		NumberFormat formatter = new DecimalFormat("0.000");
		String changeText=formatter.format(valueChange*change);
		this.change.setText(changeText);
	}
	public void setDayRange(String dayRange) {
		this.dayRange.setText(dayRange);
	}
	public void setShare(int share) {
		this.share.setText(String.valueOf(share));
	}
	public void setBid(double bid) {
		NumberFormat formatter = new DecimalFormat("0.000");
		String bidText=formatter.format(bid);		
		this.bid.setText(bidText);
	}
	public void setValue(double value) {
		NumberFormat formatter = new DecimalFormat("#,###,##0.00");
		String valueText=formatter.format(value);
		if(!valueText.equalsIgnoreCase(this.value.getText())){
			this.value.setText(valueText);	
		}
	}
	public void setpAndL(double pAndL) {
		int pAndLInt = (int) pAndL;
		this.pAndL.setForeground(getChangeColor(pAndLInt));	
		NumberFormat formatter = new DecimalFormat("#,###,##0.00");
		String pAndLStr=formatter.format(pAndL);
		if(!pAndLStr.equalsIgnoreCase(this.pAndL.getText()))
			this.pAndL.setText(pAndLStr);		
	}
	
	
	public RowButton getDetailsButton(){
		return this.btnDetails;
	}
}
