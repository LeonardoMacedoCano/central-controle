package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.AtivoCategoria;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.AtivoException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.AtivoCategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AtivoCategoriaService {
    @Autowired
    private final AtivoCategoriaRepository ativoCategoriaRepository;

    public List<CategoriaDTO> getTodasCategorias() {
        return this.ativoCategoriaRepository.findAll().stream().map(CategoriaDTO::converterParaDTO).toList();
    }

    public AtivoCategoria getCategoriaById(Long id) {
        return this.ativoCategoriaRepository.findById(id)
                .orElseThrow(() -> new AtivoException.CategoriaNaoEncontradaById(id));
    }
}
