package br.com.lcano.centraldecontrole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface LancamentoItemRepository<T> extends JpaRepository<T, Long> {
    Optional<T> findByLancamentoId(Long lancamentoId);
}
