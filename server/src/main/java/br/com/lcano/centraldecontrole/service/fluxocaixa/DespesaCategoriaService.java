package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.DespesaException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.DespesaCategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DespesaCategoriaService {
    @Autowired
    private final DespesaCategoriaRepository despesaCategoriaRepository;

    public DespesaCategoriaService(DespesaCategoriaRepository categoriaDespesaRepository) {
        this.despesaCategoriaRepository = categoriaDespesaRepository;
    }

    public List<CategoriaDTO> getTodasCategoriasDespesa() {
        return this.despesaCategoriaRepository.findAll().stream().map(CategoriaDTO::converterParaDTO).toList();
    }

    public DespesaCategoria getCategoriaDespesaById(Long id) {
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
}
