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

	/**
	 * types of transactions
	 */
	public enum type {
		CREDIT, DEBIT
	};

	private List<MonthTransactions> transactionsData;

	private ObservableList<Transaction> doneTransactions = FXCollections.observableArrayList();
	private ObservableList<Transaction> schedTransactions = FXCollections.observableArrayList();
	private MonthTransactions curTransactions;

	/**
	 * Default constructor
	 */
	public TransactionsData() {
		curTransactions = new MonthTransactions();
		List<MonthTransactions> tmp = new ArrayList<>();
		tmp.add(curTransactions);
		setTransactionsData(tmp);
	}

	/**
	 * Default constructor
	 */
	public TransactionsData(List<MonthTransactions> transactionsData) {
		setTransactionsData(transactionsData);
	}

	public List<MonthTransactions> getTransactionsData() {
		writeChanges();
		return transactionsData;
	}

	public void setTransactionsData(List<MonthTransactions> transactionsData) {
		if (transactionsData == null) {
			transactionsData = new ArrayList<>();
		}
		this.transactionsData = transactionsData;
		updateTransactions(LocalDate.now().withDayOfMonth(1));
	}

	public ObservableList<Transaction> getDoneTransactions() {
		return doneTransactions;
	}

	public ObservableList<Transaction> getDoneTransactions(type t) {
		if (doneTransactions.size() > 0) {
			ObservableList<Transaction> tmpList = FXCollections.observableArrayList();
			for (Transaction tr : doneTransactions) {
				switch (t) {
					case CREDIT:
						if (tr.getCreditSum() != null) {
							tmpList.add(tr);
						}
						break;
					case DEBIT:
						if (tr.getDebitSum() != null) {
							tmpList.add(tr);
						}
						break;
				}
			}
			return tmpList;
		}
		return doneTransactions;
	}
	/**
	 * If no params returns all sheduled transactions
	 *
	 * @return
	 */
	public ObservableList<Transaction> getSchedTransactions() {
		return schedTransactions;
	}

	/**
	 * Returns list of typed sheduled transactions
	 * @param t
	 * @return
	 */
	public ObservableList<Transaction> getSchedTransactions(type t) {
		if (schedTransactions.size() > 0) {
			ObservableList<Transaction> tmpList = FXCollections.observableArrayList();
			for (Transaction tr : schedTransactions) {
				switch (t) {
					case CREDIT:
						if (tr.getCreditSum() != null) {
							tmpList.add(tr);
						}
						break;
					case DEBIT:
						if (tr.getDebitSum() != null) {
							tmpList.add(tr);
						}
						break;
				}
			}
			return tmpList;
		}
		return schedTransactions;
	}

	public void updateTransactions(LocalDate date) {
		if (curTransactions.getMonthValue() != date.getMonthValue()) {
			writeChanges();
			if (schedTransactions.size() > 0)
				schedTransactions.clear();
			if (doneTransactions.size() > 0)
				doneTransactions.clear();
			curTransactions = new MonthTransactions(date);
		}

		for (MonthTransactions mt : transactionsData) {
			if (mt.getMonthValue() == date.getMonthValue()) {
				curTransactions = mt;
				break;
			}
		}

		if (curTransactions.size() > 0) {
			schedTransactions = curTransactions.getMonthTransactions(true);
			doneTransactions = curTransactions.getMonthTransactions(false);
		}

	}

	private void writeChanges() {
		MonthTransactions tmpMT = new MonthTransactions(curTransactions.getMonthDate());
		tmpMT.getMonthTransactions().addAll(schedTransactions);
		tmpMT.getMonthTransactions().addAll(doneTransactions);
		if ((tmpMT.size() != curTransactions.size()) && (tmpMT.size() > 0)) {
			boolean exists = false;
			for (int i = 0; i < transactionsData.size(); i++) {
				if (transactionsData.get(i).getMonthValue() == tmpMT.getMonthValue()) {
					transactionsData.set(i, tmpMT);
					exists = true;
					break;
				}
			}
			if (!exists) {
				transactionsData.add(tmpMT);
			}
		}
	}

}
