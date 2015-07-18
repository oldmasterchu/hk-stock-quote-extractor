package com.omb.utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DateTimePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 2225307168071729564L;
	private static final int ROW_HEIGHT=25;
	private static final int LABEL_WIDTH=230;
	private JLabel datetime;

	public DateTimePanel(Color bgColor){
		FlowLayout layout =(FlowLayout) this.getLayout();
		layout.setAlignment(FlowLayout.RIGHT);		
		datetime = new JLabel();
		datetime.setHorizontalAlignment(SwingConstants.CENTER);
		//datetime.setVerticalAlignment(SwingConstants.BOTTOM);
		this.setBackground(bgColor);
		setWidth(datetime, LABEL_WIDTH);
		this.add(datetime);
		
	}
	
	private void setWidth(JLabel label, int width){
		Dimension rect = new Dimension(width, ROW_HEIGHT);
		label.setPreferredSize(rect);
		label.setMaximumSize(rect);
		label.setMinimumSize(rect);
	}

	@Override
	public void run() {
		while(true){
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss  dd-MMM-yyyy");
			Date date = new Date();
        	datetime.setText(dateFormat.format(date));
			Thread.yield();
		}
	}
}
