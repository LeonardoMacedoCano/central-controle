package br.com.lcano.centraldecontrole.util;

import br.com.lcano.centraldecontrole.config.PropertiesConfig;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

@Component
public class DateUtil {

    public Date getDataAtual() {
        Date currentDate = new Date();
        currentDate.setTime(currentDate.getTime() + getTimeZone().getRawOffset());
        return currentDate;
    }

    public LocalDateTime getDataHoraAtual() {
        return LocalDateTime.now(ZoneId.of(PropertiesConfig.getTimeZone()));
    }

    public TimeZone getTimeZone() {
        return TimeZone.getTimeZone(PropertiesConfig.getTimeZone());
    }

    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd");
    }

    public static Date parseDate(String dateStr, String format) throws IllegalArgumentException {
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Erro ao converter a data: " + dateStr + " com o formato: " + format, e);
        }
    }

    public static LocalDateTime parseDateTime(String dateStr) {
        return parseDateTime(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static LocalDateTime parseDateTime(String dateStr, String format) throws IllegalArgumentException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDateTime.parse(dateStr, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao converter a data: " + dateStr + " com o formato: " + format, e);
        }
    }
}

