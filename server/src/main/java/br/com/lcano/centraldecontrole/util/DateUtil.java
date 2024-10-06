package br.com.lcano.centraldecontrole.util;

import br.com.lcano.centraldecontrole.config.PropertiesConfig;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
public class DateUtil {

    public Date getDataAtual() {
        Date currentDate = new Date();
        currentDate.setTime(currentDate.getTime() + getTimeZone().getRawOffset());
        return currentDate;
    }

    public TimeZone getTimeZone() {
        return TimeZone.getTimeZone(PropertiesConfig.getTimeZone());
    }

    public String getDateFormat() {
        return PropertiesConfig.getDateFormat();
    }

    public static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

