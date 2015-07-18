package com.omb.utility;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.omb.stock.StockExtractor;
import com.omb.stock.FieldCapturer;
import com.omb.stock.FieldsFinder;
import com.omb.stock.PageExtractor;

public class GeneralUtilities {
	public static Image getIconImage(Object object, Toolkit kit, String path, String imageFile, String resUrl){
		Image iconImg=null;
		ImageIcon image = null;
		
		URL url = object.getClass().getClassLoader().getResource(resUrl);		
		if(url !=null){
			image=(new ImageIcon(url));		
			iconImg = image.getImage();
		}else{
			String fullImageFile = path + File.separator + imageFile;
			iconImg= kit.getImage(fullImageFile);
		}
		
		return iconImg;
	}
	
	public static String testConnection(){
		String result="Unknown Error!";
		String spec="http://www.aastocks.com/tc/ltp/rtquote.aspx?symbol=00001";
		URL url=null;
		try{
			url = new URL(spec);
			InputStreamReader rdr= new InputStreamReader(url.openStream());
			PageExtractor aaExtractor = StockExtractor.getNewInstance();
			FieldCapturer [] capturers = aaExtractor.getFieldCapturers();
	
			FieldsFinder finder = new FieldsFinder(rdr, aaExtractor);
			finder.goFind();
			
			if(capturers.length>0)
				result="OK";
				
		}catch(MalformedURLException me){
			Log.write("GeneralUtilities-testConnection() " + me.getMessage());
			result=me.getMessage();
		}catch(IOException ioe){
			Log.write("GeneralUtilities-testConnection() " + ioe.getMessage());
			result=ioe.getMessage();
		}
		return result;
	}
	
	public static void setComponentFixSize(JComponent component, Dimension rect){
		component.setPreferredSize(rect);
		component.setMaximumSize(rect);
		component.setMinimumSize(rect);
	}
	
	public static void flashForAMoment(JComponent cmpt, int flashRaise, int millisecond){
		Color oldColor = cmpt.getForeground();
		int red=oldColor.getRed()+flashRaise;
		int green=oldColor.getGreen()+flashRaise;
		int blue=oldColor.getBlue()+flashRaise;
		if (red>255) red =255;
		if (green>255) green =255;
		if (blue>255) blue =255;
		//Color flashColor = new Color(red,green,blue);
		Color flashColor = new Color(255,255,255);
		try{
			cmpt.setForeground(flashColor);
			cmpt.repaint();
			Thread.sleep(1000);
			cmpt.setForeground(oldColor);
			cmpt.repaint();
		}catch(InterruptedException ie){
			Log.write("GeneralUtilities.flashForAMoment()-"+ie.getMessage());
		}
	}
	
	
}
