package br.com.lcano.centraldecontrole.repository.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaCategoriaRepository extends JpaRepository<DespesaCategoria, Long> {
    DespesaCategoria findByDescricao(String descricao);

    List<DespesaCategoria> findAllByOrderByIdAsc();
}
