package com.omb.config;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import com.omb.utility.CurrentClassGetter;
import com.omb.utility.Log;

public class AppSettings {

	private static final String CONFIG_FILE="appConfig.xml";
	public static int getUpdateFreqFromConfig(){
		String currClass=(new CurrentClassGetter()).getClassName();
		int updateFreq=0;
		File configFile = new File(CONFIG_FILE);
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=null;
		Document doc=null;
		try{
			builder=factory.newDocumentBuilder();
			doc=builder.parse(configFile);
			
			
		}catch(ParserConfigurationException pce){
			Log.write(currClass
					+"-loadPortfolioFromConfig()-ParserConfigurationException "
					+pce.getMessage());	
		}catch(IOException ioe){
			Log.write(currClass
					+"-loadPortfolioFromConfig()-IOException "
					+ioe.getMessage());			
		}catch(SAXException saxe){
			Log.write(currClass
					+" loadPortfolioFromConfig()-SAXException "
					+saxe.getMessage());			
		}catch(IllegalArgumentException iae){
			Log.write(currClass
					+" loadPortfolioFromConfig()-Exception "
					+iae.getMessage());			
		}catch(Exception e){
			Log.write(currClass
					+" loadPortfolioFromConfig()-Exception "
					+e.getMessage());		
		}
		
		Element root=doc.getDocumentElement();
		NodeList children = root.getChildNodes();
		for(int i=0;i<children.getLength();i++){
			Node child=children.item(i);
			if(child instanceof Element){
				if(((Element) child).getTagName().equals("updateFrequency")){
					String updateFreqStr=((Text)child.getFirstChild()).getData().trim();					
					updateFreq=(updateFreqStr.length()==0?0:Integer.parseInt(updateFreqStr));
					break;					
				}
			}
		}
		return updateFreq;
	}
	
}
