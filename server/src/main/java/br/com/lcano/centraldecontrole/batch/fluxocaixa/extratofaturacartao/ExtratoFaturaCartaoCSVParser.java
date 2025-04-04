package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratofaturacartao;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoFaturaCartaoDTO;
import br.com.lcano.centraldecontrole.util.DateUtil;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExtratoFaturaCartaoCSVParser {

    public List<ExtratoFaturaCartaoDTO> parse(InputStream inputStream) throws Exception {
        List<ExtratoFaturaCartaoDTO> extratos = new ArrayList<>();

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

    private ExtratoFaturaCartaoDTO parseExtrato(String[] line) {
        ExtratoFaturaCartaoDTO extrato = new ExtratoFaturaCartaoDTO();

        extrato.setDataLancamento(DateUtil.parseDate(line[0]));
        extrato.setDescricao(line[1]);
        extrato.setValor(new BigDecimal(line[2]));
        extrato.setCategoria(line.length > 3 ? line[3] : "");

        return extrato;
    }
}
