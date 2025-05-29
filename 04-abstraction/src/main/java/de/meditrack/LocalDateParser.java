package de.meditrack;

import java.time.YearMonth;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class LocalDateParser {
    public static YearMonth parseDate(String dateStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            return YearMonth.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Falsch formatiertes Datum: " + e.getMessage());
            throw e; // Or handle appropriately (e.g., throw a custom exception)
        }
    }

    public static LocalDate parseJahrMonatZuVollemDatum(YearMonth yearMonth) {
            return yearMonth.atDay(1);
    }

}
