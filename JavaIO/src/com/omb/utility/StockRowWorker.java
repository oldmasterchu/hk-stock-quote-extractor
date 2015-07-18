package com.omb.utility;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import com.omb.config.Configurator;
import com.omb.stock.Stock;
import com.omb.stock.TradingCalendar;
import com.omb.window.StockEntryRow;
import com.omb.window.TotalRow;

public class StockRowWorker implements Runnable {

	private Stock stock;
	private StockEntryRow stockRow;
	private int waitMilliseconds;
	private Boolean shouldGo;
	private TotalRow totalRow;
	private final int cumCount = 10;

	public StockRowWorker(Stock stock, StockEntryRow stockRow,
			TotalRow totalRow, int waitMilliseconds) {
		this.stock = stock;
		this.stockRow = stockRow;
		this.totalRow = totalRow;
		this.waitMilliseconds = waitMilliseconds;
		shouldGo = true;
	}

	public void stopWorking() {
		shouldGo = false;
	}

	private void updateChangingPartByEDT() {
		try {
			EventQueue.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					updateChangingPart();
				}
			});
		} catch (InterruptedException ie) {
			Log.write(this.getClass().getName()
					+ "-updateChangingPartByEDT-InterruptedException "
					+ ie.getMessage());
		} catch (InvocationTargetException ite) {
			Log.write(this.getClass().getName()
					+ "-updateChangingPartByEDT-InvocationTargetException "
					+ ite.getMessage());
			ite.printStackTrace();
		} catch (Exception e) {
			Log.write(this.getClass().getName()
					+ "-updateChangingPartByEDT-Exception " + e.getMessage());
		}
	}

	private void setFixPartPartByEDT() {
		try {
			EventQueue.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					setFixPart();
				}
			});
		} catch (Exception e) {
			Log.write(this.getClass().getName() + "-setFixPartByEDT "
					+ e.getMessage());
		}
	}

	private void updateChangingPart() {
		System.out.println(stock.getSymbol() + " : updateChangingPart()");
		int change = stock.getChangeSign();
		stockRow.setLastTrade(stock.getLastTrade(), change);
		stockRow.setChange(stock.getChange(), change);
		stockRow.setDayRange(stock.getDayRange());
		Double stockValue = stock.getLastTrade() * stock.getShare();
		Double pAndL = (stock.getLastTrade() - stock.getBid())
				* stock.getShare();
		stockRow.setValue(stockValue);
		stockRow.setpAndL(pAndL);

		totalRow.updateValueAndPL();
	}

	private void setFixPart() {
		System.out.println(stock.getSymbol() + " : setFixPart()");
		stockRow.setCode(stock.getSymbol());
		stockRow.setCompany(stock.getStockName());
		stockRow.setBid(stock.getBid());
		stockRow.setShare(stock.getShare());
		stockRow.getDetailsButton().setToolTipText(
				"打開\"" + stock.getStockName() + "\"相關網頁");
	}

	@Override
	public void run() {
		Boolean isFirst = true;
		double previous = 0;
		Boolean success = false;
		int cum = 0;
		Log.write(getClass() + " " + Thread.currentThread().getName()
				+ "-run()-" + stock.getSymbol() + " thread is starting to run.");
		while (shouldGo) {
//			synchronized (stock) {
				success = stock.updateInfo();
//System.out.println(stock.getSymbol()+": checking success");				
				if (success) {
//System.out.println(stock.getSymbol()+": does success");				
					cum = 0;
					if (isFirst) {
						setFixPartPartByEDT();
						updateChangingPartByEDT();

					} else {
						if (previous != stock.getLastTrade())
							updateChangingPartByEDT();
						else
							System.out.println(stock.getSymbol()
									+ " : no change");

					}
					previous = stock.getLastTrade();
					isFirst = false;
				} else {
					cum++;
//System.out.println(stock.getSymbol()+": NOT success - cum="+cum);				
/*					Log.write(getClass() + " "
							+ Thread.currentThread().getName() + "-run()-"
							+ stock.getSymbol()
							+ " not success in StockRowWorker run().");*/
				}
//			}
		
				if (success || cum > cumCount) {
				cum = 0;
//System.out.println(stock.getSymbol()+": success or cum> - cum="+cum);	
				try {
					Thread.sleep(waitMilliseconds);
				} catch (InterruptedException ie) {
					Log.write(getClass() + "-run() " + ie.getMessage());
				}
			}
		}
		Log.write(getClass() + " " + Thread.currentThread().getName()
				+ "-run()-" + stock.getSymbol() + " thread is going to end.");
	}
}
