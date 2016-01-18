package ru.qilnet.semfinan.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Model class for a Transaction.
 *
 * @author Andrey Semenyuk
 */
public class Transaction {
	private final StringProperty description;
	private final StringProperty sum;
	private final ObjectProperty<LocalDate> day;

	/**
	 * Default constructor.
	 */
	public Transaction() {
		this(null, null);
	}

	/**
	 * Constructor with some initial data.
	 *
	 * @param description
	 * @param sum
	 */
	public Transaction(String description, String sum) {
		this.description = new SimpleStringProperty(description);
		this.sum = new SimpleStringProperty(sum);
		this.day = new SimpleObjectProperty<>(LocalDate.now());
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

	public LocalDate getDay() {
		return day.get();
	}

	public void setDay(LocalDate day) {
		this.day.set(day);
	}

	public ObjectProperty<LocalDate> dayProperty() {
		return day;
	}
}
