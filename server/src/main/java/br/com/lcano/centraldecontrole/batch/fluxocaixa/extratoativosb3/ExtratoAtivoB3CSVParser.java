package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoativosb3;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoAtivosB3DTO;
import br.com.lcano.centraldecontrole.util.DateUtil;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExtratoAtivoB3CSVParser {

    public List<ExtratoAtivosB3DTO> parse(InputStream inputStream) throws Exception {
        List<ExtratoAtivosB3DTO> extratos = new ArrayList<>();

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

    private ExtratoAtivosB3DTO parseExtrato(String[] line) {
        ExtratoAtivosB3DTO extrato = new ExtratoAtivosB3DTO();

        extrato.setDataNegocio(DateUtil.parseDate(line[0], "dd/MM/yyyy"));
        extrato.setTipoMovimentacao(line[1]);
        extrato.setCodigoNegociacao(line[5]);
        extrato.setQuantidade(new BigDecimal(line[6]));
        extrato.setPreco(new BigDecimal(parseMonetaryValue(line[7])));

        return extrato;
    }

    public String parseMonetaryValue(String value) {
        return value.replace("R$", "").trim().replace(",", ".");
    }
}