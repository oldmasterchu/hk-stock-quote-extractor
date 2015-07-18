package com.omb.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.omb.stock.Portfolio;
import com.omb.utility.GeneralUtilities;

public class MenuSystem extends JMenuBar {

	private static final long serialVersionUID = -6797064683125040370L;
	private Portfolio portfolio;
	private JMenu mnuSettings;
	private JMenu mnuTools;
	private JMenu mnuHelp;
	private JMenuItem itmStock;
	private JMenuItem itmClose;
	private JMenuItem itmTestConn;
	private JMenuItem itmReset;
	private JMenuItem itmAbout;
	
	public MenuSystem(Portfolio portfolio){
		super();
		this.portfolio=portfolio;
		initizalize();
	}
	
	protected void initizalize(){
		mnuSettings = new JMenu("執行");
		mnuTools=new JMenu("工具");
		mnuHelp=new JMenu("幫助");
		
		itmStock=new JMenuItem("股份設定");
		itmClose=new JMenuItem("結束");
		mnuSettings.add(itmStock);
		mnuSettings.addSeparator();
		mnuSettings.add(itmClose);
		
		itmTestConn=new JMenuItem("測試網絡");
		itmReset=new JMenuItem("重載版面信息");
		mnuTools.add(itmTestConn);
		mnuTools.add(itmReset);
		
		itmAbout=new JMenuItem("關於");
		mnuHelp.add(itmAbout);
		
		add(mnuSettings);
		add(mnuTools);
		add(mnuHelp);
		
		addListeners();
	}
	
	
	private void addListeners(){
		itmTestConn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String result=GeneralUtilities.testConnection();
				Object[] options = { "關閉" };
				
				if(!result.equals("OK")){ 
					JOptionPane.showOptionDialog(null, "測試網絡失敗!", "測試網絡",
				            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
				            null, options, options[0]);
				}else{
					JOptionPane.showOptionDialog(null, "測試網絡成功.", "測試網絡",
			            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			            null, options, options[0]);
					
				}
			}
			
		});
		
		itmClose.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				MenuSystem.this.portfolio.getFrame().setVisible(false);
				MenuSystem.this.portfolio.getFrame().dispose();
				System.exit(0);
			}
			
		});
		
		itmReset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				portfolio.reset();
				portfolio.showUpdate();
			}
			
		});
		
		itmStock.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new PortfolioSettingsDialog(portfolio);
					}
				});
			}
			
		});
	}
}
