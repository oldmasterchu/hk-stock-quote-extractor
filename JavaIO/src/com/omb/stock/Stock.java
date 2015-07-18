package com.omb.stock;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.omb.utility.Log;

public class Stock {
	private int stockNum;
	private String symbol;
	private int changeSign;
	private double change;
	private String stockName;
	private double lastTrade;
	private String dayRange;
	private int share;
	private double bid;
	private Boolean successUpdate;
	private double value;
	private double pl;
	
	public Stock(int stockCode, int share, double bid){
		this.share=share;
		this.bid=bid;
		stockNum=stockCode;
		symbol=getSymbolStr();
		successUpdate=false;
	}
	
	public Stock(int stockCode){
		this(stockCode,0,0.0);
	}
	public void setBid(double bid){
		this.bid=bid;
	}	
	public double getBid(){
		return bid;
	}
	private void setValue(){
		value=share * lastTrade;
	}
	public double getValue(){
		return value;
	}
	public double getCost(){
		return share * bid;
	}
	private void setPL(){
		pl=getValue()-getCost();
	}
	public double getPL(){
		return pl;
	}
	public void setShare(int share){
		this.share=share;
	}
	public int getShare(){
		return share;
	}
	public String getSymbol(){
		return symbol;
	}
	
	public int getStockNum() {
		return stockNum;
	}

	public int getChangeSign() {
		return changeSign;
	}

	public double getChange() {
		return change;
	}

	public String getStockName() {
		return stockName;
	}

	public double getLastTrade() {
		return lastTrade;
	}

	public String getDayRange() {
		return dayRange;
	}

	public synchronized Boolean updateInfo(){
		successUpdate=false;
		String spec="http://www.aastocks.com/tc/ltp/rtquote.aspx?symbol="+symbol;
		URL url=null;
		try{
			url = new URL(spec);
			InputStreamReader rdr= new InputStreamReader(url.openStream(),"UTF-8");
			PageExtractor aaExtractor = StockExtractor.getNewInstance();
			FieldsFinder finder = new FieldsFinder(rdr, aaExtractor);
			finder.goFind2();

			stockName=finder.getFieldValue("stock_name");
			dayRange=finder.getFieldValue("day_range").trim().replace(" ", "");
			try{
				lastTrade=Double.parseDouble(finder.getFieldValue("last_trade"));
			}catch(Exception hidden){
				
			}
			change=Double.parseDouble(finder.getFieldValue("change"));
			String signStr=finder.getFieldValue("change_sign").trim();
			if(signStr.equalsIgnoreCase("neg")){
				changeSign = -1;
			}else{
				if(signStr.equalsIgnoreCase("pos")){
					changeSign = 1;
				}else
					changeSign = 0;				
			}
			successUpdate=true;
			setValue();
			setPL();
		}catch(MalformedURLException me){
			Log.write("Stock-"+getSymbol()+"-updateInfo()-MalformedURLException " + me.getMessage());
			successUpdate=false;
		}catch(IOException ioe){
			Log.write("Stock-"+getSymbol()+"-updateInfo()-IOException " + ioe.getMessage());
			successUpdate=false;
		}catch(NumberFormatException ne){
			successUpdate=false;		
		}catch(Exception e){
			Log.write("Stock-"+getSymbol()+"-updateInfo()-Exception " + e.getMessage());	
			System.out.println("Stock-"+getSymbol()+"-updateInfo()-Exception " + e.getMessage());
			successUpdate=false;		
		}
		return successUpdate;
	}
	
	private String getSymbolStr(){
		StringBuilder sb= new StringBuilder();
		int factor = 10000;
		for(int i=0;i<4;i++){
			if((stockNum/factor)>0){
				break;
			}else{
				sb.append('0');
				factor=factor/10;
			}
		}
		sb.append(stockNum);
		return sb.toString();
	}
	
}
