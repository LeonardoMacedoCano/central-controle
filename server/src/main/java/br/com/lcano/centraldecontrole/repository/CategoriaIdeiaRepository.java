package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.model.CategoriaIdeia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaIdeiaRepository extends JpaRepository<CategoriaIdeia, Long> {
}
