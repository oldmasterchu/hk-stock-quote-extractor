package com.omb.io;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

public class NumberTextInputVerifier extends InputVerifier {

	private String errorMessage;
	private int type;
	public static final int NON_NEGATIVE_DOUBLE=0;
	public static final int NON_NEGATIVE_INTEGER=1;
	public static final int POSITIVE_INTEGER=2;
	public NumberTextInputVerifier(String errMsg, int type){
		super();
		errorMessage=errMsg;
		this.type=type;
	}
	@Override
	public boolean verify(JComponent component) {
		JFormattedTextField field =(JFormattedTextField) component;
		String s = field.getText();
		if (s==null || s.trim().length()==0) 
			return true;
		
		boolean result=false;
		
		switch(type){
			case NON_NEGATIVE_DOUBLE:
				double dans=-1.0;
				try{
					dans=Double.parseDouble(s);
					result = (dans >=0.0);
				}catch (Exception e){
					result=false;
				}
				break;
			case NON_NEGATIVE_INTEGER:
				int ians=-1;
				try{
					ians=Integer.parseInt(s);
					result = (ians >=0);
				}catch (Exception e){
					result=false;
				}
				break;
			case POSITIVE_INTEGER:
				int pans=-1;
				try{
					pans=Integer.parseInt(s);
					result = (pans >0);
				}catch (Exception e){
					result=false;
				}
				break;
		}
		
		if (!result)
			if(errorMessage.trim().length()>0){
					Object[] options = { "關閉" };
					JOptionPane.showOptionDialog(null, errorMessage, "錯誤",
				            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
				            null, options, options[0]);
				}
		
		return result;
	}
	

}
