package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Receita;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.ReceitaCategoria;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReceitaDTO implements LancamentoItemDTO {
    private Long id;
    private CategoriaDTO categoria;
    private BigDecimal valor;
    private Date dataRecebimento;

    public static ReceitaDTO converterParaDTO(Receita receita) {
        ReceitaDTO dto = new ReceitaDTO();
        dto.setId(receita.getId());
        dto.setCategoria(CategoriaDTO.converterParaDTO(receita.getCategoria()));
        dto.setValor(receita.getValor());
        dto.setDataRecebimento(receita.getDataRecebimento());
        return dto;
    }

    public Receita toEntity(Long id,
                            Lancamento lancamento,
                            ReceitaCategoria receitaCategoria,
                            Date dataRecebimento,
                            BigDecimal valor) {
        Receita receita = new Receita();

        receita.setId(id);
        receita.setLancamento(lancamento);
        receita.setCategoria(receitaCategoria);
        receita.setDataRecebimento(dataRecebimento);
        receita.setValor(valor);

        return receita;
    }
}
