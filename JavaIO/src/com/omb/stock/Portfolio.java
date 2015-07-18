package com.omb.stock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.UIManager;
import com.omb.io.BrowserLaunchListener;
import com.omb.config.Configurator;
import com.omb.utility.DateTimePanel;
import com.omb.utility.Log;
import com.omb.utility.StockRowWorker;
import com.omb.window.IndicesBar;
import com.omb.window.MainWindow;
import com.omb.window.MenuSystem;
import com.omb.window.StockEntryRow;
import com.omb.window.StockHeaderRow;
import com.omb.window.TotalRow;

public class Portfolio {

	private Configurator configurator;
	private MainWindow mwin;
	private Box mbox;
	private Box headBox;
	private StockEntryRow [] entries ;
	private Stock [] stocks;
	private final static int DEFAULT_FONT_SIZE=12;
	private TotalRow totalRow;
	private final static Color CLOCK_BAR_COLOR=new Color(228,232,228);
	private final static String CONFIG_FILE="appConfig.xml";
	private MenuSystem menuSystem; 
	private int stockUpdateFreq;
	private final static int BUFFER_HEIGHT=57;
	private StockHeaderRow rowHeaders;
	private IndicesBar indices ;
	private DateTimePanel datetime;
	private StockRowWorker [] workers;
	private String configFile;
	
	public Portfolio(){
		configFile=CONFIG_FILE;
		initialize();
	}
	
	
	public void initialize(){
		getConfiguration();
		initWin();
		addIndicesBar();
		addClock();
		setupHeaders();
		int rowCount = getStockDetails();
		setupStockRows(rowCount);
		setupTotalRow();
		stockUpdateFreq=configurator.getUpdateFrequency();  
		ReadjustWinSize();
		mwin.setVisible(true);
	}
	public void reset(){
		//stop threads

		Log.write(getClass()+"-reset()-start to stopping worker threads");
		for(int i=0;i<workers.length;i++)
			workers[i].stopWorking();
		
		Log.write(getClass()+"-reset()-start to stopping indices thread");		
		indices.stopWorking();
		
		
		mbox.removeAll();
		headBox.removeAll();
		
		indices=null;
		workers=null;
		totalRow=null;

		mbox.add(headBox);
		mbox.add(rowHeaders);
		
		getConfiguration();
		addIndicesBar();
		addOldClock();
		int rowCount = getStockDetails();
		setupStockRows(rowCount);
		setupTotalRow();
		stockUpdateFreq=configurator.getUpdateFrequency();  
		ReadjustWinSize();
		mwin.setVisible(true);
	}
	private void getConfiguration(){
		configurator = new Configurator(new File(configFile));
	}
	public Configurator getConfigurator(){
		return configurator;
	}
	private void addClock(){
		datetime = new DateTimePanel(CLOCK_BAR_COLOR);
		headBox.add(datetime);
		
		Thread dtThread = new Thread(datetime);
		dtThread.setDaemon(true);
		dtThread.setPriority(Thread.MIN_PRIORITY);
		dtThread.start();
	}
	private void addOldClock(){
		headBox.add(datetime);
	}
	private void addIndicesBar(){
		indices = new IndicesBar(CLOCK_BAR_COLOR);
		headBox.add(indices);
		Thread dtThread = new Thread(indices);
		dtThread.setDaemon(true);
		dtThread.setPriority(Thread.MIN_PRIORITY);
		dtThread.start();
		
	}
	
	public void showUpdate(){

		ArrayList<Thread> rowWorkers = new ArrayList<Thread>();
		workers=new StockRowWorker[stocks.length];
		for(int i=0;i<stocks.length;i++){
			workers[i]=new StockRowWorker(stocks[i],entries[i],totalRow,stockUpdateFreq);
			rowWorkers.add(new Thread(workers[i]));	
			rowWorkers.get(i).setDaemon(true);	
			rowWorkers.get(i).start();
		}
	}
	
	private void initWin(){
		/////////////////////////////////////////////////////////////////////
		mwin= new MainWindow("old chu stock portfolio",configurator.getAppWindowWidth());	
		mbox = new Box(BoxLayout.Y_AXIS);
		headBox=new Box(BoxLayout.X_AXIS);
		mbox.add(headBox);
		//mwin.add(mbox,BorderLayout.NORTH);	
		mwin.add(mbox,BorderLayout.CENTER);	
		mwin.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		/////////////////////////////////////////////////////////////////////	
		mwin.setJMenuBar(menuSystem = new MenuSystem(this));			
	}
	private void setupHeaders(){
		String [] rowHeaderStrings ={"編號","公司名稱","現價","升跌","今日波幅","股數","買入價","股值","P/L"};
		rowHeaders= new StockHeaderRow(rowHeaderStrings, Color.WHITE);
		mbox.add(rowHeaders);
	}
	private int getStockDetails(){	
		StockBasicInfo [] stockBases = configurator.getStocks();
		
		int stockCount=stockBases.length;
		stocks = new Stock[stockCount];
		for(int i=0; i<stockCount;i++)
			stocks[i]= new Stock(stockBases[i].getCode(), stockBases[i].getShare(), stockBases[i].getBid());			
		return stockCount;		
	}
	private void setupStockRows(int rowCount){
		entries = new StockEntryRow[rowCount];
		Color rowColor;
		for(int i=0; i<rowCount;i++){
			if(i % 2==0){
				rowColor=new Color(220,220,220);
			}else{
				rowColor=new Color(200,200,200);
			}
			entries[i]= new StockEntryRow(rowColor,DEFAULT_FONT_SIZE,this);
			entries[i].setDetailAction(new BrowserLaunchListener(stocks[i]));
			mbox.add(entries[i]);
		}
	}

	private void setupTotalRow(){
		totalRow = new TotalRow(this);
		mbox.add(totalRow);
	}

	
	
	public void setStocks(Stock[] stocks) {
		this.stocks = stocks;
	}

	public TotalRow getTotalRow() {
		return totalRow;
	}
	

	public StockEntryRow[] getEntries() {
		return entries;
	}

	public Stock[] getStocks() {
		return stocks;
	}
	
	public MainWindow getFrame(){
		return this.mwin;
	}
	public String getConfigFile(){
		return configFile;
	}
	public void ReadjustWinSize(){
		int width=configurator.getAppWindowWidth();
		int height= BUFFER_HEIGHT+mbox.getPreferredSize().height;
		Dimension d=new Dimension(width,height);
		Toolkit kit= Toolkit.getDefaultToolkit();
		Dimension s = kit.getScreenSize();
		mwin.setMinimumSize(d);
		mwin.setSize(d.width,d.height);
		mwin.setLocation((s.width-mwin.getWidth())/2,(s.height-mwin.getHeight())/2);
	}
}
