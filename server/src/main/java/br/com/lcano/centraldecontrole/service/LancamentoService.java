package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Arquivo;
import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import br.com.lcano.centraldecontrole.repository.LancamentoSpecifications;
import br.com.lcano.centraldecontrole.dto.FilterDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.enums.OperatorFilterEnum;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.exception.LancamentoException;
import br.com.lcano.centraldecontrole.repository.LancamentoRepository;
import br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoconta.ImportacaoExtratoContaJobStarter;
import br.com.lcano.centraldecontrole.batch.fluxocaixa.extratofaturacartao.ImportacaoExtratoFaturaCartaoJobStarter;
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
public class LancamentoService extends AbstractGenericService<Lancamento, Long> {
    private final LancamentoRepository lancamentoRepository;
    private final Map<TipoLancamentoEnum, LancamentoItemService<? extends LancamentoItemDTO>> lancamentoItemServices;
    private final UsuarioUtil usuarioUtil;
    private final ArquivoService arquivoService;
    private final ImportacaoExtratoFaturaCartaoJobStarter importacaoExtratoFaturaCartaoJobStarter;
    private final ImportacaoExtratoContaJobStarter importacaoExtratoContaJobStarter;

    @Override
    protected LancamentoRepository getRepository() {
        return lancamentoRepository;
    }

    @Override
    protected LancamentoDTO getDtoInstance() {
        return new LancamentoDTO();
    }

    @Override
    public LancamentoDTO findByIdAsDto(Long id) {
        Lancamento lancamento = this.findById(id);
        LancamentoItemDTO itemDTO = getLancamentoItemService(lancamento.getTipo()).getByLancamentoId(id);
        return new LancamentoDTO().fromEntityWithItem(lancamento, itemDTO);
    }

    @Override
    @Transactional
    public <D extends BaseDTO<Lancamento>> D saveAsDto(D dto) {
        Lancamento lancamento = dto.toEntity();
        lancamento.setUsuario(usuarioUtil.getUsuarioAutenticado());
        lancamento = this.save(lancamento);

        getLancamentoItemService(lancamento.getTipo())
                .save(((LancamentoDTO) dto).getItemDTO(), lancamento);

        return (D) new LancamentoDTO().fromEntity(lancamento);
    }

    public Page<LancamentoDTO> search(Pageable pageable, List<FilterDTO> filterDTOs) {
        Specification<Lancamento> combinedSpecification = FilterUtil.buildSpecificationsFromDTO(filterDTOs, this::applyFieldSpecification);
        return lancamentoRepository.findAll(combinedSpecification, pageable)
                .map(lancamento -> {
                    var itemDTO = getLancamentoItemService(lancamento.getTipo()).getByLancamentoId(lancamento.getId());
                    return new LancamentoDTO().fromEntityWithItem(lancamento, itemDTO);
                });
    }

    private LancamentoItemService<LancamentoItemDTO> getLancamentoItemService(TipoLancamentoEnum tipo) {
        return (LancamentoItemService<LancamentoItemDTO>) Optional.ofNullable(lancamentoItemServices.get(tipo))
                .orElseThrow(() -> new LancamentoException.LancamentoTipoNaoSuportado(tipo.getDescricao()));
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