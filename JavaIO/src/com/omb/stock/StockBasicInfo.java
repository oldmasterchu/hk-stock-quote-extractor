package com.omb.stock;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.omb.utility.CurrentClassGetter;
import com.omb.utility.Log;
import com.omb.xml.XmlTools;

public class StockBasicInfo {
	private int code;
	private int share;
	private double bid;
		
	public StockBasicInfo(int code, int share, double bid) {
		this.code = code;
		this.share = share;
		this.bid = bid;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getShare() {
		return share;
	}
	public void setShare(int share) {
		this.share = share;
	}
	public double getBid() {
		return bid;
	}
	public void setBid(double bid) {
		this.bid = bid;
	}
	
	/////////////////////////////////////////////////////////////////////////
	
	
	public static StockBasicInfo [] loadStocksFromConfig(String config){
		String currClass=(new CurrentClassGetter()).getClassName();
		File configFile = new File(config);
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=null;
		Document doc=null;
		try{
			builder=factory.newDocumentBuilder();
			doc=builder.parse(configFile);
			
			
		}catch(ParserConfigurationException pce){
			Log.write(currClass+"-loadPortfolioFromConfig()-ParserConfigurationException "
					+pce.getMessage());	
		}catch(IOException ioe){
			Log.write(currClass+"-loadPortfolioFromConfig()-ParserConfigurationException "
					+ioe.getMessage());			
		}catch(SAXException saxe){
			Log.write(currClass+"-loadPortfolioFromConfig()-ParserConfigurationException "
					+saxe.getMessage());			
		}catch(IllegalArgumentException iae){
			Log.write(currClass+"-loadPortfolioFromConfig()-ParserConfigurationException "
					+iae.getMessage());			
		}catch(Exception e){
			Log.write(currClass+"-loadPortfolioFromConfig()-ParserConfigurationException "
					+e.getMessage());		
		}
		
		Element root=doc.getDocumentElement();
		Element [] stockElements = XmlTools.getElementsByTagName(root, "stock");
		ArrayList<StockBasicInfo> stockList= new ArrayList<StockBasicInfo>();
		for(int i=0;i<stockElements.length;i++){
			String codeStr=XmlTools.getElementText(XmlTools.getElementByTagName(stockElements[i], "code"));
			String shareStr=XmlTools.getElementText(XmlTools.getElementByTagName(stockElements[i], "share"));
			String bidStr=XmlTools.getElementText(XmlTools.getElementByTagName(stockElements[i], "bid"));
			int code=Integer.parseInt(codeStr);
			int share=(shareStr.length()>0 ? Integer.parseInt(shareStr) : 0);
			double bid=(bidStr.length()>0 ? Double.parseDouble(bidStr) : 0.0);
			stockList.add(new StockBasicInfo(code, share, bid));
		}
		int size=stockList.size();
		if(size>0){
			StockBasicInfo [] stocks = new StockBasicInfo[size];
			stockList.toArray(stocks);
			return stocks;
		}else
			return null;
		
	}
	
	public static StockBasicInfo [] loadStocksFromConfigDoc(Document doc){
		Element root=doc.getDocumentElement();
		Element [] stockElements = XmlTools.getElementsByTagName(root, "stock");
		ArrayList<StockBasicInfo> stockList= new ArrayList<StockBasicInfo>();
		for(int i=0;i<stockElements.length;i++){
			String codeStr=XmlTools.getElementText(XmlTools.getElementByTagName(stockElements[i], "code"));
			String shareStr=XmlTools.getElementText(XmlTools.getElementByTagName(stockElements[i], "share"));
			String bidStr=XmlTools.getElementText(XmlTools.getElementByTagName(stockElements[i], "bid"));
			int code=Integer.parseInt(codeStr);
			int share=(shareStr.length()>0 ? Integer.parseInt(shareStr) : 0);
			double bid=(bidStr.length()>0 ? Double.parseDouble(bidStr) : 0.0);
			stockList.add(new StockBasicInfo(code, share, bid));
		}
		int size=stockList.size();
		if(size>0){
			StockBasicInfo [] stocks = new StockBasicInfo[size];
			stockList.toArray(stocks);
			return stocks;
		}else
			return null;
		
	}

	public static boolean modifyConfigStocks(StockBasicInfo [] stockInfos, String config){
		boolean endUp=true;
		String currClass=(new CurrentClassGetter()).getClassName();
		File configFile = new File(config);
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=null;
		Document doc=null;
		try{
			builder=factory.newDocumentBuilder();
			doc=builder.parse(configFile);
			/////////////////modification here////////////////////////
			XmlTools.removeElementFromDoc(doc, "stocks");
			Element root=doc.getDocumentElement();
			Element stocks=doc.createElement("stocks");
			for(int i=0;i<stockInfos.length;i++){
				String codeStr=null;
				String shareStr=null;
				String bidStr=null;
				codeStr=String.valueOf(stockInfos[i].getCode());
				if(stockInfos[i].getBid()>0.0)
					bidStr=String.valueOf(stockInfos[i].getBid());
				if(stockInfos[i].getShare()>0)
					shareStr=String.valueOf(stockInfos[i].getShare());
				
				Element stock=doc.createElement("stock");
				
				Element code =doc.createElement("code");
				code.appendChild(doc.createTextNode(codeStr));
				stock.appendChild(code);
				
				if(bidStr!=null){
					Element bid = doc.createElement("bid");
					bid.appendChild(doc.createTextNode(bidStr));
					stock.appendChild(bid);
				}
				
				if(shareStr!=null){
					Element share = doc.createElement("share");
					share.appendChild(doc.createTextNode(shareStr));
					stock.appendChild(share);
				}
				
				stocks.appendChild(stock);
			}
			Node addAt = root.getFirstChild();
			root.insertBefore(stocks, addAt);
			
			//write the content into xml file
		     TransformerFactory transformerFactory = TransformerFactory.newInstance();
		     Transformer transformer = transformerFactory.newTransformer();
		     DOMSource source = new DOMSource(doc);
		     StreamResult result =  new StreamResult(new File(config));
		     transformer.transform(source, result);
		}catch(ParserConfigurationException pce){
			Log.write(currClass+"-modifyConfigStocks()-ParserConfigurationException "
					+pce.getMessage());	
			endUp=false;
		}catch(IOException ioe){
			Log.write(currClass+"-modifyConfigStocks()-IOException "
					+ioe.getMessage());	
			endUp=false;		
		}catch(SAXException saxe){
			Log.write(currClass+"-modifyConfigStocks()-SAXException "
					+saxe.getMessage());
			endUp=false;			
		}catch(IllegalArgumentException iae){
			Log.write(currClass+"-modifyConfigStocks()-IllegalArgumentException "
					+iae.getMessage());	
			endUp=false;	
		}catch(DOMException dome){
			Log.write(currClass+"-modifyConfigStocks()-DOMException "
					+dome.getMessage());
			endUp=false;			
		}catch(Exception e){
			Log.write(currClass+"-modifyConfigStocks()-Exception "
					+e.getMessage());
			endUp=false;		
		}
		return endUp;
	}
	
	@Override
	public String toString() {
		NumberFormat bidFormat = new DecimalFormat("0.000");
		NumberFormat codeFormat = new DecimalFormat("00000");
		return codeFormat.format(code)+" : "+ bidFormat.format(bid)+" X "+
					String.valueOf(share);
	}
	
	
	
	
	/*public static void modifyConfigStocks(StockBasicInfo [] stockInfos, String config){
		String currClass=(new CurrentClassGetter()).getClassName();
		File configFile = new File(config);
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=null;
		Document doc=null;
		try{
			builder=factory.newDocumentBuilder();
			doc=builder.parse(configFile);
			/////////////////modification here////////////////////////
			XmlTools.removeElementFromDoc(doc, "stocks");
			Element root=doc.getDocumentElement();
			Element stocks=doc.createElement("stocks");
			for(int i=0;i<stockInfos.length;i++){
				String codeStr=null;
				String shareStr=null;
				String bidStr=null;
				codeStr=String.valueOf(stockInfos[i].getCode());
				if(stockInfos[i].getBid()>0.0)
					bidStr=String.valueOf(stockInfos[i].getBid());
				if(stockInfos[i].getShare()>0)
					shareStr=String.valueOf(stockInfos[i].getShare());
				
				Element stock=doc.createElement("stock");
				
				Element code =doc.createElement("code");
				code.appendChild(doc.createTextNode(codeStr));
				stock.appendChild(code);
				
				if(bidStr!=null){
					Element bid = doc.createElement("bid");
					bid.appendChild(doc.createTextNode(bidStr));
					stock.appendChild(bid);
				}
				
				if(shareStr!=null){
					Element share = doc.createElement("share");
					share.appendChild(doc.createTextNode(shareStr));
					stock.appendChild(share);
				}
				
				stocks.appendChild(stock);
			}
			root.appendChild(stocks);
			
			//write the content into xml file
		     TransformerFactory transformerFactory = TransformerFactory.newInstance();
		     Transformer transformer = transformerFactory.newTransformer();
		     DOMSource source = new DOMSource(doc);
		     StreamResult result =  new StreamResult(new File(config));
		     transformer.transform(source, result);
		}catch(ParserConfigurationException pce){
			Log.write(currClass+"-modifyConfigStocks()-ParserConfigurationException "
					+pce.getMessage());	
		}catch(IOException ioe){
			Log.write(currClass+"-modifyConfigStocks()-ParserConfigurationException "
					+ioe.getMessage());			
		}catch(SAXException saxe){
			Log.write(currClass+"-modifyConfigStocks()-ParserConfigurationException "
					+saxe.getMessage());			
		}catch(IllegalArgumentException iae){
			Log.write(currClass+"-modifyConfigStocks()-ParserConfigurationException "
					+iae.getMessage());			
		}catch(Exception e){
			Log.write(currClass+"-modifyConfigStocks()-ParserConfigurationException "
					+e.getMessage());		
		}
		
		
	}*/
	
}
