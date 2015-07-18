package com.omb.window;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.omb.io.NumberTextInputVerifier;
import com.omb.stock.Portfolio;
import com.omb.stock.StockBasicInfo;
import com.omb.utility.GeneralUtilities;

public class PortfolioSettingsDialog extends JDialog {
	private static final long serialVersionUID = 7277358380023619708L;
	private static final int DEFAULT_DIALOG_WIDTH = 430;
	private static final int DEFAULT_DIALOG_HEIGHT = 440;
	private static final int BOTTOM_BUTTON_SPACE = 100;
	private static final int COMBINATION_BUTTON_SPACE = 30;
	private static final int NEW_STOCK_FIELD_WIDTH = 80;
	private static final int NEW_STOCK_FIELD_HEIGHT = 28;
	private static final int NEW_STOCK_FIELD_VGAP = 1;
	private static final int DEFAULT_FONT_SIZE = 16;
	private static final int BORDER_FONT_RAISE = 2;
	private static final int COMBINATION_WIDTH = 250;
	private static final int COMBINATION_HEIGHT = 220;

	private JButton okButton;
	private JButton cancelButton;
	private JButton addStockButton;
	private Font defaultFont;
	private JList combinationList;
	private JButton deleteStockButton;
	private JButton editStockButton;
	private JFormattedTextField txtCode;
	private JFormattedTextField txtShare;
	private JFormattedTextField txtBid;
	private DefaultListModel listModel;
	private JScrollPane listScrollPane;
	private Portfolio portfolio;
	private boolean combinationChanged;

	public PortfolioSettingsDialog(Portfolio portfolio) {
		super(portfolio.getFrame(), "設定組合");
		this.portfolio = portfolio;
		defaultFont = new Font(Font.SANS_SERIF, Font.BOLD, DEFAULT_FONT_SIZE);
		combinationChanged=false;
		initialize();
		setVisible(true);
	}
	public void setCombinationChange(){
		combinationChanged=true;
	}
	private void initialize() {
		setSize(DEFAULT_DIALOG_WIDTH, DEFAULT_DIALOG_HEIGHT);
		setResizable(false);

		setLayout(new BorderLayout());

		setNewStockPanel();
		setCombinationPanel();
		setButtonPanel();
		setLocationRelativeTo(portfolio.getFrame());

		addListeners();
		
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
	}

	private void setNewStockPanel() {
		JPanel newStockPanel = new JPanel();
		TitledBorder newStockBorder = BorderFactory.createTitledBorder("新增股票");
		newStockBorder.setTitleFont(new Font(defaultFont.getName(), Font.BOLD,
				DEFAULT_FONT_SIZE + BORDER_FONT_RAISE));
		newStockPanel.setBorder(newStockBorder);
		((FlowLayout) newStockPanel.getLayout()).setVgap(NEW_STOCK_FIELD_VGAP);
		add(newStockPanel, BorderLayout.NORTH);

		Box newStockBox = Box.createVerticalBox();
		// newStockBox.setBorder(BorderFactory.createLineBorder(new
		// Color(200,30,200)));
		JPanel newStockLabelPanel = new JPanel();
		((FlowLayout) newStockLabelPanel.getLayout())
				.setVgap(NEW_STOCK_FIELD_VGAP);

		JLabel lblCode = new JLabel("股票編號");
		JLabel lblShare = new JLabel("股數");
		JLabel lblBid = new JLabel("買入價");
		lblCode.setVerticalAlignment(SwingConstants.BOTTOM);
		lblShare.setVerticalAlignment(SwingConstants.BOTTOM);
		lblBid.setVerticalAlignment(SwingConstants.BOTTOM);
		lblCode.setFont(defaultFont);
		lblShare.setFont(defaultFont);
		lblBid.setFont(defaultFont);
		// lblBid.setBorder(BorderFactory.createLineBorder(new
		// Color(100,30,200)));
		Dimension rect = new Dimension(NEW_STOCK_FIELD_WIDTH,
				NEW_STOCK_FIELD_HEIGHT);

		GeneralUtilities.setComponentFixSize(lblCode, rect);
		GeneralUtilities.setComponentFixSize(lblShare, rect);
		GeneralUtilities.setComponentFixSize(lblBid, rect);
		newStockLabelPanel.add(lblCode);
		newStockLabelPanel.add(lblBid);
		newStockLabelPanel.add(lblShare);

		JPanel newStockTextPanel = new JPanel();
		((FlowLayout) newStockTextPanel.getLayout()).setVgap(1);
		txtCode = new JFormattedTextField();
		txtShare = new JFormattedTextField();
		txtBid = new JFormattedTextField(NumberFormat.getNumberInstance());
		txtCode.setInputVerifier(new NumberTextInputVerifier("股票編號輸入錯誤",
				NumberTextInputVerifier.POSITIVE_INTEGER));
		txtShare.setInputVerifier(new NumberTextInputVerifier("股數輸入錯誤",
				NumberTextInputVerifier.NON_NEGATIVE_INTEGER));
		txtBid.setInputVerifier(new NumberTextInputVerifier("買入價輸入錯誤",
				NumberTextInputVerifier.NON_NEGATIVE_DOUBLE));
		GeneralUtilities.setComponentFixSize(txtCode, rect);
		GeneralUtilities.setComponentFixSize(txtShare, rect);
		GeneralUtilities.setComponentFixSize(txtBid, rect);
		newStockTextPanel.add(txtCode);
		newStockTextPanel.add(txtBid);
		newStockTextPanel.add(txtShare);

		// newStockLabelPanel.setBorder(BorderFactory.createLineBorder(new
		// Color(100,30,200)));
		// newStockTextPanel.setBorder(BorderFactory.createLineBorder(new
		// Color(100,30,200)));
		newStockBox.add(newStockLabelPanel);
		newStockBox.add(newStockTextPanel);
		newStockPanel.add(newStockBox);

		addStockButton = new JButton("新增股票");
		// GeneralUtilities.setComponentFixSize(newStockBox, new
		// Dimension(DEFAULT_DIALOG_WIDTH,80));
		newStockPanel.add(addStockButton);

	}

