package com.omb.stock;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.omb.utility.Log;

public class Indices {


	private double hsi;
	private String hsi_change;
	private double cei;
	private String cei_change;
	

	public double getHSI(){
		return hsi;
	}
	

	public String getHsiChange() {
		return hsi_change;
	}


	public double getCEI() {
		return cei;
	}


	public String getCeiChange() {
		return cei_change;
	}
	
	public int getHsiChangeSign(){
		return getChangeSign(hsi_change);		
	}
	public int getCeiChangeSign(){
		return getChangeSign(cei_change);		
	}
	private int getChangeSign(String chgStr){
		char c=chgStr.charAt(0);
		if(c=='+'){
			return 1;
		}else{
			if(c=='-')
				return -1;
			else
				return 0;
		}
	}


	public void updateInfo(){
		
		String spec="http://www.aastocks.com/tc/market/HKIndex.aspx";
		URL url=null;
		try{
			url = new URL(spec);
			InputStreamReader rdr= new InputStreamReader(url.openStream());
			PageExtractor indexExtractor = IndexExtractor.getNewInstance();
			//FieldCapturer [] capturers = indexExtractor.getFieldCapturers();
	
			FieldsFinder finder = new FieldsFinder(rdr, indexExtractor);
			finder.goFind();

//			for(int i=0;i<capturers.length;i++){
//				System.out.println(capturers[i].getName() + " : '" + finder.getFieldValue(capturers[i].getName()) + "'");
//			}
			hsi=Double.parseDouble((finder.getFieldValue("hang_seng_index")).replace(",", ""));			
			hsi_change=getCombinedChange(finder,"hsi_change","hsi_change_pc");
			
			cei=Double.parseDouble(finder.getFieldValue("h_share_index").replace(",", ""));			
			cei_change=getCombinedChange(finder,"cei_change","cei_change_pc");
			
		}catch(MalformedURLException me){
			Log.write("Indcies-updateInfo()-MalformedURLException " + me.getMessage());
		}catch(IOException ioe){
			Log.write("Indcies-updateInfo()-IOException " + ioe.getMessage());
		}catch(Exception e){
			Log.write("Indcies-updateInfo()-Exception " + e.getMessage());			
		}
		
	}
	
	private String getCombinedChange(FieldsFinder finder,String changeKey, 
			String changPcKey, String searchToken, int shift){
		String result="";
		int getFrom=0;
		String change=finder.getFieldValue(changeKey);
		getFrom=change.indexOf(searchToken)+shift;
		change=change.substring(getFrom);	
		String changePc=finder.getFieldValue(changPcKey);
		getFrom=changePc.indexOf(searchToken)+shift;
		changePc=changePc.substring(getFrom);	
		result=change.trim().replace(" ", "") + "  " + changePc.trim().replace(" ", "") ;
		
		return result;
	}
	//this replace the above one
	private String getCombinedChange(FieldsFinder finder,String changeKey, 
			String changPcKey){
		String result="";
		String change=finder.getFieldValue(changeKey);
		String changePc=finder.getFieldValue(changPcKey);
		result=change.trim().replace(" ", "") + "  " + changePc.trim().replace(" ", "") ;
		
		return result;
	}
}
