package com.omb.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.omb.stock.StockBasicInfo;
import com.omb.utility.Log;

public class StockCombination {
	private static final String CONFIG_FILE="appConfig.xml";
	
	public Boolean saveStockInfo(String code, String share, String bid){
		Boolean success=false;
		
		return success;
	}
	
	public StockBasicInfo [] loadPortfolio(){
		StockBasicInfo [] stocks = new StockBasicInfo[6];
		
		stocks[0]= new StockBasicInfo(1,30000,119.3);
		stocks[1]= new StockBasicInfo(235,200000,0.790);
		stocks[2]= new StockBasicInfo(267,10000,23.90);
		stocks[3]= new StockBasicInfo(388,7000,179.80);
		stocks[4]= new StockBasicInfo(1288,220000,3.9);
		stocks[5]= new StockBasicInfo(2628,90000,31.21);
		
		return stocks;
	}

	public StockBasicInfo [] loadPortfolioFromConfig(){
		ArrayList<StockBasicInfo> stocks = new ArrayList<StockBasicInfo>();
		File configFile = new File(CONFIG_FILE);
		if(!doesConfigFileExist(configFile)) return null;
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=null;
		Document doc=null;
		try{
			builder=factory.newDocumentBuilder();
			doc=builder.parse(configFile);
			
			
		}catch(ParserConfigurationException pce){
			Log.write(this.getClass().getName()
					+"loadPortfolioFromConfig()-ParserConfigurationException "
					+pce.getMessage());	
		}catch(IOException ioe){
			Log.write(this.getClass().getName()
					+"loadPortfolioFromConfig()-IOException "
					+ioe.getMessage());			
		}catch(SAXException saxe){
			Log.write(this.getClass().getName()
					+"loadPortfolioFromConfig()-SAXException "
					+saxe.getMessage());			
		}catch(IllegalArgumentException iae){
			Log.write(this.getClass().getName()
					+"loadPortfolioFromConfig()-Exception "
					+iae.getMessage());			
		}catch(Exception e){
			Log.write(this.getClass().getName()
					+"loadPortfolioFromConfig()-Exception "
					+e.getMessage());		
		}
		
		Element root=doc.getDocumentElement();
		NodeList children = root.getChildNodes();
		for(int i=0;i<children.getLength();i++){
			Node child=children.item(i);
			if(child instanceof Element){
				if(((Element) child).getTagName().equals("stock")){
					String code="";
					String bid="";
					String share="";
					NodeList grandsons=((Element)child).getChildNodes();
					for(int j=0;j<grandsons.getLength();j++){
						Node grandson=grandsons.item(j);
						if(grandson instanceof Element){
							Element elm = (Element) grandson;
							String tagName = elm.getTagName();
							String data=((Text)elm.getFirstChild()).getData().trim();
							if(tagName.equals("code"))
								code=data;
							if(tagName.equals("bid"))
								bid=data;
							if(tagName.equals("share"))
								share=data;							
						}
					}
					StockBasicInfo stock= new StockBasicInfo(
											Integer.parseInt(code),
											(share.length()==0? 0 :Integer.parseInt(share)),
											(bid.length()==0? 0.0: Double.parseDouble(bid)));
					stocks.add(stock);
				}
			}
		}
		
		StockBasicInfo [] result=null;
		if(stocks.size()>0){
			result = new StockBasicInfo[stocks.size()];
			stocks.toArray(result);
		}
		return result;
	}
	
	private Boolean doesConfigFileExist(File file){
		if(!file.exists()){
			Object[] options = { "關閉" };
			JOptionPane.showOptionDialog(null, "找不到配置檔\"appConfig.xml\"!", "程式起動失敗",
	            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
	            null, options, options[0]);
			return false;
			
		}else
			return true;
	}
	
}
