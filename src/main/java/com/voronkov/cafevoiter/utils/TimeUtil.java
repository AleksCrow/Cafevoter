package com.voronkov.cafevoiter.utils;

import com.voronkov.cafevoiter.model.Cafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeUtil {

    private static Logger log = LoggerFactory.getLogger(TimeUtil.class);

    private TimeUtil() {
    }

    public static boolean canVote(Cafe cafe) {
        log.info("IN ---------------------------------");
        log.info("IN ---------------------------------");

        if (cafe.getCreatedDate().isEqual(LocalDate.now())) {
            return LocalDateTime.now().getHour() < 11;
        }
        return false;
    }
}
