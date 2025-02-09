package br.com.lcano.centraldecontrole.repository.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.Renda;
import br.com.lcano.centraldecontrole.repository.LancamentoItemRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RendaRepository extends LancamentoItemRepository<Renda> {
    @Query("SELECT r FROM Renda r WHERE r.lancamento.id = :lancamentoId")
    Optional<Renda> findByLancamentoId(@Param("lancamentoId") Long lancamentoId);
}
