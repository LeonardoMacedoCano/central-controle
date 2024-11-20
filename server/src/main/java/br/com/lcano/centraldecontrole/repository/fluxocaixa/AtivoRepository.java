package br.com.lcano.centraldecontrole.repository.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtivoRepository extends JpaRepository<Ativo, Long> {

    @Query("SELECT a FROM Ativo a WHERE a.lancamento.id = :lancamentoId")
    Optional<Ativo> findByLancamentoId(@Param("lancamentoId") Long lancamentoId);
}
