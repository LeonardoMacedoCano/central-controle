package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoconta;

import br.com.lcano.centraldecontrole.domain.Arquivo;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoContaDTO;
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
public class ImportacaoExtratoContaReader implements ItemReader<ExtratoContaDTO> {

    private final ArquivoService arquivoService;
    private final Long arquivoId;
    private Iterator<ExtratoContaDTO> extratoIterator;

    @Autowired
    public ImportacaoExtratoContaReader(ArquivoService arquivoService,
                                        @Value("#{jobParameters['arquivoId']}") Long arquivoId) {
        this.arquivoService = arquivoService;
        this.arquivoId = arquivoId;
    }

    @Override
    public ExtratoContaDTO read() throws Exception {
        if (extratoIterator == null) {
            this.initializeExtratoIterator();
        }
        return this.getNextExtrato();
    }

    private void initializeExtratoIterator() throws Exception {
        Arquivo arquivo = this.arquivoService.findByIdwithValidation(arquivoId);
        List<ExtratoContaDTO> extratos = this.parseExtratos(arquivo.getConteudo());
        extratoIterator = extratos.iterator();
    }

    private List<ExtratoContaDTO> parseExtratos(byte[] arquivoConteudo) throws Exception {
        ExtratoContaCSVParser extratoContaCSVParser = new ExtratoContaCSVParser();
        return extratoContaCSVParser.parse(new ByteArrayInputStream(arquivoConteudo));
    }

    private ExtratoContaDTO getNextExtrato() {
        if (extratoIterator != null && extratoIterator.hasNext()) {
            return extratoIterator.next();
        } else {
            return null;
        }
    }
}
