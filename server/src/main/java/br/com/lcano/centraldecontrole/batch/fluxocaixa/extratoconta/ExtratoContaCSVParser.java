package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoconta;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoContaDTO;
import br.com.lcano.centraldecontrole.util.DateUtil;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExtratoContaCSVParser {

    public List<ExtratoContaDTO> parse(InputStream inputStream) throws Exception {
        List<ExtratoContaDTO> extratos = new ArrayList<>();

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

    private ExtratoContaDTO parseExtrato(String[] line) {
        ExtratoContaDTO extrato = new ExtratoContaDTO();

        extrato.setDataLancamento(DateUtil.parseDate(line[0], "dd/MM/yyyy"));
        extrato.setValor(new BigDecimal(line[1]));
        extrato.setDescricao(line[3]);

        return extrato;
    }
}
