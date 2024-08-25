package br.com.lcano.centraldecontrole.util;

import br.com.lcano.centraldecontrole.config.PropertiesConfig;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
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

    public Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public LocalDate getPrimeiroDiaDoMes(Integer ano, Integer mes) {
        return LocalDate.of(ano, mes, 1);
    }

    public LocalDate getUltimoDiaDoMes(Integer ano, Integer mes) {
        return getPrimeiroDiaDoMes(ano, mes).plusMonths(1).minusDays(1);
    }

}

