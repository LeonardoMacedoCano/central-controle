package br.com.lcano.centraldecontrole.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {
    @Autowired
    DateUtil dateUtil;

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateUtil.getDateFormat());
        dateFormat.setTimeZone(dateUtil.getTimeZone());

        try {
            return dateFormat.parse(jsonParser.getText());
        } catch (ParseException e) {
            throw new IOException("Erro ao desserializar data", e);
        }
    }
}
