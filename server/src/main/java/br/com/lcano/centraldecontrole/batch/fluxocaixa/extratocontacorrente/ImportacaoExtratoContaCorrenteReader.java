package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratocontacorrente;

import br.com.lcano.centraldecontrole.domain.Arquivo;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoContaCorrenteDTO;
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
public class ImportacaoExtratoContaCorrenteReader implements ItemReader<ExtratoContaCorrenteDTO> {

    private final ArquivoService arquivoService;
    private final Long arquivoId;
    private Iterator<ExtratoContaCorrenteDTO> extratoIterator;

    @Autowired
    public ImportacaoExtratoContaCorrenteReader(ArquivoService arquivoService,
                                                @Value("#{jobParameters['arquivoId']}") Long arquivoId) {
        this.arquivoService = arquivoService;
        this.arquivoId = arquivoId;
    }

    @Override
    public ExtratoContaCorrenteDTO read() throws Exception {
        if (extratoIterator == null) {
            this.initializeExtratoIterator();
        }
        return this.getNextExtrato();
    }

    private void initializeExtratoIterator() throws Exception {
        Arquivo arquivo = this.arquivoService.findByIdwithValidation(arquivoId);
        List<ExtratoContaCorrenteDTO> extratos = this.parseExtratos(arquivo.getConteudo());
        extratoIterator = extratos.iterator();
    }

    private List<ExtratoContaCorrenteDTO> parseExtratos(byte[] arquivoConteudo) throws Exception {
        ExtratoContaCorrenteCSVParser extratoContaCorrenteCSVParser = new ExtratoContaCorrenteCSVParser();
        return extratoContaCorrenteCSVParser.parse(new ByteArrayInputStream(arquivoConteudo));
    }

    private ExtratoContaCorrenteDTO getNextExtrato() {
        if (extratoIterator != null && extratoIterator.hasNext()) {
            return extratoIterator.next();
        } else {
            return null;
        }
    }
}
