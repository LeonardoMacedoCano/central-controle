package br.com.lcano.centraldecontrole.dto.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.ExtratoContaRegra;
import lombok.Data;

@Data
public class ExtratoContaRegraDTO {
    private Long id;
    private String tipoRegra;
    private String descricaoMatch;
    private String descricaoDestino;
    private Long idCategoria;
    private Long prioridade;
    private boolean ativo;

    public static ExtratoContaRegraDTO converterParaDTO(ExtratoContaRegra extratoContaRegra) {
        ExtratoContaRegraDTO dto = new ExtratoContaRegraDTO();
        dto.setId(extratoContaRegra.getId());
        dto.setTipoRegra(extratoContaRegra.getTipoRegra().getDescricao());
        dto.setDescricaoMatch(extratoContaRegra.getDescricaoMatch());
        dto.setDescricaoDestino(extratoContaRegra.getDescricaoDestino());
        dto.setIdCategoria(extratoContaRegra.getIdCategoria());
        dto.setPrioridade(extratoContaRegra.getPrioridade());
        dto.setAtivo(extratoContaRegra.isAtivo());
        return dto;
    }
}
