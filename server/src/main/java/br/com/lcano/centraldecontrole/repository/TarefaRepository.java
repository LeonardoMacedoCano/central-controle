package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByUsuarioId(Long idUsuario);
    List<Tarefa> findByUsuarioIdAndDataPrazoBetween(Long idUsuario, Date startOfMonth, Date endOfMonth);
}
