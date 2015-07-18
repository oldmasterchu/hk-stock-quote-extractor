package com.omb.window;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
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
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;

import com.omb.io.NumberTextInputVerifier;
import com.omb.stock.StockBasicInfo;
import com.omb.utility.GeneralUtilities;

public class StockEditDialog extends JDialog {

	private static final long serialVersionUID = 7120327319695050224L;
	private static final int DEFAULT_DIALOG_WIDTH = 210;
	private static final int DEFAULT_DIALOG_HEIGHT = 168;
	private static final int BOTTOM_BUTTON_SPACE = 30;
	private static final int NEW_STOCK_FIELD_WIDTH = 80;
	private static final int NEW_STOCK_FIELD_HEIGHT = 28;
	private static final int NEW_STOCK_FIELD_VGAP = 1;
	private static final int DEFAULT_FONT_SIZE = 16;
	private static final int BORDER_FONT_RAISE = 2;

	private JButton okButton;
	private JButton cancelButton;
	private Font defaultFont;
	private JFormattedTextField txtShare;
	private JFormattedTextField txtBid;
	private PortfolioSettingsDialog parentDialog;
	private JList combinationList;
	private boolean bidChanged;
	private boolean shareChanged;
	private double oldBid;
	private int oldShare;

	public StockEditDialog(Dialog parent, JList comList) {
		super(parent, "更改股票 : "
				+ getSymbol(((StockBasicInfo) comList.getSelectedValue())
						.getCode()));
		this.parentDialog = (PortfolioSettingsDialog) parent;
		this.combinationList = comList;
		defaultFont = new Font(Font.SANS_SERIF, Font.BOLD, DEFAULT_FONT_SIZE);
		initialize();
		setVisible(true);
	}

	private static String getSymbol(int code) {
		NumberFormat format = new DecimalFormat("00000");
		return format.format(code);
	}

	private void initialize() {
		bidChanged = false;
		shareChanged = false;
		setSize(DEFAULT_DIALOG_WIDTH, DEFAULT_DIALOG_HEIGHT);
		setResizable(false);

		setLayout(new BorderLayout());

		setEditStockPanel();
		setButtonPanel();
		setLocationRelativeTo(parentDialog);

		addListeners();

		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
	}

	private void setEditStockPanel() {
		JPanel editStockPanel = new JPanel();
		NumberFormat codeFormat = new DecimalFormat("00000");
		StockBasicInfo stock = (StockBasicInfo) combinationList
				.getSelectedValue();
		TitledBorder editStockBorder = BorderFactory
				.createTitledBorder(codeFormat.format(stock.getCode()));
		editStockBorder.setTitleFont(new Font(defaultFont.getName(), Font.BOLD,
				DEFAULT_FONT_SIZE + BORDER_FONT_RAISE));
		editStockPanel.setBorder(editStockBorder);
		((FlowLayout) editStockPanel.getLayout()).setVgap(NEW_STOCK_FIELD_VGAP);
		add(editStockPanel, BorderLayout.NORTH);

		Box editStockBox = Box.createVerticalBox();
		JPanel editStockLabelPanel = new JPanel();
		((FlowLayout) editStockLabelPanel.getLayout())
				.setVgap(NEW_STOCK_FIELD_VGAP);

		JLabel lblShare = new JLabel("股數");
		JLabel lblBid = new JLabel("買入價");
		lblShare.setVerticalAlignment(SwingConstants.BOTTOM);
		lblBid.setVerticalAlignment(SwingConstants.BOTTOM);
		lblShare.setFont(defaultFont);
		lblBid.setFont(defaultFont);
		Dimension rect = new Dimension(NEW_STOCK_FIELD_WIDTH,
				NEW_STOCK_FIELD_HEIGHT);

		GeneralUtilities.setComponentFixSize(lblShare, rect);
		GeneralUtilities.setComponentFixSize(lblBid, rect);
		editStockLabelPanel.add(lblBid);
		editStockLabelPanel.add(lblShare);

		JPanel editStockTextPanel = new JPanel();
		((FlowLayout) editStockTextPanel.getLayout()).setVgap(1);
		txtShare = new JFormattedTextField();
		txtBid = new JFormattedTextField(NumberFormat.getNumberInstance());
		txtShare.setInputVerifier(new NumberTextInputVerifier("股數輸入錯誤",
				NumberTextInputVerifier.NON_NEGATIVE_INTEGER));
		txtBid.setInputVerifier(new NumberTextInputVerifier("買入價輸入錯誤",
				NumberTextInputVerifier.NON_NEGATIVE_DOUBLE));
		txtShare.setText(String.valueOf(stock.getShare()));
		txtBid.setText(String.valueOf(stock.getBid()));

		if (txtBid.getText() == null || txtBid.getText().trim().length() == 0)
			oldBid = 0.0;
		else
			oldBid = Double.parseDouble(txtBid.getText());
		if (txtShare.getText() == null
				|| txtShare.getText().trim().length() == 0)
			oldShare = 0;
		else
			oldShare = Integer.parseInt(txtShare.getText());
		GeneralUtilities.setComponentFixSize(txtShare, rect);
		GeneralUtilities.setComponentFixSize(txtBid, rect);
		editStockTextPanel.add(txtBid);
		editStockTextPanel.add(txtShare);

		editStockBox.add(editStockLabelPanel);
		editStockBox.add(editStockTextPanel);
		editStockPanel.add(editStockBox);

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

	private void addListeners() {
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StockEditDialog.this.setVisible(false);
				StockEditDialog.this.dispose();
			}
		});

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int share;
				double bid;
				if (txtShare.getText() == null
						|| txtShare.getText().trim().length() == 0)
					share = 0;
				else
					share = Integer.parseInt(txtShare.getText());
				if (txtBid.getText() == null
						|| txtBid.getText().trim().length() == 0)
					bid = 0.0;
				else
					bid = Double.parseDouble(txtBid.getText());
				if (oldBid != bid || oldShare != share) {

					StockBasicInfo stock = (StockBasicInfo) combinationList
							.getSelectedValue();
					stock.setBid(bid);
					stock.setShare(share);

					// DefaultListModel model=(DefaultListModel)
					// combinationList.getModel();
					combinationList.repaint();
					StockEditDialog.this.parentDialog.setCombinationChange();
				}
				StockEditDialog.this.setVisible(false);
				StockEditDialog.this.dispose();
			}
		});


	}
}
