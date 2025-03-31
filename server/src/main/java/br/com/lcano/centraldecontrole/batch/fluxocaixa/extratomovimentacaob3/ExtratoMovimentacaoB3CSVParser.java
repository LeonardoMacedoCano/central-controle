package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratomovimentacaob3;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoMovimentacaoB3DTO;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.CamposExtratoMovimentacaoB3;
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
public class ExtratoMovimentacaoB3CSVParser {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String CURRENCY_SYMBOL = "R$";

    public List<ExtratoMovimentacaoB3DTO> parse(InputStream inputStream) throws Exception {
        List<ExtratoMovimentacaoB3DTO> extratos = new ArrayList<>();

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

    private ExtratoMovimentacaoB3DTO parseExtrato(String[] line) {
        try {
            return ExtratoMovimentacaoB3DTO.builder()
                    .tipoOperacao(getValue(line, CamposExtratoMovimentacaoB3.TIPO_OPERACAO))
                    .dataMovimentacao(DateUtil.parseDate(getValue(line, CamposExtratoMovimentacaoB3.DATA_MOVIMENTACAO), DATE_FORMAT))
                    .tipoMovimentacao(getValue(line, CamposExtratoMovimentacaoB3.TIPO_MOVIMENTACAO))
                    .produto(getValue(line, CamposExtratoMovimentacaoB3.PRODUTO))
                    .quantidade(parseBigDecimal(getValue(line, CamposExtratoMovimentacaoB3.QUANTIDADE), CamposExtratoMovimentacaoB3.QUANTIDADE))
                    .precoUnitario(parseBigDecimal(parseMonetaryValue(getValue(line, CamposExtratoMovimentacaoB3.PRECO_UNITARIO)), CamposExtratoMovimentacaoB3.PRECO_UNITARIO))
                    .precoTotal(parseBigDecimal(parseMonetaryValue(getValue(line, CamposExtratoMovimentacaoB3.PRECO_TOTAL)), CamposExtratoMovimentacaoB3.PRECO_TOTAL))
                    .build();
        } catch (Exception e) {
            throw new ExtratoException.ErroPreenchimentoCampo("Erro ao processar linha do CSV", String.join(",", line));
        }
    }

    private String getValue(String[] line, CamposExtratoMovimentacaoB3 campo) {
        try {
            return line[campo.getIndex()];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ExtratoException.ErroPreenchimentoCampo(campo.getNome(), "Valor ausente");
        }
    }

    private BigDecimal parseBigDecimal(String value, CamposExtratoMovimentacaoB3 campo) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new ExtratoException.ErroPreenchimentoCampo(campo.getNome(), value);
        }
    }

    private String parseMonetaryValue(String value) {
        return value.replace(CURRENCY_SYMBOL, "")
                .replace(".", "")
                .replace(",", ".")
                .trim();
    }
}
