package com.voronkov.cafevoiter.utils;

import com.voronkov.cafevoiter.model.Cafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public class TimeUtil {

    private static Logger log = LoggerFactory.getLogger(TimeUtil.class);

    private TimeUtil() {
    }

    public static <T extends Comparable<? super T>> boolean isBetween(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) <= 0);
    }

    public static boolean canVote(Cafe cafe) {
        log.info("IN ---------------------------------");
        log.info("IN допустимое время: {}", cafe.getCreatedDate().plusDays(1).withHour(11));
        log.info("IN ---------------------------------");
        return isBetween(LocalDateTime.now(),
                cafe.getCreatedDate(),
                cafe.getCreatedDate().plusDays(1).withHour(11));
    }
}
