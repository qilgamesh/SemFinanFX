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

	private ObservableList<Transaction> completeTransactions = FXCollections.observableArrayList();
	private ObservableList<Transaction> scheduledTransactions = FXCollections.observableArrayList();
	private MonthTransactions currentTransactions;

	/**
	 * Default constructor
	 */
	public TransactionsData() {
		transactionsData = new ArrayList<>();
		currentTransactions = new MonthTransactions();
		transactionsData.add(currentTransactions);
		updateCurrentTransactions(LocalDate.now().withDayOfMonth(1));
	}

	public List<MonthTransactions> getTransactionsData() {
		writeChanges();
		return transactionsData;
	}

	public void setTransactionsData(List<MonthTransactions> transactionsData) {
		if (this.transactionsData == null) {
			this.transactionsData = new ArrayList<>();
		}
		if (transactionsData != null) {
			this.transactionsData = transactionsData;
			updateCurrentTransactions(LocalDate.now().withDayOfMonth(1));
		}
	}

	public ObservableList<Transaction> getCompleteTransactions() {
		return completeTransactions;
	}

	public ObservableList<Transaction> getScheduledTransactions() {
		return scheduledTransactions;
	}

	public void updateCurrentTransactions(LocalDate date) {
		if (scheduledTransactions.size() > 0)
			scheduledTransactions.clear();
		if (completeTransactions.size() > 0)
			completeTransactions.clear();
		if (currentTransactions.getMonthTransactions().size() != 0) {
			currentTransactions.getMonthTransactions().clear();
		}
		for (MonthTransactions mt : transactionsData) {
			if (mt.getMonthDate().equals(date.withDayOfMonth(1))) {
				currentTransactions = mt;
				break;
			}
		}
		if (currentTransactions.getMonthTransactions().size() == 0) {
			currentTransactions = new MonthTransactions(date);
			transactionsData.add(currentTransactions);
		}

		if (currentTransactions.getMonthTransactions().size() > 0) {
			scheduledTransactions = currentTransactions.getMonthTransactions(true);
			completeTransactions = currentTransactions.getMonthTransactions(false);
		}

	}

	private void writeChanges() {
		boolean needSave = false;
		currentTransactions.getMonthTransactions().clear();
		if (scheduledTransactions.size() > 0) {
			currentTransactions.getMonthTransactions().addAll(scheduledTransactions);
			needSave = true;
		}
		if (completeTransactions.size() > 0) {
			currentTransactions.getMonthTransactions().addAll(completeTransactions);
			needSave = true;
		}
		if (needSave) {
			for (int i = 1; i < transactionsData.size(); i++) {
				if (transactionsData.get(i).getMonthDate().isEqual(currentTransactions.getMonthDate())) {
					transactionsData.set(i, currentTransactions);
					break;
				}
			}
		}
	}

}
