package com.backend.centraldecontrole.repository;

import com.backend.centraldecontrole.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByUsuarioId(Long idUsuario);
    List<Tarefa> findByUsuarioIdAndDataInclusaoBetween(Long idUsuario, Date startOfMonth, Date endOfMonth);
}
