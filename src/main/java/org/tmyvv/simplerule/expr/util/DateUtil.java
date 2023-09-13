package org.tmyvv.simplerule.expr.util;

import java.time.format.DateTimeFormatter;

public final class DateUtil {
    private DateUtil() { }

    private static final DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String formatYYYYMMDD(java.time.LocalDate date) {
        return YYYYMMDD.format(date);
    }
}
