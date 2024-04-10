package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.DespesaParcela;
import br.com.lcano.centraldecontrole.dto.DespesaDTO;
import br.com.lcano.centraldecontrole.domain.CategoriaDespesa;
import br.com.lcano.centraldecontrole.domain.Despesa;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.dto.DespesaResumoMensalDTO;
import br.com.lcano.centraldecontrole.exception.DespesaException;
import br.com.lcano.centraldecontrole.repository.CategoriaDespesaRepository;
import br.com.lcano.centraldecontrole.repository.DespesaRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DespesaService {
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

    public void salvarDespesa(Despesa despesa) {
        despesaRepository.save(despesa);
    }

    public CategoriaDespesa getCategoriaById(Long id) {
        return categoriaDespesaRepository.findById(id)
            .orElseThrow(() -> new DespesaException.CategoriaDespesaNaoEncontradaById(id));
    }

    public Despesa getDespesaById(Long id) {
        return despesaRepository.findById(id)
            .orElseThrow(() -> new DespesaException.DespesaNaoEncontradaById(id));
    }

    public Despesa getDespesaByIdWithParcelas(Long id) {
        return despesaRepository.findByIdWithParcelas(id)
            .orElseThrow(() -> new DespesaException.DespesaNaoEncontradaById(id));
    }

    public Despesa criarDespesa(DespesaDTO data, Usuario usuario) {
        Despesa novaDespesa = new Despesa();
        novaDespesa.setUsuario(usuario);
        novaDespesa.setCategoria(categoriaDespesaService.getCategoriaDespesaById(data.getCategoria().getId()));
        novaDespesa.setDataLancamento(DateUtil.getDataAtual());
        novaDespesa.setDescricao(data.getDescricao());
        return novaDespesa;
    }

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

    @Transactional
    public void gerarDespesa(DespesaDTO data, Usuario usuario) {
        Despesa novaDespesa = criarDespesa(data, usuario);
        novaDespesa.getParcelas().addAll(despesaParcelaService.criarParcelas(novaDespesa, data.getParcelasDTO()));
        salvarDespesa(novaDespesa);
        despesaParcelaService.salvarParcelas(novaDespesa.getParcelas());
    }

    @Transactional
    public void editarDespesa(Long idDespesa, DespesaDTO data, Usuario usuario) {
        Despesa despesaExistente = getDespesaById(idDespesa);
        despesaExistente.setCategoria(getCategoriaById(data.getCategoria().getId()));
        despesaExistente.setUsuario(usuario);
        despesaExistente.setDescricao(data.getDescricao());

        List<DespesaParcela> parcelasAtualizadas = despesaParcelaService.atualizarParcelas(despesaExistente, data.getParcelasDTO());
        List<DespesaParcela> novasParcelas = new ArrayList<>(parcelasAtualizadas);

        despesaExistente.setParcelas(novasParcelas);

        salvarDespesa(despesaExistente);
        despesaParcelaService.salvarParcelas(despesaExistente.getParcelas());
    }

    @Transactional
    public void excluirDespesa(Long idDespesa) {
        despesaRepository.delete(getDespesaById(idDespesa));
    }
}
