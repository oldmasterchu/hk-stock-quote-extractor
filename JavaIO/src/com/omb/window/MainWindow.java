package com.omb.window;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.omb.utility.GeneralUtilities;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -1070341264570224384L;
	//private static final int WIN_WIDTH=800;
	private static final int WIN_HEIGHT=600;
	public MainWindow(String title, int winWidth){
		super();
		Toolkit kit= Toolkit.getDefaultToolkit();

		Image iconImg=GeneralUtilities.getIconImage(this, kit, "res","icon.png", "res/icon.png");
		
		Dimension d = kit.getScreenSize();
		setIconImage(iconImg);
		setTitle(title);
		setMinimumSize(new Dimension(winWidth,WIN_HEIGHT));
		setSize(winWidth,WIN_HEIGHT);
		setLocation((d.width-getWidth())/2,(d.height-getHeight())/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		////////////////////////////////////////////////////////////////
//		JPanel bottomPanel = new JPanel();
//		bottomPanel.setBackground(Color.LIGHT_GRAY);
//		JButton bottomBtn = new JButton("關閉");
//		bottomBtn.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				MainWindow.this.setVisible(false);
//				MainWindow.this.dispose();
//			}
//		});
//		bottomPanel.add(bottomBtn);
//		this.add(bottomPanel,BorderLayout.SOUTH);	
//		/////////////////////////////////////////////////////////////////
	}
	
	public void setLookAndFeel(String lookAndFeel){
		try {
		    // Set System L&F
	        UIManager.setLookAndFeel( lookAndFeel);
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
	}

}
