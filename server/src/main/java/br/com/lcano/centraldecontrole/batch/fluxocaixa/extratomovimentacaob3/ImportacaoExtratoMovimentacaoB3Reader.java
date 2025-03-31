package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratomovimentacaob3;

import br.com.lcano.centraldecontrole.domain.Arquivo;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoMovimentacaoB3DTO;
import br.com.lcano.centraldecontrole.service.ArquivoService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;

@Component
@StepScope
public class ImportacaoExtratoMovimentacaoB3Reader implements ItemReader<ExtratoMovimentacaoB3DTO> {

    private final ArquivoService arquivoService;
    private final Long arquivoId;
    private Iterator<ExtratoMovimentacaoB3DTO> extratoIterator;

    @Autowired
    public ImportacaoExtratoMovimentacaoB3Reader(ArquivoService arquivoService,
                                                 @Value("#{jobParameters['arquivoId']}") Long arquivoId) {
        this.arquivoService = arquivoService;
        this.arquivoId = arquivoId;
    }

    @Override
    public ExtratoMovimentacaoB3DTO read() throws Exception {
        if (extratoIterator == null) {
            this.initializeExtratoIterator();
        }
        return this.getNextExtrato();
    }

    private void initializeExtratoIterator() throws Exception {
        Arquivo arquivo = this.arquivoService.findByIdwithValidation(arquivoId);
        List<ExtratoMovimentacaoB3DTO> extratos = this.parseExtratos(arquivo.getConteudo());
        extratoIterator = extratos.iterator();
    }

    private List<ExtratoMovimentacaoB3DTO> parseExtratos(byte[] arquivoConteudo) throws Exception {
        ExtratoMovimentacaoB3CSVParser extratoAtivoB3XLSXParser = new ExtratoMovimentacaoB3CSVParser();
        return extratoAtivoB3XLSXParser.parse(new ByteArrayInputStream(arquivoConteudo));
    }

    private ExtratoMovimentacaoB3DTO getNextExtrato() {
        if (extratoIterator != null && extratoIterator.hasNext()) {
            return extratoIterator.next();
        } else {
            return null;
        }
    }
}
