package br.com.lcano.centraldecontrole.dto.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.Servico;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import br.com.lcano.centraldecontrole.enums.servicos.DockerStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServicoDTO extends BaseDTO<Servico> {
    private Long id;
    private String nome;
    private String descricao;
    private Integer porta;
    private Long idarquivo;
    private DockerStatus status;
    private Boolean permissao;
    private List<ServicoCategoriaDTO> categorias = new ArrayList<>();

    public ServicoDTO fromEntity(Servico servico, DockerStatus status, Boolean permissao, List<ServicoCategoriaDTO> categorias) {
        this.id = servico.getId();
        this.nome = servico.getNome();
        this.descricao = servico.getDescricao();
        this.porta = servico.getPorta();
        if (null != servico.getArquivo()) this.idarquivo = servico.getArquivo().getId();
        this.status = status;
        this.permissao = permissao;
        this.categorias = categorias;
        return this;
    }

    @Override
    public ServicoDTO fromEntity(Servico entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();
        this.porta = entity.getPorta();
        if (null != entity.getArquivo()) this.idarquivo = entity.getArquivo().getId();
        return this;
    }

    @Override
    public Servico toEntity() {
        return null;
    }
}
