package br.com.lcano.centraldecontrole.repository.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.Servico;
import br.com.lcano.centraldecontrole.domain.servicos.ServicoCategoria;
import br.com.lcano.centraldecontrole.domain.servicos.ServicoCategoriaRel;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class ServicoSpecifications {
    public static Specification<Servico> hasNome(String nome) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nome"), nome);
    }

    public static Specification<Servico> hasNomeNot(String nome) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("nome"), nome);
    }

    public static Specification<Servico> hasNomeLike(String nome) {
        return (root, query, builder) -> builder.like(root.get("nome"), "%" + nome + "%");
    }

    public static Specification<Servico> hasDescricao(String descricao) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("descricao"), descricao);
    }

    public static Specification<Servico> hasDescricaoNot(String descricao) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("descricao"), descricao);
    }

    public static Specification<Servico> hasDescricaoLike(String descricao) {
        return (root, query, builder) -> builder.like(root.get("descricao"), "%" + descricao + "%");
    }

    public static Specification<Servico> hasPorta(Integer porta) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("porta"), porta);
    }

    public static Specification<Servico> hasPortaNot(Integer porta) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("porta"), porta);
    }

    public static Specification<Servico> hasPortaGreaterThan(Integer porta) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("porta"), porta);
    }

    public static Specification<Servico> hasPortaLessThan(Integer porta) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("porta"), porta);
    }

    public static Specification<Servico> hasPortaGreaterOrEqual(Integer porta) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("porta"), porta);
    }

    public static Specification<Servico> hasPortaLessOrEqual(Integer porta) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("porta"), porta);
    }

    public static Specification<Servico> hasCategorias(String descricao) {
        return (root, query, criteriaBuilder) -> {
            Join<Servico, ServicoCategoriaRel> categoriaRelJoin = root.join("servicoCategoriaRel");
            Join<ServicoCategoriaRel, ServicoCategoria> categoriaJoin = categoriaRelJoin.join("servicoCategoria");
            return criteriaBuilder.equal(categoriaJoin.get("descricao"), descricao);
        };
    }

    public static Specification<Servico> hasCategoriasNot(String descricao) {
        return (root, query, criteriaBuilder) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            var categoriaRelRoot = subquery.from(ServicoCategoriaRel.class);
            Join<ServicoCategoriaRel, ServicoCategoria> categoriaJoin = categoriaRelRoot.join("servicoCategoria");

            subquery.select(categoriaRelRoot.get("servico").get("id"))
                    .where(criteriaBuilder.equal(categoriaJoin.get("descricao"), descricao));

            return criteriaBuilder.not(root.get("id").in(subquery));
        };
    }
}
