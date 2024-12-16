package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratofaturacartao;

import br.com.lcano.centraldecontrole.domain.Arquivo;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoFaturaCartaoDTO;
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
public class ImportacaoExtratoFaturaCartaoReader implements ItemReader<ExtratoFaturaCartaoDTO> {

    private final ArquivoService arquivoService;
    private final Long arquivoId;
    private Iterator<ExtratoFaturaCartaoDTO> extratoIterator;

    @Autowired
    public ImportacaoExtratoFaturaCartaoReader(ArquivoService arquivoService,
                                               @Value("#{jobParameters['arquivoId']}") Long arquivoId) {
        this.arquivoService = arquivoService;
        this.arquivoId = arquivoId;
    }

    @Override
    public ExtratoFaturaCartaoDTO read() throws Exception {
        if (extratoIterator == null) {
            this.initializeExtratoIterator();
        }
        return this.getNextExtrato();
    }

    private void initializeExtratoIterator() throws Exception {
        Arquivo arquivo = this.arquivoService.findByIdwithValidation(arquivoId);
        List<ExtratoFaturaCartaoDTO> extratos = this.parseExtratos(arquivo.getConteudo());
        extratoIterator = extratos.iterator();
    }

    private List<ExtratoFaturaCartaoDTO> parseExtratos(byte[] arquivoConteudo) throws Exception {
        ExtratoFaturaCartaoCSVParser extratoFaturaCartaoCSVParser = new ExtratoFaturaCartaoCSVParser();
        return extratoFaturaCartaoCSVParser.parse(new ByteArrayInputStream(arquivoConteudo));
    }

    private ExtratoFaturaCartaoDTO getNextExtrato() {
        if (extratoIterator != null && extratoIterator.hasNext()) {
            return extratoIterator.next();
        } else {
            return null;
        }
    }
}
