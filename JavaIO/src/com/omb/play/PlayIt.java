package com.omb.play;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.omb.config.Configurator;
import com.omb.stock.Delimiter;
import com.omb.stock.ExtractorEngine;
import com.omb.stock.FieldCapturer;
import com.omb.stock.FieldsFinder;
import com.omb.stock.IndexExtractor;
import com.omb.stock.Indices;
import com.omb.stock.PageExtractor;
import com.omb.stock.Portfolio;
import com.omb.stock.Stock;
import com.omb.stock.StockBasicInfo;
import com.omb.stock.StockExtractor;

public class PlayIt {


	public static void main(String[] args) {
		doWin();
		//doStock();
	}
	
	public static void doConfigurator(){
		Configurator cfg = new Configurator(new File("appConfig.xml"));
		StockBasicInfo [] bases = cfg.getStocks();
		for(int i=0;i<bases.length;i++){
			System.out.print("Code:" + bases[i].getCode());
			System.out.print("\tShare:" + bases[i].getShare());
			System.out.println("\tBid:" + bases[i].getBid());
		}
		
		System.out.println("Update Frequency : "+cfg.getUpdateFrequency());
		System.out.println("App Win Width : "+cfg.getAppWindowWidth());
		System.out.println("Blink Rate : "+cfg.getBlinkRate());
		System.out.println("Blink Count : "+cfg.getBlinkCount());
	}
	
	public static void doDialog(){
		
	}
	
//	public static void doThread(){
//		int [] codes={1,235,267,388,1288,2628};
//		ArrayList<Thread> stockThreads = new ArrayList<Thread>();
//		for(int i=0;i<codes.length;i++){
//			stockThreads.add(new Thread(new StockThread(codes[i], 2000)));			
//		}
//		for(int i=0;i<codes.length;i++){
//			stockThreads.get(i).start();
//		}
//	}
	
	public static void doWin(){
		Portfolio pfo = new Portfolio();	
		pfo.showUpdate();
	}
	public static void doIndices(){
		Indices indices = new Indices();
		indices.updateInfo();
		
		System.out.println("HSI\t\t: " + indices.getHSI());
		System.out.println("HSI Chg \t: " + indices.getHsiChange());
		System.out.println("HSI Chg Sg\t: " + indices.getHsiChangeSign());
		System.out.println("CEI\t\t: " + indices.getCEI());
		System.out.println("CEI Chg \t: " + indices.getCeiChange());
		System.out.println("CEI Chg Sg\t: " + indices.getCeiChangeSign());
	}
	public static void doStock(){
		Stock stock = new Stock(1288);
		stock.updateInfo();
		
		System.out.println("Stock name \t: " + stock.getStockName());
		System.out.println("Stock Code \t: " + stock.getStockNum());
		System.out.println("Symbol \t\t: " + stock.getSymbol());
		System.out.println("Last Trade \t: " + stock.getLastTrade());
		System.out.println("Change \t\t: " + stock.getChange());
		System.out.println("Change Sign \t: " + stock.getChangeSign());
		System.out.println("Day Range \t: " + stock.getDayRange());
		

		stock = new Stock(1);
		stock.updateInfo();
		
		System.out.println("Stock name \t: " + stock.getStockName());
		System.out.println("Stock Code \t: " + stock.getStockNum());
		System.out.println("Symbol \t\t: " + stock.getSymbol());
		System.out.println("Last Trade \t: " + stock.getLastTrade());
		System.out.println("Change \t\t: " + stock.getChange());
		System.out.println("Change Sign \t: " + stock.getChangeSign());
		System.out.println("Day Range \t: " + stock.getDayRange());
	}
	public static void doThat(){
		String spec="http://www.aastocks.com/tc/ltp/rtquote.aspx?symbol=00001";
		URL url=null;
		try{
			url = new URL(spec);
			InputStreamReader rdr= new InputStreamReader(url.openStream());
			PageExtractor aaExtractor = StockExtractor.getNewInstance();
			FieldCapturer [] capturers = aaExtractor.getFieldCapturers();
	
			FieldsFinder finder = new FieldsFinder(rdr, aaExtractor);
			finder.goFind();
			
			for(int i=0;i<capturers.length;i++){
				System.out.println(capturers[i].getName() + " : '" + finder.getFieldValue(capturers[i].getName()) + "'");
			}
		}catch(MalformedURLException me){
			System.out.println(me.getMessage());
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
		
	}
	public static void doIndex(){
		String spec="http://www.aastocks.com/tc/ltp/rtquote.aspx?symbol=00001";
		URL url=null;
		try{
			url = new URL(spec);
			InputStreamReader rdr= new InputStreamReader(url.openStream());
			PageExtractor indexExtractor = IndexExtractor.getNewInstance();
			FieldCapturer [] capturers = indexExtractor.getFieldCapturers();
	
			FieldsFinder finder = new FieldsFinder(rdr, indexExtractor);
			finder.goFind();
			
			for(int i=0;i<capturers.length;i++){
				System.out.println(capturers[i].getName() + " : '" + finder.getFieldValue(capturers[i].getName()) + "'");
			}
		}catch(MalformedURLException me){
			System.out.println(me.getMessage());
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
		
	}	
	public static void doThis(){
		try{
			Reader rdr=new FileReader("D:\\GameDoc\\Developments\\text\\sample.txt");
			PageExtractor aaExtractor = StockExtractor.getNewInstance();
			FieldCapturer [] capturers = aaExtractor.getFieldCapturers();
	
			FieldsFinder finder = new FieldsFinder(rdr, aaExtractor);
			finder.goFind();
			
			for(int i=0;i<capturers.length;i++){
				System.out.println(capturers[i].getName() + " : '" + finder.getFieldValue(capturers[i].getName()) + "'");
			}
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	public static void doIt(){
		char [] skips ={'\r','\n'};
		FieldCapturer [] capturers = { 	new FieldCapturer("mk", new Delimiter("<span class=\"pos bold\">", "</span>"),3),
									new FieldCapturer("m", new Delimiter("<m>", "</m>"),2),
									new FieldCapturer("most", new Delimiter("<most>", "</most>"))
								};
		String original ="I <most>am <mk>a boy.</mk> Who <mk>are</dmk>\r\n you</mk> is\r <m>mem</m> a </most>bc"
							+" here is you go to me <mk>yes</mk> not ham";
		StringReader sr = new StringReader(original);
		ExtractorEngine parser= new ExtractorEngine(capturers,skips,sr);
		parser.fillCapturerContents();
		
		System.out.println("original : " + original);
		for(int i=0;i<capturers.length;i++){
			System.out.println("extract "+ capturers[i].getName() + " : '" + capturers[i].getContent() + "'");
		}		
	}

}
