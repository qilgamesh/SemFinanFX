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

	public void setMonthTransactions(ObservableList<Transaction> transactions) {
		this.monthTransactions = transactions;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlAttribute(name = "date")
	public LocalDate getMonthDate() {
		return monthDate;
	}

	public void setMonthDate(LocalDate date) {
		this.monthDate = date.withDayOfMonth(1);
	}
}
