package com.omb.stock;

public class StockExtractor {
	private static PageExtractor extractor=null;
	
	public static PageExtractor getNewInstance(){		
		///Singleton causes thread unsafe
		FieldCapturer [] capturers = { 	
				new FieldCapturer("stock_name", new Delimiter("<div class=\"floatL f15\">\\s*(.*?)&nbsp;",""),1),
				new FieldCapturer("last_trade",
						new Delimiter("<span class='.+?'>([\\d|\\.]*?)</span></span></span></span></span></span>",""),1),
				new FieldCapturer("change", new Delimiter("<span class='.+?'>(([\\d|\\.]*?)</span></span></span></div>|(N/A)</span></div></span>)",""),10),
				new FieldCapturer("change_sign", new Delimiter("<span class=\"(.*?) bold\"><span",""),3),
				new FieldCapturer("day_range", 
						new Delimiter("<strong>(.*?)</strong>",""),1)}; 
		//if (extractor==null){
			extractor= new PageExtractor(capturers);			
		//}
		return extractor;
	}
}
