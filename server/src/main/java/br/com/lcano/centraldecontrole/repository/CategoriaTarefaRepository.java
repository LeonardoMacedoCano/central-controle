package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.model.CategoriaTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaTarefaRepository extends JpaRepository<CategoriaTarefa, Long> {
}
