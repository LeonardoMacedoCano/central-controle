package com.backend.centraldecontrole.repository;

import com.backend.centraldecontrole.model.Ideia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface IdeiaRepository extends JpaRepository<Ideia, Long> {
    List<Ideia> findByUsuarioId(Long idUsuario);
    List<Ideia> findByUsuarioIdAndDataPrazoBetween(Long idUsuario, Date startOfMonth, Date endOfMonth);
}
