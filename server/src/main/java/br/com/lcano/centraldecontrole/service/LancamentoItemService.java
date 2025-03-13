package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamento;

public interface LancamentoItemService<T extends LancamentoItemDTO> {
    void save(T itemDTO, Lancamento lancamento);
    T getByLancamentoId(Long lancamentoId);
    TipoLancamento getTipoLancamento();
}
