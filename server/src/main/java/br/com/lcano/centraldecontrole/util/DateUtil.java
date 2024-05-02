package br.com.lcano.centraldecontrole.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
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

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate getPrimeiroDiaDoMes(Integer ano, Integer mes) {
        return LocalDate.of(ano, mes, 1);
    }

    public static LocalDate getUltimoDiaDoMes(Integer ano, Integer mes) {
        return getPrimeiroDiaDoMes(ano, mes).plusMonths(1).minusDays(1);
    }
}

