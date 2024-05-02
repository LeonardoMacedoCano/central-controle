package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Despesa;
import br.com.lcano.centraldecontrole.domain.DespesaParcela;
import br.com.lcano.centraldecontrole.dto.DespesaParcelaDTO;
import br.com.lcano.centraldecontrole.repository.DespesaParcelaRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DespesaParcelaService {
    private final DespesaParcelaRepository despesaParcelaRepository;
    private final FormaPagamentoService formaPagamentoService;

    @Autowired
    public DespesaParcelaService(DespesaParcelaRepository despesaParcelaRepository, FormaPagamentoService formaPagamentoService) {
        this.despesaParcelaRepository = despesaParcelaRepository;
        this.formaPagamentoService = formaPagamentoService;
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

        if (data.getFormaPagamento() != null && data.getFormaPagamento().getId() != null) {
            novaParcela.setFormaPagamento(formaPagamentoService.getFormaPagamentoById(data.getFormaPagamento().getId()));
        }

        return novaParcela;
    }

    public List<DespesaParcela> criarParcelas(Despesa despesa, List<DespesaParcelaDTO> parcelasDTO) {
        return parcelasDTO.stream()
            .map(parcelaDTO -> criarParcela(parcelaDTO, despesa))
            .collect(Collectors.toList());
    }

    public void editarParcela(DespesaParcela parcelaExistente, DespesaParcelaDTO parcelaDTO) {
        parcelaExistente.setDataVencimento(parcelaDTO.getDataVencimento());
        parcelaExistente.setValor(parcelaDTO.getValor());
        parcelaExistente.setPago(parcelaDTO.getPago());

        if (parcelaDTO.getFormaPagamento() != null && parcelaDTO.getFormaPagamento().getId() != null) {
            parcelaExistente.setFormaPagamento(formaPagamentoService.getFormaPagamentoById(parcelaDTO.getFormaPagamento().getId()));
        }
    }
    public List<DespesaParcela> atualizarParcelas(Despesa despesaExistente, List<DespesaParcelaDTO> parcelasDTO) {
        List<DespesaParcela> parcelasAtualizadas = new ArrayList<>();

        for (DespesaParcelaDTO parcelaDTO : parcelasDTO) {
            if (parcelaDTO.getId() != null && parcelaDTO.getId() > 0) {
                DespesaParcela parcelaExistente = despesaExistente.getParcelas().stream()
                        .filter(p -> p.getId().equals(parcelaDTO.getId()))
                        .findFirst()
                        .orElse(null);

                if (parcelaExistente != null) {
                    editarParcela(parcelaExistente, parcelaDTO);
                    parcelasAtualizadas.add(parcelaExistente);
                }
            } else {
                DespesaParcela novaParcela = criarParcela(parcelaDTO, despesaExistente);
                parcelasAtualizadas.add(novaParcela);
            }
        }

        return parcelasAtualizadas;
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
