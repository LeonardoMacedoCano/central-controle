package com.backend.centraldecontrole.util;

import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    public static Date getDataAtual() {
        TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");
        Date currentDate = new Date();
        currentDate.setTime(currentDate.getTime() + timeZone.getRawOffset());
        return currentDate;
    }
}
