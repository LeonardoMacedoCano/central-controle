package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.DespesaException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.DespesaCategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DespesaCategoriaService {
    @Autowired
    private final DespesaCategoriaRepository despesaCategoriaRepository;

    public Page<CategoriaDTO> getTodasCategorias(Pageable pageable) {
        Pageable pageableComOrdenacao = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Order.asc("id")));

        return this.despesaCategoriaRepository.findAll(pageableComOrdenacao)
                .map(CategoriaDTO::converterParaDTO);
    }

    public DespesaCategoria getCategoriaById(Long id) {
        return this.despesaCategoriaRepository.findById(id)
            .orElseThrow(() -> new DespesaException.CategoriaNaoEncontradaById(id));
    }

    public CategoriaDTO findOrCreateCategoria(String descricaoCategoria) {
        DespesaCategoria categoria = despesaCategoriaRepository.findByDescricao(descricaoCategoria);

        if (categoria == null) {
            categoria = new DespesaCategoria(descricaoCategoria);
            despesaCategoriaRepository.save(categoria);
        }

        return CategoriaDTO.converterParaDTO(categoria);
    }

    public Long saveCategoria(CategoriaDTO categoriaDTO) {
        DespesaCategoria despesaCategoria = new DespesaCategoria();

        despesaCategoria.setId(categoriaDTO.getId());
        despesaCategoria.setDescricao(categoriaDTO.getDescricao());

        return despesaCategoriaRepository.save(despesaCategoria).getId();
    }

    public void deleteCategoria(Long id) {
        despesaCategoriaRepository.delete(this.getCategoriaById(id));
    }
}
