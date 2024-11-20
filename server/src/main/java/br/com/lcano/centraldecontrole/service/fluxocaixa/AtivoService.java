package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Ativo;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.AtivoCategoria;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.AtivoDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.AtivoException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.AtivoCategoriaRepository;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.AtivoRepository;
import br.com.lcano.centraldecontrole.service.LancamentoItemService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AtivoService implements LancamentoItemService<AtivoDTO> {
    private final AtivoRepository ativoRepository;
    private final AtivoCategoriaRepository ativoCategoriaRepository;
    private final AtivoCategoriaService ativoCategoriaService;

    @Override
    public void create(AtivoDTO itemDTO, Lancamento lancamento) {
        Ativo ativo = new Ativo();
        ativo.setLancamento(lancamento);
        ativo.setCategoria(this.ativoCategoriaService.getCategoriaById(itemDTO.getCategoria().getId()));
        ativo.setTicker(itemDTO.getTicker());
        ativo.setOperacao(itemDTO.getOperacao());
        ativo.setQuantidade(itemDTO.getQuantidade());
        ativo.setPrecoUnitario(itemDTO.getPrecoUnitario());
        ativo.setDataMovimento(itemDTO.getDataMovimento());
        this.ativoRepository.save(ativo);
    }

    @Transactional
    @Override
    public void update(Long id, AtivoDTO itemDTO) {
        Ativo ativo = this.getAtivoByLancamentoId(id);
        ativo.setCategoria(this.getCategoriaById(itemDTO.getCategoria().getId()));
        ativo.setTicker(itemDTO.getTicker());
        ativo.setOperacao(itemDTO.getOperacao());
        ativo.setQuantidade(itemDTO.getQuantidade());
        ativo.setPrecoUnitario(itemDTO.getPrecoUnitario());
        ativo.setDataMovimento(itemDTO.getDataMovimento());
        this.ativoRepository.save(ativo);
    }

    @Override
    public void delete(Long id) {
        this.ativoRepository.delete(this.getAtivoByLancamentoId(id));
    }

    @Override
    public AtivoDTO get(Long id) {
        return AtivoDTO.converterParaDTO(getAtivoByLancamentoId(id));
    }

    @Override
    public TipoLancamentoEnum getTipoLancamento() {
        return TipoLancamentoEnum.ATIVO;
    }

    public AtivoCategoria getCategoriaById(Long id) {
        return ativoCategoriaRepository.findById(id)
                .orElseThrow(() -> new AtivoException.CategoriaNaoEncontradaById(id));
    }

    public Ativo getAtivoByLancamentoId(Long lancamentoId) {
        return ativoRepository.findByLancamentoId(lancamentoId)
                .orElseThrow(() -> new AtivoException.AtivoNaoEncontradaByLancamentoId(lancamentoId));
    }
}
