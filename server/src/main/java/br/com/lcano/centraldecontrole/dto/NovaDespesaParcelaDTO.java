package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.util.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import java.util.Date;

@Data
public class NovaDespesaParcelaDTO {
    private Integer numero;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date dataVencimento;

    private Double valor;

    private Boolean pago;
}
