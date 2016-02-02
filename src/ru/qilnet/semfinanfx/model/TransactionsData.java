package ru.qilnet.semfinanfx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Semenyuk
 */
public class TransactionsData {

	private List<MonthTransactions> transactionsData;

	private LocalDate currentDate;
	private ObservableList<Transaction> completeTransactions = FXCollections.observableArrayList();
	private ObservableList<Transaction> scheduledTransactions = FXCollections.observableArrayList();

	/**
	 * Default constructor
	 */
	public TransactionsData() {
		this(null);
	}

	/**
	 * Constructor with given Transaction data
	 *
	 * @param transactionsData list of transaction data
	 */
	public TransactionsData(List<MonthTransactions> transactionsData) {
		if (transactionsData == null) {
			this.transactionsData = new ArrayList<>();
			this.transactionsData.add(new MonthTransactions());
		} else {
			this.transactionsData = transactionsData;
		}
		currentDate = LocalDate.now().withDayOfMonth(1);
		updateCurrentTransactions();
	}

	public List<MonthTransactions> getTransactionsData() {
		writeChanges();
		return transactionsData;
	}

	public ObservableList<Transaction> getCompleteTransactions() {
		return completeTransactions;
	}

	public ObservableList<Transaction> getScheduledTransactions() {
		return scheduledTransactions;
	}

	public void changeCurrentDate(LocalDate newDate) {
		if (!currentDate.isEqual(newDate.withDayOfMonth(1))) {
			writeChanges();
			currentDate = newDate;
			updateCurrentTransactions();
		}
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

		}
		return new MonthTransactions(date);
	}

	private void updateCurrentTransactions() {
		if (scheduledTransactions.size() > 0)
			scheduledTransactions.clear();
		if (completeTransactions.size() > 0)
			completeTransactions.clear();
		scheduledTransactions = getMonthTransactions(currentDate).getTransactionsList(true);
		completeTransactions = getMonthTransactions(currentDate).getTransactionsList(false);
	}

	private void writeChanges() {
		ObservableList<Transaction> tt = FXCollections.observableArrayList();
		if (scheduledTransactions.size() > 0) {
			tt.addAll(scheduledTransactions);
		}
		if (completeTransactions.size() > 0) {
			tt.addAll(completeTransactions);
		}
		if (tt.size() > 0) {
			getMonthTransactions(currentDate).setMonthTransactions(tt);
		}
	}

}
