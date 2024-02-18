package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByUsuarioId(Long idUsuario);
    List<Despesa> findByUsuarioIdAndDataBetween(Long idUsuario, Date startOfMonth, Date endOfMonth);
}
