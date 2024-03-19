package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Despesa;
import br.com.lcano.centraldecontrole.domain.DespesaParcela;
import br.com.lcano.centraldecontrole.dto.DespesaParcelaDTO;
import br.com.lcano.centraldecontrole.repository.DespesaParcelaRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
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

    public void salvarParcelas(List<DespesaParcela> parcelas) {
        despesaParcelaRepository.saveAll(parcelas);
    }

    public DespesaParcela criarParcela(DespesaParcelaDTO data, Despesa despesa) {
        DespesaParcela novaParcela = new DespesaParcela();
        novaParcela.setDespesa(despesa);
        novaParcela.setNumero(data.getNumero());
        novaParcela.setDataVencimento(data.getDataVencimento());
        novaParcela.setValor(data.getValor());
        novaParcela.setPago(data.getPago());
        return novaParcela;
    }

    public List<DespesaParcela> criarParcelas(Despesa despesa, List<DespesaParcelaDTO> parcelasDTO) {
        return parcelasDTO.stream()
            .map(parcelaDTO -> criarParcela(parcelaDTO, despesa))
            .collect(Collectors.toList());
    }

    public List<DespesaParcela> listarParcelasPorVencimento(Despesa despesa, Integer ano, Integer mes) {
        return despesaParcelaRepository.findByDespesaAndDataVencimentoBetween(
            despesa,
            DateUtil.toDate(DateUtil.getPrimeiroDiaDoMes(ano, mes)),
            DateUtil.toDate(DateUtil.getUltimoDiaDoMes(ano, mes))
        );
    }

    public double calcularValorTotalParcelasMensal(Integer ano, Integer mes) {
        List<DespesaParcela> parcelas = despesaParcelaRepository.findByDataVencimentoBetween(
            DateUtil.toDate(DateUtil.getPrimeiroDiaDoMes(ano, mes)),
            DateUtil.toDate(DateUtil.getUltimoDiaDoMes(ano, mes)));

        return parcelas.stream().mapToDouble(DespesaParcela::getValor).sum();
    }
}
