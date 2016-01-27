package ru.qilnet.semfinanfx.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
	private transient final ObjectProperty<LocalDate> date;
	private transient final StringProperty description;
	private transient final StringProperty sum;
	private transient final StringProperty dayOfMonth;

	private LocalDate dateValue;
	private String descriptionValue;
	private String sumValue;
	private boolean isScheduled = false;

	/**
	 * Default constructor.
	 */
	public Transaction() {
		this(LocalDate.now().withDayOfMonth(1), "NNNNNNNNNNNNNNNNNNNNNNNNN", "1");
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
		this.descriptionValue = description;
		this.sum = new SimpleStringProperty(sum);
		this.sumValue = sum;
		this.isScheduled = isScheduled;
		this.date = new SimpleObjectProperty<>(date);
		this.dateValue = date;
		this.dayOfMonth = new SimpleStringProperty(String.valueOf(date.getDayOfMonth()));
	}

	public String getDescription() {
		return descriptionValue;
	}

	public void setDescription(String description) {
		this.description.set(description);
		this.descriptionValue = description;
	}

	public StringProperty descriptionProperty() {
		return description;
	}

	public String getSum() {
		return sumValue;
	}

	public void setSum(String sum) {
		this.sum.set(sum);
		this.sumValue = sum;
	}

	public StringProperty sumProperty() {
		return sum;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getDate() {
		return dateValue;
	}

	public void setDate(LocalDate date) {
		this.date.set(date);
		this.dateValue = date;
		this.dayOfMonth.set(String.valueOf(date.getDayOfMonth()));
	}

	public ObjectProperty<LocalDate> dateProperty() {
		return date;
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
