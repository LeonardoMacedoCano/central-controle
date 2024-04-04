package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.Despesa;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class DespesaDTO {
    private Long id;

    private CategoriaDTO categoria;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataLancamento;

    private String descricao;

    private Double valorTotal;

    private String situacao;

    @JsonProperty("parcelas")
    private List<DespesaParcelaDTO> parcelasDTO;

    public static DespesaDTO converterParaDTO(Despesa despesa) {
        DespesaDTO dto = new DespesaDTO();

        dto.setId(despesa.getId());
        dto.setCategoria(CategoriaDTO.converterParaDTO(despesa.getCategoria()));
        dto.setDataLancamento(despesa.getDataLancamento());
        dto.setDescricao(despesa.getDescricao());
        dto.setParcelasDTO(DespesaParcelaDTO.converterListaParaDTO(despesa.getParcelas()));
        dto.setValorTotal(calcularValorTotal(dto.parcelasDTO));
        dto.setSituacao(calcularSituacao(dto.parcelasDTO));

        return dto;
    }

    static Double calcularValorTotal(List<DespesaParcelaDTO> parcelas) {
        return parcelas.stream()
                .mapToDouble(DespesaParcelaDTO::getValor)
                .sum();
    }

    static String calcularSituacao(List<DespesaParcelaDTO> parcelas) {
        boolean todasPagas = parcelas.stream()
                .allMatch(DespesaParcelaDTO::getPago);
        boolean todasNaoPagas = parcelas.stream()
                .noneMatch(DespesaParcelaDTO::getPago);
        if (todasPagas) {
            return "Pago";
        } else if (todasNaoPagas) {
            return "Não pago";
        } else {
            return "Parcialmente pago";
        }
    }
}
