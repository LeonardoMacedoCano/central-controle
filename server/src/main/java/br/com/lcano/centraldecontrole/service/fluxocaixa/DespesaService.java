package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaParcela;
import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaDTO;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.CategoriaDespesa;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.DespesaException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.CategoriaDespesaRepository;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.DespesaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class DespesaService implements LancamentoItemService<DespesaDTO> {

    private final DespesaRepository despesaRepository;
    private final CategoriaDespesaRepository categoriaDespesaRepository;
    private final CategoriaDespesaService categoriaDespesaService;
    private final DespesaParcelaService despesaParcelaService;

    @Autowired
    public DespesaService(DespesaRepository despesaRepository,
                          CategoriaDespesaRepository categoriaDespesaRepository,
                          CategoriaDespesaService categoriaDespesaService,
                          DespesaParcelaService despesaParcelaService) {
        this.despesaRepository = despesaRepository;
        this.categoriaDespesaRepository = categoriaDespesaRepository;
        this.categoriaDespesaService = categoriaDespesaService;
        this.despesaParcelaService = despesaParcelaService;
    }

    @Override
    public void create(DespesaDTO itemDTO, Lancamento lancamento) {
        Despesa novaDespesa = new Despesa();
        novaDespesa.setLancamento(lancamento);
        novaDespesa.setCategoria(this.categoriaDespesaService.getCategoriaDespesaById(itemDTO.getCategoria().getId()));
        novaDespesa.getParcelas().addAll(this.despesaParcelaService.criarParcelas(novaDespesa, itemDTO.getParcelasDTO()));
        this.despesaRepository.save(novaDespesa);
        this.despesaParcelaService.salvarParcelas(novaDespesa.getParcelas());
    }

    @Transactional
    @Override
    public void update(Long id, DespesaDTO itemDTO) {
        Despesa despesa = this.getDespesaByLancamentoIdWithParcelas(id);
        despesa.setCategoria(this.getCategoriaById(itemDTO.getCategoria().getId()));

        List<DespesaParcela> parcelasAtualizadas = this.despesaParcelaService.updateParcelas(despesa, itemDTO.getParcelasDTO());

        despesa.getParcelas().clear();
        despesa.getParcelas().addAll(parcelasAtualizadas);

        this.despesaRepository.save(despesa);
        this.despesaParcelaService.salvarParcelas(despesa.getParcelas());
    }

    @Override
    public void delete(Long id) {
        this.despesaRepository.delete(this.getDespesaById(id));
    }

    @Override
    public DespesaDTO get(Long id) {
        return DespesaDTO.converterParaDTO(getDespesaById(id));
    }

    @Override
    public TipoLancamentoEnum getTipoLancamento() {
        return TipoLancamentoEnum.DESPESA;
    }

    private Despesa getDespesaById(Long id) {
        return despesaRepository.findById(id)
                .orElseThrow(() -> new DespesaException.DespesaNaoEncontradaById(id));
    }

    private CategoriaDespesa getCategoriaById(Long id) {
        return categoriaDespesaRepository.findById(id)
            .orElseThrow(() -> new DespesaException.CategoriaDespesaNaoEncontradaById(id));
    }

    public Despesa getDespesaByIdWithParcelas(Long id) {
        return despesaRepository.findByIdWithParcelas(id)
            .orElseThrow(() -> new DespesaException.DespesaNaoEncontradaById(id));
    }

    public Despesa getDespesaByLancamentoIdWithParcelas(Long lancamentoId) {
        return despesaRepository.findByLancamentoIdWithParcelas(lancamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Despesa não encontrada para o lançamento"));
    }

/*
    public Page<DespesaResumoMensalDTO> listarDespesaResumoMensalDTO(Long idUsuario, Integer ano, Integer mes, Pageable pageable) {
        List<Despesa> despesas = despesaRepository.findByUsuarioId(idUsuario);

        despesas.forEach(despesa -> despesa.setParcelas(despesaParcelaService.listarParcelasPorVencimento(despesa, ano, mes)));

        List<Despesa> despesasComParcelas = despesas.stream()
                .filter(despesa -> !despesa.getParcelas().isEmpty())
                .toList();

        List<DespesaResumoMensalDTO> despesaResumoMensalDTOList = despesasComParcelas.stream()
                .map(DespesaResumoMensalDTO::converterParaDTO)
                .collect(Collectors.toList());

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<DespesaResumoMensalDTO> paginaDespesasResumoMensalDTO;

        if (despesaResumoMensalDTOList.size() < startItem) {
            paginaDespesasResumoMensalDTO = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, despesaResumoMensalDTOList.size());
            paginaDespesasResumoMensalDTO = despesaResumoMensalDTOList.subList(startItem, toIndex);
        }

        return new PageImpl<>(paginaDespesasResumoMensalDTO, pageable, despesaResumoMensalDTOList.size());
    }

 */
}
