package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.LancamentoSpecifications;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, JpaSpecificationExecutor<Lancamento> {
    default Page<Lancamento> search(Long usuarioId, List<TipoLancamentoEnum> tipos, String descricao,
                                    Date dataInicio, Date dataFim, Pageable pageable) {
        return findAll(
                Specification.where(LancamentoSpecifications.hasUsuarioId(usuarioId))
                        .and(LancamentoSpecifications.hasTipos(tipos))
                        .and(LancamentoSpecifications.hasDescricaoLike(descricao))
                        .and(LancamentoSpecifications.hasDataLancamentoAfter(dataInicio))
                        .and(LancamentoSpecifications.hasDataLancamentoBefore(dataFim)),
                pageable
        );
    }
}
