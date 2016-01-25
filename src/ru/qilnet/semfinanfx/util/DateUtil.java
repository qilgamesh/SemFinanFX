package ru.qilnet.semfinanfx.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
	 * The date formatter (use only a day).
	 */
	private static final DateTimeFormatter DAY_FORMATTER =
			DateTimeFormatter.ofPattern("dd");

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

	/**
	 * Returns the given date as a well formatted String.
	 *
	 * @param date the date to be returned as a string
	 * @return formatted string
	 */
	public static String format(LocalDate date) {
		if (date == null) {
			return null;
		}
		return DATE_FORMATTER.format(date);
	}

	/**
	 * Returns the given date as a well formatted String of day value.
	 *
	 * @param date the date to be returned
	 * @return formatted string of day value
	 */
	public static String dayFormat(LocalDate date) {
		if (date == null) {
			return null;
		}
		return DAY_FORMATTER.format(date);
	}

	/**
	 * Converts a String in the format of the defined {@link DateUtil#DATE_FORMATTER}
	 * to a {@link LocalDate} object.
	 * <p>
	 * Returns null if the String could not be converted.
	 *
	 * @param dateString the date as String
	 * @return the date object or null if it could not be converted
	 */
	public static LocalDate parse(String dateString) {
		try {
			return DATE_FORMATTER.parse(dateString, LocalDate::from);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	/**
	 * Converts a String in the format of the defined {@link DateUtil#DAY_FORMATTER}
	 * to a {@link LocalDate} object.
	 * <p>
	 * Returns null if the String could not be converted.
	 *
	 * @param dayString the day as String
	 * @return the date object or null if it could not be converted
	 */
	public static LocalDate parseDay(String dayString) {
		try {
			return DAY_FORMATTER.parse(dayString, LocalDate::from);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	/**
	 * Checks the String whether it is a valid date.
	 *
	 * @param dateString
	 * @return true if the String is a valid date
	 */
	public static boolean validDate(String dateString) {
		// Try to parse the String.
		return DateUtil.parse(dateString) != null;
	}

	/**
	 * Checks the String whether it is a valid day.
	 *
	 * @param dayString
	 * @return true if the String is a valid date
	 */
	public static boolean validDay(String dayString) {
		// Try to parse the String.
		return DateUtil.parse(dayString) != null;
	}

}