	private void setCombinationPanel() {
		JPanel combinationPanel = new JPanel();
		TitledBorder combinationBorder = BorderFactory
				.createTitledBorder("股票組合");
		combinationBorder.setTitleFont(new Font(defaultFont.getName(),
				Font.BOLD, DEFAULT_FONT_SIZE + BORDER_FONT_RAISE));
		combinationPanel.setBorder(BorderFactory
				.createTitledBorder(combinationBorder));

		Box combinationBox = Box.createHorizontalBox();
		JPanel listPanel = new JPanel();
		listModel = new DefaultListModel();
		combinationList = new JList(listModel);
		combinationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listScrollPane = new JScrollPane(combinationList);
		Dimension rect = new Dimension(COMBINATION_WIDTH, COMBINATION_HEIGHT);
		GeneralUtilities.setComponentFixSize(listScrollPane, rect);
		listPanel.add(listScrollPane);

		JPanel buttonPanel = new JPanel();
		Box buttonBox = Box.createVerticalBox();
		editStockButton = new JButton("編輯選股");
		editStockButton.setEnabled(false);
		deleteStockButton = new JButton("刪除選股");
		buttonBox.add(editStockButton);
		buttonBox.add(Box.createVerticalStrut(COMBINATION_BUTTON_SPACE));
		buttonBox.add(deleteStockButton);
		buttonPanel.add(buttonBox);

		combinationBox.add(listPanel);
		combinationBox.add(buttonPanel);
		combinationPanel.add(combinationBox);

		populateCombination();
		add(combinationPanel, BorderLayout.CENTER);
	}

	private void setButtonPanel() {
		JPanel buttonPanel = new JPanel();
		Box buttonBox = Box.createHorizontalBox();
		okButton = new JButton("確定");
		cancelButton = new JButton("取銷");
		buttonBox.add(okButton);
		buttonBox.add(Box.createHorizontalStrut(BOTTOM_BUTTON_SPACE));
		buttonBox.add(cancelButton);
		buttonPanel.add(buttonBox);
		okButton.requestFocusInWindow();
		getRootPane().setDefaultButton(okButton);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void populateCombination() {
		StockBasicInfo[] stocks = StockBasicInfo.loadStocksFromConfig(portfolio
				.getConfigFile());
		DefaultListModel listModel = (DefaultListModel) combinationList
				.getModel();
		for (int i = 0; i < stocks.length; i++)
			listModel.addElement(stocks[i]);
	}

	private void modifyConfigFile(){
		StockBasicInfo [] stocks =null;
		int count = listModel.getSize();
		if(count >0){
			stocks = new StockBasicInfo[count];
			for(int i=0;i<count;i++){
				stocks[i]=(StockBasicInfo)listModel.get(i);
			}
			if(StockBasicInfo.modifyConfigStocks(stocks, portfolio.getConfigFile())){
				portfolio.reset();
				portfolio.showUpdate();
			}
		}else{
			return;
		}
	}
	private void openEditStockDialog(){
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new StockEditDialog(PortfolioSettingsDialog.this, combinationList);
			}
		});
	}
	private void addListeners() {
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PortfolioSettingsDialog.this.setVisible(false);
				PortfolioSettingsDialog.this.dispose();
			}
		});

		deleteStockButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = combinationList.getSelectedIndex();
				if (index != -1)
					listModel.remove(index);
			}
		});

		addStockButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = combinationList.getSelectedIndex();
				double bid = 0.0;
				int share = 0;
				if (txtCode.getText() == null
						|| txtCode.getText().trim().length() == 0)
					return;
				else {
					int code = Integer.parseInt(txtCode.getText());

					if (txtBid.getText() != null
							&& txtBid.getText().trim().length() != 0)
						bid = Double.parseDouble(txtBid.getText());

					if (txtShare.getText() != null
							&& txtShare.getText().trim().length() != 0)
						share = Integer.parseInt(txtShare.getText());

					if (index != -1)
						listModel.insertElementAt(new StockBasicInfo(code,
								share, bid), index);
					else
						listModel.addElement(new StockBasicInfo(code, share,
								bid));
					txtCode.setText("");
					txtBid.setText("");
					txtShare.setText("");
				}
			}
		});
	
		listModel.addListDataListener(new ListDataListener(){

			@Override
			public void contentsChanged(ListDataEvent e) {
				combinationChanged=true;
			}

			@Override
			public void intervalAdded(ListDataEvent e) {
				combinationChanged=true;
			}

			@Override
			public void intervalRemoved(ListDataEvent e) {
				combinationChanged=true;
			}
		});
		

	
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(combinationChanged){
					modifyConfigFile();
				}
				PortfolioSettingsDialog.this.setVisible(false);
				PortfolioSettingsDialog.this.dispose();
			}
		});
		editStockButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(combinationList.getSelectedIndex()!=-1)
					openEditStockDialog();	
		    }
		});
		combinationList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		        	openEditStockDialog();
		         }
		    }
		});
		
		combinationList.addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e){
				if(combinationList.getSelectedIndex()!=-1)
					editStockButton.setEnabled(true);
		    }
		});
	}

}