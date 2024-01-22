package com.backend.centraldecontrole.repository;

import com.backend.centraldecontrole.model.CategoriaIdeia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaIdeiaRepository extends JpaRepository<CategoriaIdeia, Long> {
}
