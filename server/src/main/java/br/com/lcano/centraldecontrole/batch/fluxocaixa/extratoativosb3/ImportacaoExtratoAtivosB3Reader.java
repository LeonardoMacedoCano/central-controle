package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoativosb3;

import br.com.lcano.centraldecontrole.domain.Arquivo;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoAtivosB3DTO;
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
public class ImportacaoExtratoAtivosB3Reader implements ItemReader<ExtratoAtivosB3DTO> {

    private final ArquivoService arquivoService;
    private final Long arquivoId;
    private Iterator<ExtratoAtivosB3DTO> extratoIterator;

    @Autowired
    public ImportacaoExtratoAtivosB3Reader(ArquivoService arquivoService,
                                           @Value("#{jobParameters['arquivoId']}") Long arquivoId) {
        this.arquivoService = arquivoService;
        this.arquivoId = arquivoId;
    }

    @Override
    public ExtratoAtivosB3DTO read() throws Exception {
        if (extratoIterator == null) {
            this.initializeExtratoIterator();
        }
        return this.getNextExtrato();
    }

    private void initializeExtratoIterator() throws Exception {
        Arquivo arquivo = this.arquivoService.findByIdwithValidation(arquivoId);
        List<ExtratoAtivosB3DTO> extratos = this.parseExtratos(arquivo.getConteudo());
        extratoIterator = extratos.iterator();
    }

    private List<ExtratoAtivosB3DTO> parseExtratos(byte[] arquivoConteudo) throws Exception {
        ExtratoAtivoB3CSVParser extratoAtivoB3XLSXParser = new ExtratoAtivoB3CSVParser();
        return extratoAtivoB3XLSXParser.parse(new ByteArrayInputStream(arquivoConteudo));
    }

    private ExtratoAtivosB3DTO getNextExtrato() {
        if (extratoIterator != null && extratoIterator.hasNext()) {
            return extratoIterator.next();
        } else {
            return null;
        }
    }
}