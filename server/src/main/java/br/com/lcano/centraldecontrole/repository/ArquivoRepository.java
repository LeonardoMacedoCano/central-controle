package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {
    Optional<Arquivo> findByHash(String hash);
}
