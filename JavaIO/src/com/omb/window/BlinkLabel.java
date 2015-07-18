package com.omb.window;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class BlinkLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	private static final int BLINKING_RATE = 50; 
	private static final int BLINKING_COUNT = 13;
	private static final int RAISE=50;
	private boolean blinkingOn;
	private int count;
	private TimerListener timerListener;

	public BlinkLabel() {
		count = 0;
		blinkingOn = false;
		timerListener = new TimerListener(this);
		Timer timer = new Timer(BLINKING_RATE, timerListener);
		timer.setInitialDelay(0);
		timer.start();
	}

	public void setBlinking(boolean flag) {
		this.blinkingOn = flag;
	}

	public boolean getBlinking(boolean flag) {
		return this.blinkingOn;
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		count = 0;
	}

	
	public void setForegroundTimer(Color fg) {
		super.setForeground(fg);
		if(timerListener!=null)
			timerListener.resetFgBg();
	}

	private class TimerListener implements ActionListener {
		private BlinkLabel bl;
		private Color bg;
		private Color fg;
		private boolean isForeground = true;

		public TimerListener(BlinkLabel bl) {
			this.bl = bl;
			count = 0;
		}
		private void resetFgBg(){
			fg = bl.getForeground();
			int red = fg.getRed()+RAISE;
			int green = fg.getGreen()+RAISE;
			int blue = fg.getBlue()+RAISE;
			if (red>255) red=255;
			if (green>255) green=255;
			if(blue>255) blue=255;
			bg =new Color(red,green,blue);
		}
		
		public void actionPerformed(ActionEvent e) {
			if(blinkingOn){
				count++;
				if (count > BLINKING_COUNT){
					bl.setForeground(fg);
					return;
				}else{
					if (isForeground) {
						bl.setForeground(fg);
					} else {
						bl.setForeground(bg);
					}
					isForeground = !isForeground;
				}
			}
		}
	}

}
