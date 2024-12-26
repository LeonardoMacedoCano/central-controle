package br.com.lcano.centraldecontrole.repository.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.Receita;
import br.com.lcano.centraldecontrole.repository.LancamentoItemRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceitaRepository extends LancamentoItemRepository<Receita> {
    @Query("SELECT r FROM Receita r WHERE r.lancamento.id = :lancamentoId")
    Optional<Receita> findByLancamentoId(@Param("lancamentoId") Long lancamentoId);
}
