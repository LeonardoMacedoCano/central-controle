package br.com.lcano.centraldecontrole.util;

import br.com.lcano.centraldecontrole.config.PropertiesConfig;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
        ZoneId zoneId = ZoneId.of(PropertiesConfig.getTimeZone());
        ZoneOffset offset = zoneId.getRules().getOffset(LocalDateTime.now());
        return LocalDateTime.now().plusSeconds(offset.getTotalSeconds());
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
}

