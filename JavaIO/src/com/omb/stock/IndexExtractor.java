package com.omb.stock;

public class IndexExtractor {

	private static PageExtractor extractor=null;
	
	public static PageExtractor getNewInstance(){		
		///Singleton causes thread unsafe
		FieldCapturer [] capturers = { 	
				new FieldCapturer("hang_seng_index", new Delimiter("<div class=\"float_r\">", "</div>"),2),
				new FieldCapturer("hsi_change", 
						new Delimiter("<span class='***'>", "</span>"),6),
				new FieldCapturer("hsi_change_pc",
						new Delimiter("<span class='***'>", "</span>"),7),
				new FieldCapturer("h_share_index", new Delimiter("<div class=\"float_r\">", "</div>"),4),
				new FieldCapturer("cei_change", 
						new Delimiter("<span class='***'>", "</span>"),14),
				new FieldCapturer("cei_change_pc",
						new Delimiter("<span class='***'>", "</span>"),15)};
		//if (extractor==null){
			extractor= new PageExtractor(capturers);			
		//}
		return extractor;
	}
}
