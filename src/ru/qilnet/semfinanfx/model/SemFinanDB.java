package ru.qilnet.semfinanfx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Andrey Semenyuk
 */

@XmlRootElement(name = "SemFinanDB")
public class SemFinanDB implements Serializable {
	private static int firstYear;
	private ArrayList<YearTransactionData> allTransactions;

	public SemFinanDB() {
		firstYear = LocalDate.now().getYear();
		allTransactions = new ArrayList<>();
		allTransactions.add(new YearTransactionData());
	}

	public int getFirstYear() {
		return firstYear;
	}

	@XmlElement(name = "YearTransactions")
	public ArrayList<YearTransactionData> getAllTransactions(){
		return allTransactions;
	}

	/**
	 * TODO Доработать метод получения бд
	 */
	public void setTransactions() {}

	public YearTransactionData getYearTransactions() {
		return getYearTransactions(LocalDate.now().getYear());
	}

	public YearTransactionData getYearTransactions(int year) {
		return allTransactions.get(year - firstYear);
	}

	public class YearTransactionData implements Serializable {
		private ArrayList<ObservableList<Transaction>> yearTransactionData = new ArrayList<>(12);

		public YearTransactionData() {
			for (int i = 0; i < 13; i++) {
				yearTransactionData.add(i, FXCollections.observableArrayList(new Transaction()));
			}
		}

		public ObservableList<Transaction> getMonthTransactions() {
			return getMonthTransactions(LocalDate.now().getMonthValue());
		}

		public ObservableList<Transaction> getMonthTransactions(int month) {
			return yearTransactionData.get(month);
		}

		public void setMonthTransactions(int month, ObservableList<Transaction> data) {
			this.yearTransactionData.set(month, data);
		}
	}

}
