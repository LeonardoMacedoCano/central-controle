package br.com.lcano.centraldecontrole.repository.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.repository.LancamentoItemRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DespesaRepository extends LancamentoItemRepository<Despesa> {
    @Query("SELECT d FROM Despesa d WHERE d.lancamento.id = :lancamentoId")
    Optional<Despesa> findByLancamentoId(@Param("lancamentoId") Long lancamentoId);
}
