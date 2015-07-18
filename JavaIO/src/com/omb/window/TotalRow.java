package com.omb.window;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import com.omb.stock.Portfolio;
import com.omb.stock.Stock;

public class TotalRow extends StockRow {

	private static final long serialVersionUID = -1360374962144810226L;
	private static final Color DEFAULT_BGCOLOR=new Color(248,248,248);
	private Portfolio portfolio;
	
	public TotalRow(Portfolio portfolio){
		super(null, DEFAULT_BGCOLOR, DEFAULT_FONTSIZE-3);
		//initializeRow();
		this.portfolio=portfolio;
	}
	

	public void initializeRow(){  // formatting only
		super.initializeRow();
		code= new StockLabel(fontSize,35);
		company= new StockLabel(fontSize,169);
		//lastTrade= new StockLabel(fontSize,57);
		change= new StockLabel(fontSize,94);
		dayRange= new StockLabel(fontSize,115);
		share= new StockLabel(fontSize,48);
		bid= new StockLabel(fontSize,62);
		value= new StockLabel(fontSize,95);
		pAndL= new StockLabel(fontSize,90);
		
		
		add(code);
		add(company);
		//add(lastTrade);
		add(change);
		add(dayRange);
		add(share);
		add(bid);
		add(value);
		add(pAndL);

		company.setHorizontalAlignment(SwingConstants.RIGHT);
		company.setText("組合今日改變:");
		bid.setHorizontalAlignment(SwingConstants.RIGHT);
		bid.setText("組合總計:");
		change.setHorizontalAlignment(SwingConstants.RIGHT);
		value.setHorizontalAlignment(SwingConstants.RIGHT);
		pAndL.setHorizontalAlignment(SwingConstants.RIGHT);
		

		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		
	    value.setBorder(raisedetched);
	    pAndL.setBorder(raisedetched);
	    change.setBorder(raisedetched);
	}

	private void setValue(double value) {
		NumberFormat formatter = new DecimalFormat("#,###,##0.00");
		String valueText=formatter.format(value);
		if(this.value.getText()==null || !this.value.getText().equalsIgnoreCase(valueText))
			this.value.setText(valueText);		
	}
	private void setpAndL(double pAndL) {
		int pAndLInt = (int) pAndL;
		this.pAndL.setForeground(getChangeColor(pAndLInt));	
		NumberFormat formatter = new DecimalFormat("#,###,##0.00");
		String pAndLStr=formatter.format(pAndL);
		if(this.pAndL.getText()==null || !this.pAndL.getText().equalsIgnoreCase(pAndLStr))
			this.pAndL.setText(pAndLStr);
	}
	private void setChange(double change) {
		int changeInt = (int) change;
		this.change.setForeground(getChangeColor(changeInt));	
		NumberFormat formatter = new DecimalFormat("#,###,##0.00");
		String changeStr=formatter.format(change);
		if(this.change.getText()==null || !this.change.getText().equalsIgnoreCase(changeStr))
			this.change.setText(changeStr);
	}

	synchronized public void updateValueAndPL(){
		double totald=0.0;
		double pAndLd=0.0;	
		double changed=0.0;

		Stock [] stocks = portfolio.getStocks();
		synchronized(stocks){
			for(int i=0; i<stocks.length;i++){			
				totald=totald+stocks[i].getValue();
				pAndLd=pAndLd+stocks[i].getPL();
				changed=changed+stocks[i].getShare()*stocks[i].getChange()*stocks[i].getChangeSign();
			}
			setValue(totald);
			setpAndL(pAndLd);
			setChange(changed);
		}
	}
	

}
