package br.com.lcano.centraldecontrole.repository.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.Servico;
import br.com.lcano.centraldecontrole.domain.servicos.ServicoCategoria;
import jakarta.persistence.criteria.Join;
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
            Join<Servico, ServicoCategoria> categoriaJoin = root.join("categorias");
            return criteriaBuilder.equal(categoriaJoin.get("descricao"), descricao);
        };
    }

    public static Specification<Servico> hasCategoriasNot(String descricao) {
        return (root, query, criteriaBuilder) -> {
            Join<Servico, ServicoCategoria> categoriaJoin = root.join("categorias");
            return criteriaBuilder.notEqual(categoriaJoin.get("descricao"), descricao);
        };
    }

    public static Specification<Servico> hasCategoriasLike(String descricao) {
        return (root, query, criteriaBuilder) -> {
            Join<Servico, ServicoCategoria> categoriaJoin = root.join("categorias");
            return criteriaBuilder.like(categoriaJoin.get("descricao"), "%" + descricao + "%");
        };
    }
}
