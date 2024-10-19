package br.com.lcano.centraldecontrole.dto.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.ServicoCategoria;
import br.com.lcano.centraldecontrole.domain.servicos.ServicoCategoriaRel;
import lombok.Data;

@Data
public class ServicoCategoriaDTO {
    private Long id;
    private String descricao;
    private String icone;

    public static ServicoCategoriaDTO converterParaDTO(ServicoCategoriaRel servicoCategoriaRel) {
        ServicoCategoria servicoCategoria = servicoCategoriaRel.getServicoCategoria();

        ServicoCategoriaDTO dto = new ServicoCategoriaDTO();
        dto.setId(servicoCategoria.getId());
        dto.setDescricao(servicoCategoria.getDescricao());
        dto.setIcone(servicoCategoria.getIcone());
        return dto;
    }
}
