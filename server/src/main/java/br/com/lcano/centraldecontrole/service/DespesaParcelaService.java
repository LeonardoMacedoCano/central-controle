package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Despesa;
import br.com.lcano.centraldecontrole.domain.DespesaParcela;
import br.com.lcano.centraldecontrole.dto.NovaDespesaParcelaDTO;
import br.com.lcano.centraldecontrole.repository.DespesaParcelaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DespesaParcelaService {
    private final DespesaParcelaRepository despesaParcelaRepository;

    @Autowired
    public DespesaParcelaService(DespesaParcelaRepository despesaParcelaRepository) {
        this.despesaParcelaRepository = despesaParcelaRepository;
    }

    public void salvarParcela(DespesaParcela parcela) {
        despesaParcelaRepository.save(parcela);
    }

    public void salvarParcelas(List<DespesaParcela> parcelas) {
        despesaParcelaRepository.saveAll(parcelas);
    }

    public DespesaParcela criarParcela(NovaDespesaParcelaDTO data, Despesa despesa) {
        DespesaParcela novaParcela = new DespesaParcela();
        novaParcela.setDespesa(despesa);
        novaParcela.setNumero(data.getNumero());
        novaParcela.setDataVencimento(data.getDataVencimento());
        novaParcela.setValor(data.getValor());
        novaParcela.setPago(data.getPago());
        return novaParcela;
    }

    public List<DespesaParcela> criarParcelas(Despesa despesa, List<NovaDespesaParcelaDTO> parcelasDTO) {
        return parcelasDTO.stream()
            .map(parcelaDTO -> criarParcela(parcelaDTO, despesa))
            .collect(Collectors.toList());
    }

}
