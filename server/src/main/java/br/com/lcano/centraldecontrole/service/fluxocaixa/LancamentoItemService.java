package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;

public interface LancamentoItemService<T extends LancamentoItemDTO> {
    void create(T itemDTO, Lancamento lancamento);
    void update(Long id, T itemDTO);
    void delete(Long id);
    T get(Long id);
    TipoLancamentoEnum getTipoLancamento();
}
