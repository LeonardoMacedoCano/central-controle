package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaDTO;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.DespesaException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.DespesaCategoriaRepository;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.DespesaRepository;
import br.com.lcano.centraldecontrole.service.LancamentoItemService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DespesaService implements LancamentoItemService<DespesaDTO> {

    private final DespesaRepository despesaRepository;
    private final DespesaCategoriaRepository despesaCategoriaRepository;
    private final DespesaCategoriaService despesaCategoriaService;

    @Override
    public void create(DespesaDTO itemDTO, Lancamento lancamento) {
        Despesa novaDespesa = new Despesa();
        novaDespesa.setLancamento(lancamento);
        novaDespesa.setCategoria(this.despesaCategoriaService.getCategoriaDespesaById(itemDTO.getCategoria().getId()));

        novaDespesa.setDataVencimento(itemDTO.getDataVencimento());
        novaDespesa.setValor(itemDTO.getValor());
        if (itemDTO.getFormaPagamento() != null) novaDespesa.setFormaPagamento((itemDTO.getFormaPagamento()));

        this.despesaRepository.save(novaDespesa);
    }

    @Transactional
    @Override
    public void update(Long id, DespesaDTO itemDTO) {
        Despesa despesa = this.getDespesaByLancamentoId(id);
        despesa.setCategoria(this.getCategoriaById(itemDTO.getCategoria().getId()));
        despesa.setDataVencimento(itemDTO.getDataVencimento());
        despesa.setValor(itemDTO.getValor());

        if (itemDTO.getFormaPagamento() != null) despesa.setFormaPagamento((itemDTO.getFormaPagamento()));
        else despesa.setFormaPagamento(null);

        this.despesaRepository.save(despesa);
    }

    @Override
    public void delete(Long id) {
        this.despesaRepository.delete(this.getDespesaByLancamentoId(id));
    }

    @Override
    public DespesaDTO get(Long id) {
        return DespesaDTO.converterParaDTO(getDespesaByLancamentoId(id));
    }

    @Override
    public TipoLancamentoEnum getTipoLancamento() {
        return TipoLancamentoEnum.DESPESA;
    }

    public DespesaCategoria getCategoriaById(Long id) {
        return despesaCategoriaRepository.findById(id)
            .orElseThrow(() -> new DespesaException.CategoriaNaoEncontradaById(id));
    }

    public Despesa getDespesaByLancamentoId(Long lancamentoId) {
        return despesaRepository.findByLancamentoId(lancamentoId)
                .orElseThrow(() -> new DespesaException.DespesaNaoEncontradaByLancamentoId(lancamentoId));
    }
}
