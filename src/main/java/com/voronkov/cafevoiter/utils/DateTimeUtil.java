package com.voronkov.cafevoiter.utils;

import java.time.LocalDate;

public class DateTimeUtil {

    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    private DateTimeUtil() {
    }

    public static LocalDate adjustStartDateTime(LocalDate localDate) {
        return adjustDateTime(localDate, MIN_DATE);
    }

    public static LocalDate adjustEndDateTime(LocalDate localDate) {
        return adjustDateTime(localDate, MAX_DATE);
    }

    private static LocalDate adjustDateTime(LocalDate localDate, LocalDate defaultDate) {
        if (localDate != null) {
            return localDate;
        }
        return defaultDate;
    }
}
