package br.com.lcano.centraldecontrole.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.TimeZone;

@Component
public class DateUtil {
    private static String timeZoneId;

    @Value("${spring.jackson.time-zone}")
    private void setTimeZoneId(String timeZoneId) {
        DateUtil.timeZoneId = timeZoneId;
    }

    public static Date getDataAtual() {
        Date currentDate = new Date();
        currentDate.setTime(currentDate.getTime() + getTimeZone().getRawOffset());
        return currentDate;
    }

    public static TimeZone getTimeZone() {
        return TimeZone.getTimeZone(timeZoneId);
    }
}

