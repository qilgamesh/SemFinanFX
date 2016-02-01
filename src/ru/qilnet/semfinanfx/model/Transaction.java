package ru.qilnet.semfinanfx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Model class for a Transaction.
 *
 * @author Andrey Semenyuk
 */
public class Transaction implements Serializable {
	private transient final StringProperty description;
	private transient final StringProperty creditSum;
	private transient final StringProperty debitSum;
	private transient final StringProperty dayOfMonth;

	private boolean isScheduled = false;

	/**
	 * Default constructor.
	 */
	public Transaction() {
		this(null, null);
	}

	/**
	 * Constructor with some initial data with default value of isScheduled and day of current month.
	 *
	 * @param description
	 * @param sum
	 */
	public Transaction(String description, String sum) {
		this(LocalDate.now().getDayOfMonth(), description, sum);
	}

	/**
	 * Constructor with some initial data with default value of isScheduled.
	 *
	 * @param dayOfMonth
	 * @param description
	 * @param sum
	 */
	public Transaction(int dayOfMonth, String description, String sum) {
		this(dayOfMonth, description, sum, false);
	}

	/**
	 * Constructor with some initial data.
	 *
	 * @param dayOfMonth
	 * @param description
	 * @param sum
	 * @param isScheduled
	 */
	public Transaction(int dayOfMonth, String description, String sum, boolean isScheduled) {
		if ((sum != null) && (Integer.valueOf(sum) < 0)) {
				this.creditSum = new SimpleStringProperty(null);
				this.debitSum = new SimpleStringProperty(sum);
			} else {
				this.debitSum = new SimpleStringProperty(null);
				this.creditSum = new SimpleStringProperty(sum);
			}
		this.description = new SimpleStringProperty(description);
		this.isScheduled = isScheduled;
		this.dayOfMonth = new SimpleStringProperty(String.valueOf(dayOfMonth));
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public StringProperty descriptionProperty() {
		return description;
	}

	public String getCreditSum() {
		return creditSum.get();
	}

	public String getDebitSum() {
		return debitSum.get();
	}

	public void setCreditSum(String creditSum) {
		if (debitSum != null) {
			debitSum.set(null);
		}
		this.creditSum.set(creditSum);
	}

	public void setDebitSum(String sum) {
		if (creditSum != null) {
			creditSum.set(null);
		}
		this.debitSum.set(sum);
	}

	public StringProperty creditSumProperty() {
		return creditSum;
	}

	public StringProperty debitSumProperty() {
		return debitSum;
	}

	public String getDayOfMonth(){
		return dayOfMonth.get();
	}

	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth.set(dayOfMonth);
	}

	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth.set(String.valueOf(dayOfMonth));
	}

	public StringProperty dayOfMonthProperty() {
		return dayOfMonth;
	}

	public boolean getScheduled() {
		return isScheduled;
	}

	public void setScheduled(boolean selected) {
		isScheduled = selected;
	}

}
