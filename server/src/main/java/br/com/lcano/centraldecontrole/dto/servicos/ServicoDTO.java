package br.com.lcano.centraldecontrole.dto.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.Servico;
import br.com.lcano.centraldecontrole.enums.servicos.DockerStatusEnum;
import lombok.Data;

@Data
public class ServicoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Integer porta;
    private Long idarquivo;
    private DockerStatusEnum status;
    private Boolean permissao;

    public static ServicoDTO converterParaDTO(Servico servico, DockerStatusEnum status, Boolean permissao) {
        ServicoDTO dto = new ServicoDTO();
        dto.setId(servico.getId());
        dto.setNome(servico.getNome());
        dto.setDescricao(servico.getDescricao());
        dto.setPorta(servico.getPorta());
        if (null != servico.getArquivo()) dto.setIdarquivo(servico.getArquivo().getId());
        dto.setStatus(status);
        dto.setPermissao(permissao);
        return dto;
    }
}
