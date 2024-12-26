package br.com.lcano.centraldecontrole.repository.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DespesaCategoriaRepository extends JpaRepository<DespesaCategoria, Long> {
    Optional<DespesaCategoria> findByDescricao(String descricaoCategoria);
}
