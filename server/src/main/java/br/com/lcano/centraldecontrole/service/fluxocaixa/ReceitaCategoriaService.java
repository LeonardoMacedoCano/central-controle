package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.ReceitaCategoria;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.ReceitaException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.ReceitaCategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReceitaCategoriaService {
    @Autowired
    private final ReceitaCategoriaRepository receitaCategoriaRepository;

    public List<CategoriaDTO> getTodasCategorias() {
        return this.receitaCategoriaRepository.findAll().stream().map(CategoriaDTO::converterParaDTO).toList();
    }

    public ReceitaCategoria getCategoriaById(Long id) {
        return this.receitaCategoriaRepository.findById(id)
                .orElseThrow(() -> new ReceitaException.CategoriaNaoEncontradaById(id));
    }
}
