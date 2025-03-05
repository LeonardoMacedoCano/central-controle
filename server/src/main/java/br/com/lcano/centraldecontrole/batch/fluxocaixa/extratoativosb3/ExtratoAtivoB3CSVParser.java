package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoativosb3;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoAtivosB3DTO;
import br.com.lcano.centraldecontrole.util.DateUtil;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.ExtratoException;
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

        setField(() -> extrato.setTipoMovimentacao(line[0]), "tipoMovimentacao", line[0]);
        setField(() -> extrato.setDataNegocio(DateUtil.parseDate(line[1], "dd/MM/yyyy")), "dataNegocio", line[1]);
        setField(() -> extrato.setCodigoNegociacao(line[3]), "codigoNegociacao", line[3]);
        setField(() -> extrato.setQuantidade(new BigDecimal(line[5])), "quantidade", line[5]);
        setField(() -> extrato.setPreco(new BigDecimal(parseMonetaryValue(line[6]))), "preco", line[6]);

        return extrato;
    }

    private void setField(Runnable setter, String fieldName, String value) {
        try {
            setter.run();
        } catch (Exception e) {
            throw new ExtratoException.ErroPreenchimentoCampo(fieldName, value);
        }
    }

    public String parseMonetaryValue(String value) {
        return value.replace("R$", "").trim().replace(",", ".");
    }
}
