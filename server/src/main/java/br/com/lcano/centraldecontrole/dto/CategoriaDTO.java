package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.Categoria;
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
}
