package com.backend.centraldecontrole.repository;

import com.backend.centraldecontrole.model.CategoriaTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaTarefaRepository extends JpaRepository<CategoriaTarefa, Long> {
}
