package ru.qilnet.semfinanfx.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Model class for a Transaction.
 *
 * @author Andrey Semenyuk
 */
public class Transaction {
	//	private final StringProperty date;
	private final StringProperty description;
	private final StringProperty sum;
	private final StringProperty dayOfMonth;
	private final BooleanProperty scheduled;

	/**
	 * Default constructor.
	 */
	public Transaction() {
		this(null, null);
	}

	/**
	 * Constructor with some initial data with default value of scheduled.
	 *
	 * @param description
	 * @param sum
	 */
	public Transaction(String description, String sum) {
		this(String.valueOf(LocalDate.now().getDayOfMonth()), description, sum);
	}

	/**
	 * Constructor with some initial data with default value of scheduled.
	 *
	 * @param dayOfMonth
	 * @param description
	 * @param sum
	 */
	public Transaction(String dayOfMonth, String description, String sum) {
		this(dayOfMonth, description, sum, false);
	}

	/**
	 * Constructor with some initial data.
	 *
	 * @param dayOfMonth
	 * @param description
	 * @param sum
	 * @param scheduled
	 */
	public Transaction(String dayOfMonth, String description, String sum, boolean scheduled) {
		this.dayOfMonth = new SimpleStringProperty(dayOfMonth);
		this.description = new SimpleStringProperty(description);
		this.sum = new SimpleStringProperty(sum);
		this.scheduled = new SimpleBooleanProperty(scheduled);
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String firstName) {
		this.description.set(firstName);
	}

	public StringProperty descriptionProperty() {
		return description;
	}

	public String getSum() {
		return sum.get();
	}

	public void setSum(String lastName) {
		this.sum.set(lastName);
	}

	public StringProperty sumProperty() {
		return sum;
	}

	public String getDayOfMonth() {
		return dayOfMonth.get();
	}

	public void setDayOfMonth(String day) {
		dayOfMonth.set(day);
	}

	public void setDayOfMonth(int day) {
		dayOfMonth.set(String.valueOf(day));
	}

	public StringProperty dayOfMonthProperty() {
		return dayOfMonth;
	}

	public boolean isScheduled() {
		return scheduled.get();
	}
}
