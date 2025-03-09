package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.Notificacao;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotificacaoDTO extends BaseDTO<Notificacao>{
    private Long id;
    private LocalDateTime dataHora;
    private String mensagem;
    private String link;
    private boolean visto;

    @Override
    public NotificacaoDTO fromEntity(Notificacao entity) {
        this.id = entity.getId();
        this.dataHora = entity.getDataHora();
        this.mensagem = entity.getMensagem();
        this.link = entity.getLink();
        this.visto = entity.isVisto();

        return this;
    }

    @Override
    public Notificacao toEntity() {
        Notificacao entity = new Notificacao();

        entity.setId(this.id);
        entity.setDataHora(this.dataHora);
        entity.setMensagem(this.mensagem);
        entity.setLink(this.link);
        entity.setVisto(this.visto);

        return entity;
    }
}
