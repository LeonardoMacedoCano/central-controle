package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;

public interface LancamentoItemService<T extends LancamentoItemDTO> {
    void create(T itemDTO, Lancamento lancamento);
    void update(T itemDTO, Lancamento lancamento);
    void delete(Long id);
    T getByLancamentoId(Long lancamentoId);
    TipoLancamentoEnum getTipoLancamento();
}
