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
import br.com.lcano.centraldecontrole.service.fluxocaixa.ImportacaoExtratoContaJobStarter;
import br.com.lcano.centraldecontrole.service.fluxocaixa.ImportacaoExtratoFaturaCartaoJobStarter;
import br.com.lcano.centraldecontrole.util.DateUtil;
import br.com.lcano.centraldecontrole.util.FilterUtil;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@AllArgsConstructor
public class LancamentoService {
    private final LancamentoRepository lancamentoRepository;
    private final Map<TipoLancamentoEnum, LancamentoItemService<? extends LancamentoItemDTO>> lancamentoItemServices;
    private final UsuarioUtil usuarioUtil;
    private final DateUtil dateUtil;
    private final ArquivoService arquivoService;
    private final ImportacaoExtratoFaturaCartaoJobStarter importacaoExtratoFaturaCartaoJobStarter;
    private final ImportacaoExtratoContaJobStarter importacaoExtratoContaJobStarter;

    @Transactional
    public Long createLancamento(LancamentoDTO lancamentoDTO) {
        var lancamento = new Lancamento();
        lancamento.setDataLancamento(dateUtil.getDataAtual());
        lancamento.setDescricao(lancamentoDTO.getDescricao());
        lancamento.setTipo(lancamentoDTO.getTipo());
        lancamento.setUsuario(usuarioUtil.getUsuarioAutenticado());
        lancamento = lancamentoRepository.save(lancamento);

        getLancamentoItemService(lancamento.getTipo())
                .create(lancamentoDTO.getItemDTO(), lancamento);

        return lancamento.getId();
    }

    @Transactional
    public void updateLancamento(Long id, LancamentoDTO lancamentoDTO) {
        var lancamento = getLancamentoById(id);
        lancamento.setDescricao(lancamentoDTO.getDescricao());
        lancamentoRepository.save(lancamento);

        getLancamentoItemService(lancamento.getTipo())
                .update(id, lancamentoDTO.getItemDTO());
    }

    @Transactional
    public void deleteLancamento(Long id) {
        var lancamento = getLancamentoById(id);
        getLancamentoItemService(lancamento.getTipo()).delete(id);
        lancamentoRepository.delete(lancamento);
    }

    public LancamentoDTO getLancamentoDTO(Long id) {
        var lancamento = getLancamentoById(id);
        var itemDTO = getLancamentoItemService(lancamento.getTipo()).get(id);
        return LancamentoDTO.converterParaDTO(lancamento, itemDTO);
    }

    private LancamentoItemService<LancamentoItemDTO> getLancamentoItemService(TipoLancamentoEnum tipo) {
        return (LancamentoItemService<LancamentoItemDTO>) Optional.ofNullable(lancamentoItemServices.get(tipo))
                .orElseThrow(() -> new LancamentoException.LancamentoTipoNaoSuportado(tipo.getDescricao()));
    }

    private Lancamento getLancamentoById(Long id) {
        return lancamentoRepository.findById(id)
                .orElseThrow(() -> new LancamentoException.LancamentoNaoEncontradoById(id));
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

        return switch (field) {
            case "descricao" -> applyDescricaoSpecification(operator, value);
            case "tipo" -> applyTipoSpecification(operator, value);
            case "dataLancamento" -> applyDataSpecification(operator, value);
            default -> null;
        };
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

    public void importExtratoFaturaCartao(MultipartFile file, Date dataVencimento) throws Exception {
        Arquivo arquivo = arquivoService.uploadArquivo(file);

        try {
            importacaoExtratoFaturaCartaoJobStarter.startJob(arquivo.getId(), usuarioUtil.getUsuarioAutenticado().getId(), dataVencimento);
        } catch (Exception e) {
            arquivoService.deleteArquivoIfExists(arquivo.getId());
            throw new LancamentoException.ErroIniciarImportacaoExtrato(e);
        }
    }

    public void importExtratoConta(MultipartFile file) throws Exception {
        Arquivo arquivo = arquivoService.uploadArquivo(file);

        try {
            importacaoExtratoContaJobStarter.startJob(arquivo.getId(), usuarioUtil.getUsuarioAutenticado().getId());
        } catch (Exception e) {
            arquivoService.deleteArquivoIfExists(arquivo.getId());
            throw new LancamentoException.ErroIniciarImportacaoExtrato(e);
        }
    }
}
