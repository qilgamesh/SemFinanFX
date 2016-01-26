package ru.qilnet.semfinanfx.model;

import javafx.beans.property.*;
import ru.qilnet.semfinanfx.util.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Model class for a Transaction.
 *
 * @author Andrey Semenyuk
 */
public class Transaction implements Serializable {
	private final ObjectProperty<LocalDate> date;
	private final StringProperty description;
	private final StringProperty sum;
	private final BooleanProperty isScheduled;
	private final StringProperty dayOfMonth;

	/**
	 * Default constructor.
	 */
	public Transaction() {
		this(LocalDate.now().withDayOfMonth(1), "Начальный остаток", "1");
	}

	/**
	 * Constructor with some initial data with default value of isScheduled and date now().
	 *
	 * @param description
	 * @param sum
	 */
	public Transaction(String description, String sum) {
		this(LocalDate.now(), description, sum);
	}

	/**
	 * Constructor with some initial data with default value of isScheduled.
	 *
	 * @param date
	 * @param description
	 * @param sum
	 */
	public Transaction(LocalDate date, String description, String sum) {
		this(date, description, sum, false);
	}

	/**
	 * Constructor with some initial data.
	 *
	 * @param date
	 * @param description
	 * @param sum
	 * @param isScheduled
	 */
	public Transaction(LocalDate date, String description, String sum, boolean isScheduled) {
		this.description = new SimpleStringProperty(description);
		this.sum = new SimpleStringProperty(sum);
		this.isScheduled = new SimpleBooleanProperty(isScheduled);
		this.date = new SimpleObjectProperty<>(date);
		dayOfMonth = new SimpleStringProperty(String.valueOf(date.getDayOfMonth()));
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

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getDate() {
		return date.get();
	}

	public void setDate(LocalDate date) {
		this.date.set(date);
		this.dayOfMonth.set(String.valueOf(date.getDayOfMonth()));
	}

	public ObjectProperty<LocalDate> dateProperty() {
		return date;
	}

	public String getDayOfMonth() {
		return dayOfMonth.get();
	}

	public StringProperty dayOfMonthProperty() {
		return dayOfMonth;
	}

	public boolean getIsScheduled() {
		return isScheduled.get();
	}
}
