package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Receita;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.ReceitaCategoria;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ReceitaDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.ReceitaException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.ReceitaCategoriaRepository;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.ReceitaRepository;
import br.com.lcano.centraldecontrole.service.LancamentoItemService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ReceitaService implements LancamentoItemService<ReceitaDTO>  {
    private final ReceitaRepository receitaRepository;
    private final ReceitaCategoriaRepository receitaCategoriaRepository;
    private final ReceitaCategoriaService receitaCategoriaService;

    @Override
    public void create(ReceitaDTO itemDTO, Lancamento lancamento) {
        Receita receita = new Receita();
        receita.setLancamento(lancamento);
        receita.setCategoria(this.receitaCategoriaService.getCategoriaById(itemDTO.getCategoria().getId()));
        receita.setValor(itemDTO.getValor());
        receita.setDataRecebimento(itemDTO.getDataRecebimento());
        this.receitaRepository.save(receita);
    }

    @Transactional
    @Override
    public void update(Long id, ReceitaDTO itemDTO) {
        Receita receita = this.getReceitaByLancamentoId(id);
        receita.setCategoria(this.getCategoriaById(itemDTO.getCategoria().getId()));
        receita.setValor(itemDTO.getValor());
        receita.setDataRecebimento(itemDTO.getDataRecebimento());
        this.receitaRepository.save(receita);
    }

    @Override
    public void delete(Long id) {
        this.receitaRepository.delete(this.getReceitaByLancamentoId(id));
    }

    @Override
    public ReceitaDTO get(Long id) {
        return ReceitaDTO.converterParaDTO(getReceitaByLancamentoId(id));
    }

    @Override
    public TipoLancamentoEnum getTipoLancamento() {
        return TipoLancamentoEnum.RECEITA;
    }

    public ReceitaCategoria getCategoriaById(Long id) {
        return receitaCategoriaRepository.findById(id)
                .orElseThrow(() -> new ReceitaException.CategoriaNaoEncontradaById(id));
    }

    public Receita getReceitaByLancamentoId(Long lancamentoId) {
        return receitaRepository.findByLancamentoId(lancamentoId)
                .orElseThrow(() -> new ReceitaException.ReceitaNaoEncontradaByLancamentoId(lancamentoId));
    }
}
