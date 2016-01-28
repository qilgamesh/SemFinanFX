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
	private transient final StringProperty sum;
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
		this.description = new SimpleStringProperty(description);
		this.sum = new SimpleStringProperty(sum);
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

	public String getSum() {
		return sum.get();
	}

	public void setSum(String sum) {
		this.sum.set(sum);
	}

	public StringProperty sumProperty() {
		return sum;
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
	public boolean getIsScheduled() {
		return isScheduled;
	}

	public void setScheduled() {
		isScheduled = true;
	}

	public void setNotScheduled() {
		isScheduled = false;
	}
}
