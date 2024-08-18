package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.FormaPagamento;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.FormaPagamentoDTO;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.DespesaException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoService {
    @Autowired
    private final FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    public List<FormaPagamentoDTO> getTodasFormaPagamento() {
        return formaPagamentoRepository.findAll().stream().map(FormaPagamentoDTO::converterParaDTO).toList();
    }

    public FormaPagamento getFormaPagamentoById(Long id) {
        return formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new DespesaException.FormaPagamentoNaoEncontradaById(id));
    }
}
