package ru.qilnet.semfinanfx.model;

import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Model class for all Transactions
 *
 * @author Andrey Semenyuk
 */
@XmlRootElement(name = "TransactionsData")
public class TransactionsData {

	private List<MonthTransactions> transactionsData;

	@XmlElement(name = "Transactions")
	public List<MonthTransactions> getTransactionsData() {
		if (transactionsData == null) {
			transactionsData = new ArrayList<>();
			transactionsData.add(new MonthTransactions());
		}
		return transactionsData;
	}

	public void setTransactionsData(List<MonthTransactions> lmt) {
		this.transactionsData = lmt;
	}

	public void setMonthTransactions(LocalDate date, ObservableList<Transaction> mt) {
		getMonthTransactions(date).setMonthTransactions(mt);
	}

	/**
	 * Returns the data as an MonthTransactions object for given month.
	 *
	 * @param date given first day of month
	 * @return object of MonthTransactions
	 */
	public MonthTransactions getMonthTransactions(LocalDate date) {
		if (transactionsData != null) {
			for (MonthTransactions mt : transactionsData) {
				if (mt.getMonthDate().equals(date.withDayOfMonth(1)))
					return mt;
			}
			transactionsData.add(new MonthTransactions(date));
			return getMonthTransactions(date);
		}
		return getTransactionsData().get(0);
	}

}
