package com.omb.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.omb.utility.GeneralUtilities;

public class RowButton extends JButton {
	private static final long serialVersionUID = -528248954916090887L;
	private static int buttonSize=25;
	public RowButton(String toolTip, Color bgColor,String path, String imageFile, String imageURL){	
		this.setToolTipText(toolTip);
		this.setIcon(
				new ImageIcon(GeneralUtilities.getIconImage(this, Toolkit.getDefaultToolkit(), path,imageFile, imageURL)));
		this.setMaximumSize(new Dimension(buttonSize,buttonSize));
		this.setPreferredSize(new Dimension(buttonSize,buttonSize));
		this.setBackground(bgColor);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
	}
}
