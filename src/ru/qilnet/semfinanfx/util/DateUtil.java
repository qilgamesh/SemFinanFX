package ru.qilnet.semfinanfx.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Helper functions for handling dates.
 *
 * @author Andrey Semenyuk
 */
public class DateUtil {

	/**
	 * ObservableList of Russian month.
	 */
	private static final ObservableList<String> monthNames = FXCollections.observableArrayList(
			"Январь",
			"Февраль",
			"Март",
			"Апрель",
			"Май",
			"Июнь",
			"Июль",
			"Август",
			"Сентябрь",
			"Октябрь",
			"Ноябрь",
			"Декабрь"
	);

	/**
	 * The date formatter (use full date).
	 */
	private static final DateTimeFormatter DATE_FORMATTER =
			DateTimeFormatter.ofPattern("dd.MM.yyyy");

	/**
	 * Returns the list of months
	 *
	 * @return ObservableList
	 */
	public static ObservableList<String> getListOfMonths() {
		return monthNames;
	}

	/**
	 * Returns the name of month from given LocalDate
	 *
	 * @param date the date to be returned as a string name of month
	 * @return string
	 */
	public static String getMonthName(LocalDate date) {
		return monthNames.get(date.getMonthValue() - 1);
	}

	/**
	 * Converts a given month number to month name
	 *
	 * @param monthNumber month number
	 * @return string
	 */
	public static String getMonthName(int monthNumber) {
		return monthNames.get(monthNumber - 1);
	}

}
