package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.repository.LancamentoItemRepository;

public abstract class AbstractLancamentoItemService<T, D extends BaseDTO<T> & LancamentoItemDTO>
        extends AbstractGenericService<T, Long>
        implements LancamentoItemService<D> {

    @Override
    protected abstract LancamentoItemRepository<T> getRepository();

    @Override
    public void save(D itemDTO, Lancamento lancamento) {
        T entity = itemDTO.toEntity();
        setLancamento(entity, lancamento);
        getRepository().save(entity);
    }

    @Override
    public D getByLancamentoId(Long lancamentoId) {
        T entity = getRepository().findByLancamentoId(lancamentoId)
                .orElseThrow(() -> new RuntimeException("Entity not found for lancamento ID: " + lancamentoId));
        return (D) getDtoInstance().fromEntity(entity);
    }

    protected abstract void setLancamento(T entity, Lancamento lancamento);
}
