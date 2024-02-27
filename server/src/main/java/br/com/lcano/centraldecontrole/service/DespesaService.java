package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.DespesaDTO;
import br.com.lcano.centraldecontrole.domain.CategoriaDespesa;
import br.com.lcano.centraldecontrole.domain.Despesa;
import br.com.lcano.centraldecontrole.domain.Usuario;
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

    public Despesa criarDespesa(DespesaDTO data, Usuario usuario) {
        Despesa novaDespesa = new Despesa();
        novaDespesa.setUsuario(usuario);
        novaDespesa.setCategoria(categoriaDespesaService.getCategoriaDespesaById(data.getCategoria().getId()));
        novaDespesa.setDataLancamento(DateUtil.getDataAtual());
        novaDespesa.setDescricao(data.getDescricao());
        return novaDespesa;
    }

    public Page<DespesaDTO> listarDespesasDoUsuarioPorVencimento(Long idUsuario, Integer ano, Integer mes, Pageable pageable) {
        List<Despesa> despesas = despesaRepository.findByUsuarioId(idUsuario);

        despesas.forEach(despesa -> despesa.setParcelas(despesaParcelaService.listarParcelasPorVencimento(despesa, ano, mes)));

        List<Despesa> despesasComParcelas = despesas.stream()
                .filter(despesa -> !despesa.getParcelas().isEmpty())
                .toList();

        List<DespesaDTO> despesasDTO = despesasComParcelas.stream()
                .map(DespesaDTO::converterParaDTO)
                .collect(Collectors.toList());

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<DespesaDTO> paginaDespesasDTO;

        if (despesasDTO.size() < startItem) {
            paginaDespesasDTO = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, despesasDTO.size());
            paginaDespesasDTO = despesasDTO.subList(startItem, toIndex);
        }

        return new PageImpl<>(paginaDespesasDTO, pageable, despesasDTO.size());
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

        salvarDespesa(despesaExistente);
    }

    @Transactional
    public void excluirDespesa(Long idDespesa) {
        despesaRepository.delete(getDespesaById(idDespesa));
    }
}
