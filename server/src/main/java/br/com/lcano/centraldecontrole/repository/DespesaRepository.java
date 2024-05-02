package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByUsuarioId(Long idUsuario);

    @Query("SELECT d FROM Despesa d LEFT JOIN FETCH d.parcelas WHERE d.id = :id")
    Optional<Despesa> findByIdWithParcelas(@Param("id") Long id);
}
