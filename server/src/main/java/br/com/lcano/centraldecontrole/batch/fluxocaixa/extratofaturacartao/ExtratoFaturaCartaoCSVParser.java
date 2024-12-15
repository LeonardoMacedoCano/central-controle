package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratofaturacartao;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.ExtratoFaturaCartao;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ExtratoFaturaCartaoCSVParser {

    public List<ExtratoFaturaCartao> parse(InputStream inputStream) throws Exception {
        List<ExtratoFaturaCartao> extratos = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] line;
            boolean isFirstLine = true;

            while ((line = csvReader.readNext()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                extratos.add(parseExtrato(line));
            }
        }
        return extratos;
    }

    private ExtratoFaturaCartao parseExtrato(String[] line) {
        ExtratoFaturaCartao extrato = new ExtratoFaturaCartao();

        extrato.setDataLancamento(parseDate(line[0]));
        extrato.setCategoria(line[1]);
        extrato.setDescricao(line[2]);
        extrato.setValor(new BigDecimal(line[3]));

        return extrato;
    }

    private Date parseDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao parsear a data: " + dateStr, e);
        }
    }
}
