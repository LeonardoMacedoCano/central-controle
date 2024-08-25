package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaFormaPagamento;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaFormaPagamentoDTO;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.DespesaException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.DespesaFormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespesaFormaPagamentoService {
    @Autowired
    private final DespesaFormaPagamentoRepository despesaFormaPagamentoRepository;

    public DespesaFormaPagamentoService(DespesaFormaPagamentoRepository formaPagamentoRepository) {
        this.despesaFormaPagamentoRepository = formaPagamentoRepository;
    }

    public List<DespesaFormaPagamentoDTO> getTodasFormaPagamento() {
        return despesaFormaPagamentoRepository.findAll().stream().map(DespesaFormaPagamentoDTO::converterParaDTO).toList();
    }

    public DespesaFormaPagamento getFormaPagamentoById(Long id) {
        return despesaFormaPagamentoRepository.findById(id)
                .orElseThrow(() -> new DespesaException.FormaPagamentoNaoEncontradaById(id));
    }
}
