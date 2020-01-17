package com.voronkov.cafevoiter.utils;

import com.voronkov.cafevoiter.model.Cafe;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeUtil {

    public static int limitHourForVote = 11;

    private TimeUtil() {
    }

    public static boolean canVote(Cafe cafe) {
        if (cafe.getCreatedDate().isEqual(LocalDate.now())) {
            return LocalDateTime.now().getHour() < limitHourForVote;
        }
        return false;
    }
}
