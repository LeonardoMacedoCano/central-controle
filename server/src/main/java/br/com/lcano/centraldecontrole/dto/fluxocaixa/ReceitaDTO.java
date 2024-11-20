package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.Receita;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReceitaDTO implements LancamentoItemDTO {
    private Long id;
    private CategoriaDTO categoria;
    private BigDecimal valor;

    public static ReceitaDTO converterParaDTO(Receita receita) {
        ReceitaDTO dto = new ReceitaDTO();
        dto.setId(receita.getId());
        dto.setCategoria(CategoriaDTO.converterParaDTO(receita.getCategoria()));
        dto.setValor(receita.getValor());
        return dto;
    }
}
