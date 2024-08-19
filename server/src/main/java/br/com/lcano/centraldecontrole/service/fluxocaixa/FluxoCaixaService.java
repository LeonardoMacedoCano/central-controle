package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.LancamentoDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.exception.LancamentoException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.LancamentoRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class FluxoCaixaService {

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Resource(name = "lancamentoItemServicesMap")
    Map<TipoLancamentoEnum, LancamentoItemService<? extends LancamentoItemDTO>> lancamentoItemServices;

    @Autowired
    UsuarioUtil usuarioUtil;

    public void createLancamento(LancamentoDTO lancamentoDTO) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDataLancamento(DateUtil.getDataAtual());
        lancamento.setDescricao(lancamentoDTO.getDescricao());
        lancamento.setTipo(lancamentoDTO.getTipo());
        lancamento.setUsuario(usuarioUtil.getUsuarioAutenticado());
        lancamento = lancamentoRepository.save(lancamento);

        LancamentoItemService lancamentoItemService = getLancamentoItemService(lancamento.getTipo());
        lancamentoItemService.create(lancamentoDTO.getItemDTO(), lancamento);
    }

    public void updateLancamento(Long id, LancamentoDTO lancamentoDTO) {
        Lancamento lancamento = getLancamentoById(id);
        lancamento.setDescricao(lancamentoDTO.getDescricao());
        lancamento = lancamentoRepository.save(lancamento);

        LancamentoItemService lancamentoItemService = getLancamentoItemService(lancamento.getTipo());
        lancamentoItemService.update(id, lancamentoDTO.getItemDTO());
    }


    public void deleteLancamento(Long id) {
        Lancamento lancamento = getLancamentoById(id);
        LancamentoItemService lancamentoItemService = getLancamentoItemService(lancamento.getTipo());

        lancamentoItemService.delete(id);
        lancamentoRepository.delete(lancamento);
    }

    public LancamentoDTO getLancamentoDTO(Long id) {
        Lancamento lancamento = getLancamentoById(id);
        LancamentoItemService lancamentoItemService = getLancamentoItemService(lancamento.getTipo());
        LancamentoItemDTO itemDTO = lancamentoItemService.get(id);

        return LancamentoDTO.converterParaDTO(lancamento, itemDTO);
    }

    private Lancamento getLancamentoById(Long id) {
        return lancamentoRepository.findById(id)
                .orElseThrow(() -> new LancamentoException.LancamentoNaoEncontradoById(id));
    }

    private LancamentoItemService<? extends LancamentoItemDTO> getLancamentoItemService(TipoLancamentoEnum tipo) {
        return Optional.ofNullable(lancamentoItemServices.get(tipo))
                .orElseThrow(() -> new LancamentoException.LancamentoTipoNaoSuportado(tipo.getDescricao()));
    }
}
