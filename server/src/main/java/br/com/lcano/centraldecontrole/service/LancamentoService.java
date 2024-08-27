package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.LancamentoDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.exception.LancamentoException;
import br.com.lcano.centraldecontrole.repository.LancamentoRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LancamentoService {
    @Autowired
    LancamentoRepository lancamentoRepository;
    @Resource(name = "lancamentoItemServicesMap")
    Map<TipoLancamentoEnum, LancamentoItemService<? extends LancamentoItemDTO>> lancamentoItemServices;
    @Autowired
    UsuarioUtil usuarioUtil;
    @Autowired
    DateUtil dateUtil;

    @Transactional
    public void createLancamento(LancamentoDTO lancamentoDTO) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDataLancamento(dateUtil.getDataAtual());
        lancamento.setDescricao(lancamentoDTO.getDescricao());
        lancamento.setTipo(lancamentoDTO.getTipo());
        lancamento.setUsuario(usuarioUtil.getUsuarioAutenticado());
        lancamento = lancamentoRepository.save(lancamento);

        LancamentoItemService lancamentoItemService = getLancamentoItemService(lancamento.getTipo());
        lancamentoItemService.create(lancamentoDTO.getItemDTO(), lancamento);
    }

    @Transactional
    public void updateLancamento(Long id, LancamentoDTO lancamentoDTO) {
        Lancamento lancamento = getLancamentoById(id);
        lancamento.setDescricao(lancamentoDTO.getDescricao());
        lancamento = lancamentoRepository.save(lancamento);

        LancamentoItemService lancamentoItemService = getLancamentoItemService(lancamento.getTipo());
        lancamentoItemService.update(id, lancamentoDTO.getItemDTO());
    }

    @Transactional
    public void deleteLancamento(Long id) {
        Lancamento lancamento = getLancamentoById(id);
        LancamentoItemService lancamentoItemService = getLancamentoItemService(lancamento.getTipo());

        lancamentoItemService.delete(id);
        lancamentoRepository.delete(lancamento);
    }

    public Page<LancamentoDTO> getLancamentos(Pageable pageable, String descricao, TipoLancamentoEnum tipo, Date dataInicio, Date dataFim) {
        List<TipoLancamentoEnum> tipos = tipo != null ? List.of(tipo) : List.of(TipoLancamentoEnum.DESPESA, TipoLancamentoEnum.RECEITA, TipoLancamentoEnum.PASSIVO, TipoLancamentoEnum.ATIVO);

        return lancamentoRepository.search(usuarioUtil.getUsuarioAutenticado().getId(), tipos, descricao, dataInicio, dataFim, pageable)
                .map(LancamentoDTO::converterParaDTO);
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
