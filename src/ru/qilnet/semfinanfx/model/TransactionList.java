package ru.qilnet.semfinanfx.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Model class for a TransactionList
 *
 * @author Andrey Semenyuk
 */
public class TransactionList implements Serializable {

	private List<Transaction> transactions;

	public TransactionList() {
		transactions = new ArrayList<>();
		transactions.add(new Transaction());
	}

	public List<Transaction> getTransactions() {
		if (transactions == null) {
			new TransactionList();
		}
		return transactions;
	}

	public List<Transaction> getTransactions(LocalDate date) {
		List<Transaction> tempTransactions = new ArrayList<>();
		int lm = date.lengthOfMonth();
		tempTransactions.addAll(transactions.stream().filter(transaction -> transaction.getDate().isBefore(date.withDayOfMonth(lm)) &&
				transaction.getDate().isAfter(date.withDayOfMonth(1))).collect(Collectors.toList()));
		if (tempTransactions.size() == 0) {
			tempTransactions.add(new Transaction());
		}
		return tempTransactions;
	}


}
