package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.JobErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobErrorLogRepository extends JpaRepository<JobErrorLog, Long> {
}
