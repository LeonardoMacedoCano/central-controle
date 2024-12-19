package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.Categoria;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.ReceitaCategoria;
import lombok.Data;

@Data
public class CategoriaDTO {
    private Long id;
    private String descricao;

    public static CategoriaDTO converterParaDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setDescricao(categoria.getDescricao());
        return dto;
    }

    public static CategoriaDTO converterParaDTO(DespesaCategoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setDescricao(categoria.getDescricao());
        return dto;
    }

    public static CategoriaDTO converterParaDTO(ReceitaCategoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setDescricao(categoria.getDescricao());
        return dto;
    }
}
