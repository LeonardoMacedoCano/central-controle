package com.backend.centraldecontrole.util;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    private static String timeZoneId;

    @Value("${spring.jackson.time-zone}")
    public void setTimeZoneId(String timeZoneId) {
        DateUtil.timeZoneId = timeZoneId;
    }

    public static Date getDataAtual() {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);

        Date currentDate = new Date();
        currentDate.setTime(currentDate.getTime() + timeZone.getRawOffset());

        return currentDate;
    }

    public static TimeZone getTimeZone() {
        return TimeZone.getTimeZone(timeZoneId);
    }
}
