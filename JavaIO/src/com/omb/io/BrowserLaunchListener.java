package com.omb.io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.omb.stock.Stock;

public class BrowserLaunchListener implements ActionListener {
	private Stock stock;
	
	public BrowserLaunchListener(Stock stock){
		this.stock=stock;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		BrowserLauncher.openURL("http://www.aastocks.com/tc/ltp/rtquote.aspx?symbol="+stock.getSymbol());

	}

}
