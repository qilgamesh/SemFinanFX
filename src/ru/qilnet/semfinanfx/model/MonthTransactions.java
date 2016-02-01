package ru.qilnet.semfinanfx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.qilnet.semfinanfx.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * @author Andrey Semenyuk
 */

public class MonthTransactions {

	private LocalDate monthDate;
	private ObservableList<Transaction> monthTransactions;

	/**
	 * Default constructor
	 */
	public MonthTransactions() {
		this(LocalDate.now().withDayOfMonth(1), FXCollections.observableArrayList(new Transaction(1, "Начальный остаток", "1000")));
	}

	/**
	 * Constructor with same parameters
	 *  @param date month
	 *
	 */
	public MonthTransactions(LocalDate date) {
		this(date, FXCollections.observableArrayList());
	}

	/**
	 * Constructor with same parameters
	 *
	 * @param date month
	 * @param mt data as an observable list of Transactions
	 */
	public MonthTransactions(LocalDate date, ObservableList<Transaction> mt) {
		setMonthDate(date);
		setMonthTransactions(mt);
	}

	@XmlElement(name = "Transaction")
	public ObservableList<Transaction> getMonthTransactions() {
		return monthTransactions;
	}

	public ObservableList<Transaction> getTransactionsList(boolean scheduled) {
		if (monthTransactions != null) {
			ObservableList<Transaction> tempList = FXCollections.observableArrayList();
			for (Transaction tr :
					monthTransactions) {
				if (tr.getScheduled() == scheduled) {
					tempList.add(tr);
				}
			}
			return tempList;
		}
		return FXCollections.emptyObservableList();
	}

	public void setMonthTransactions(ObservableList<Transaction> transactions) {
		if (monthTransactions != null) {
			monthTransactions.clear();
		}
		monthTransactions = transactions;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlAttribute(name = "date")
	public LocalDate getMonthDate() {
		return monthDate;
	}

	public void setMonthDate(LocalDate date) {
		monthDate = date.withDayOfMonth(1);
	}

	public int getDebitTotal() {
		if (this.monthTransactions.size() > 0) {
			int debit = 0;
			for (Transaction tr :
					this.monthTransactions) {
				if (tr.getCreditSum() == null) {
					debit += Integer.valueOf(tr.getDebitSum());
				}
			}
			return debit;
		}
		return 0;
	}

	public int getCreditTotal() {
		if (this.monthTransactions.size() > 0) {
			int credit = 0;
			for (Transaction tr :
					this.monthTransactions) {
				if (tr.getDebitSum() == null) {
					credit += Integer.valueOf(tr.getCreditSum());
				}
			}
			return credit;
		}
		return 0;
	}

}
