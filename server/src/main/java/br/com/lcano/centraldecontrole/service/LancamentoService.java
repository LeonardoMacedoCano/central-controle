package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Arquivo;
import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.repository.LancamentoSpecifications;
import br.com.lcano.centraldecontrole.dto.FilterDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.enums.OperatorFilterEnum;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.exception.LancamentoException;
import br.com.lcano.centraldecontrole.repository.LancamentoRepository;
import br.com.lcano.centraldecontrole.service.fluxocaixa.ImportacaoExtratoFaturaCartaoJobStarter;
import br.com.lcano.centraldecontrole.util.DateUtil;
import br.com.lcano.centraldecontrole.util.FilterUtil;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
    @Autowired
    ArquivoService arquivoService;
    @Autowired
    ImportacaoExtratoFaturaCartaoJobStarter importacaoExtratoFaturaCartaoJobStarter;

    @Transactional
    public Long createLancamento(LancamentoDTO lancamentoDTO) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDataLancamento(dateUtil.getDataAtual());
        lancamento.setDescricao(lancamentoDTO.getDescricao());
        lancamento.setTipo(lancamentoDTO.getTipo());
        lancamento.setUsuario(usuarioUtil.getUsuarioAutenticado());
        lancamento = lancamentoRepository.save(lancamento);

        LancamentoItemService lancamentoItemService = getLancamentoItemService(lancamento.getTipo());
        lancamentoItemService.create(lancamentoDTO.getItemDTO(), lancamento);

        return lancamento.getId();
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

    public Page<LancamentoDTO> getLancamentos(Pageable pageable, List<FilterDTO> filterDTOs) {
        Specification<Lancamento> combinedSpecification = FilterUtil.buildSpecificationsFromDTO(filterDTOs, this::applyFieldSpecification);
        return lancamentoRepository.findAll(combinedSpecification, pageable)
                .map(LancamentoDTO::converterParaDTO);
    }

    private Specification<Lancamento> applyFieldSpecification(FilterDTO filterDTO) {
        String field = filterDTO.getField();
        String operator = filterDTO.getOperator();
        String value = filterDTO.getValue();

        switch (field) {
            case "descricao":
                return applyDescricaoSpecification(operator, value);
            case "tipo":
                return applyTipoSpecification(operator, value);
            case "dataLancamento":
                return applyDataSpecification(operator, value);
            default:
                return null;
        }
    }

    private Specification<Lancamento> applyDescricaoSpecification(String operator, String value) {
        OperatorFilterEnum filterEnum = OperatorFilterEnum.fromSymbol(operator);

        return switch (filterEnum) {
            case IGUAL -> LancamentoSpecifications.hasDescricao(value);
            case DIFERENTE -> LancamentoSpecifications.hasDescricaoNot(value);
            case CONTEM -> LancamentoSpecifications.hasDescricaoLike(value);
            default -> null;
        };
    }

    private Specification<Lancamento> applyTipoSpecification(String operator, String value) {
        OperatorFilterEnum filterEnum = OperatorFilterEnum.fromSymbol(operator);
        TipoLancamentoEnum tipo = TipoLancamentoEnum.valueOf(value);

        return switch (filterEnum) {
            case IGUAL -> LancamentoSpecifications.hasTipos(List.of(tipo));
            case DIFERENTE -> LancamentoSpecifications.hasTiposNot(List.of(tipo));
            default -> null;
        };
    }

    private Specification<Lancamento> applyDataSpecification(String operator, String value) {
        OperatorFilterEnum filterEnum = OperatorFilterEnum.fromSymbol(operator);
        Date date = DateUtil.parseDate(value);
        if (date == null) return null;

        return switch (filterEnum) {
            case IGUAL -> LancamentoSpecifications.hasDataLancamentoEqual(date);
            case DIFERENTE -> LancamentoSpecifications.hasDataLancamentoNotEqual(date);
            case MAIOR -> LancamentoSpecifications.hasDataLancamentoAfter(date);
            case MENOR -> LancamentoSpecifications.hasDataLancamentoBefore(date);
            case MAIOR_OU_IGUAL -> LancamentoSpecifications.hasDataLancamentoGreaterOrEqual(date);
            case MENOR_OU_IGUAL -> LancamentoSpecifications.hasDataLancamentoLessOrEqual(date);
            default -> null;
        };
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

    public void importExtratoFaturaCartao(MultipartFile file, Date dataVencimento) throws Exception {
        Arquivo arquivo = arquivoService.uploadArquivo(file);
        importacaoExtratoFaturaCartaoJobStarter.startJob(arquivo.getId(), usuarioUtil.getUsuarioAutenticado().getId(), dataVencimento);
    }
}
