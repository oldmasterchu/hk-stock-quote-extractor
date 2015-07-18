package com.omb.window;

import java.awt.Color;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.SwingConstants;

import com.omb.stock.Indices;
import com.omb.utility.Log;

public class IndicesBar extends StockRow implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private Boolean shouldGo;
	public IndicesBar(Color bgColor){
		super(bgColor,DEFAULT_FONTSIZE-6);
		shouldGo=true;
	}

	public void stopWorking(){
		shouldGo=false;
	}
	public void initializeRow(){
		super.initializeRow();
		this.setMinimumSize(new Dimension(580,30));
		code= new StockLabel("恆生指數",60);	
		company =new StockLabel("",195);	
		lastTrade= new StockLabel("國企指數",60);
		change= new StockLabel("",205);
		add(code);	
		add(company);
		add(lastTrade);
		add(change);
		code.setHorizontalAlignment(SwingConstants.CENTER);		
		company.setHorizontalAlignment(SwingConstants.LEFT);
		lastTrade.setHorizontalAlignment(SwingConstants.CENTER);
		change.setHorizontalAlignment(SwingConstants.LEFT);
		code.setVerticalAlignment(SwingConstants.BOTTOM);		
		company.setVerticalAlignment(SwingConstants.BOTTOM);
		lastTrade.setVerticalAlignment(SwingConstants.BOTTOM);
		change.setVerticalAlignment(SwingConstants.BOTTOM);
	}


	@Override
	public void run() {
		Indices indices = new Indices();
		String hsiDisplay="";
		String ceiDisplay="";
		StockLabel hsi=company;
		StockLabel cei=change;
		NumberFormat formatter = new DecimalFormat("#,###,###.00");
		while(shouldGo){
			indices.updateInfo();
			hsiDisplay=formatter.format(indices.getHSI()) + "  " + indices.getHsiChange();
			ceiDisplay=formatter.format(indices.getCEI()) + "  " + indices.getCeiChange();
			hsi.setText(hsiDisplay);
			cei.setText(ceiDisplay);
			hsi.setForeground(getChangeColor(indices.getHsiChangeSign()));
			cei.setForeground(getChangeColor(indices.getCeiChangeSign()));	
			Thread.yield();		
		}
		Log.write(getClass()+" "+Thread.currentThread().getName()+
				"-run()-indices thread is going to end.");
	}
	
	
}
