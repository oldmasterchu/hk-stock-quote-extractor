package com.omb.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.omb.stock.StockBasicInfo;
import com.omb.utility.Log;
import com.omb.xml.XmlTools;

public class Configurator {
	private static final int DEFAULT_UPDATE_FREQUENCY = 1500;
	private static final int DEFAULT_APP_WIN_WIDTH = 820;
	private static final int DEFAULT_BLINK_RATE = 50;
	private static final int DEFAULT_BLINK_COUNT = 13;

	private File configFile;
	private StockBasicInfo[] stocks;
	private int updateFrequency;
	private int appWindowWidth;
	private int blinkRate;
	private int blinkCount;

	public Configurator(File configFile) {
		this.configFile = configFile;
		initialize();
	}

	private int setIntProperty(Element root, String tagName, int defaultValue) {
		String propertyStr = XmlTools.getElementText(XmlTools
				.getElementByTagName(root, tagName));
		return (propertyStr.equalsIgnoreCase("")) ? defaultValue : Integer
				.parseInt(propertyStr);

	}

	private void initialize() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(configFile);

			stocks = StockBasicInfo.loadStocksFromConfigDoc(doc);
			Element root = doc.getDocumentElement();

			updateFrequency= setIntProperty(root, "updateFrequency", DEFAULT_UPDATE_FREQUENCY);
			appWindowWidth= setIntProperty(root, "appWindowWidth", DEFAULT_APP_WIN_WIDTH);
			blinkRate= setIntProperty(root, "blinkRate", DEFAULT_BLINK_RATE);
			blinkCount= setIntProperty(root, "blinkCount", DEFAULT_BLINK_COUNT);
			
			

		} catch (ParserConfigurationException pce) {
			Log.write(this.getClass().getName()
					+ "-initialize()-ParserConfigurationException "
					+ pce.getMessage());
		} catch (IOException ioe) {
			Log.write(this.getClass().getName() + "-initialize()-IOException "
					+ ioe.getMessage());
		} catch (SAXException saxe) {
			Log.write(this.getClass().getName() + "-initialize()-SAXException "
					+ saxe.getMessage());
		} catch (IllegalArgumentException iae) {
			Log.write(this.getClass().getName()
					+ "-initialize()-IllegalArgumentExceptionException "
					+ iae.getMessage());
		} catch (Exception e) {
			Log.write(this.getClass().getName() + "-initialize()-Exception "
					+ e.getMessage());
		}

	}

	public File getConfigFile() {
		return configFile;
	}

	public StockBasicInfo[] getStocks() {
		return stocks;
	}

	public int getUpdateFrequency() {
		return updateFrequency;
	}

	public int getAppWindowWidth() {
		return appWindowWidth;
	}

	public int getBlinkRate() {
		return blinkRate;
	}

	public int getBlinkCount() {
		return blinkCount;
	}

}
