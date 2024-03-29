package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.Despesa;
import lombok.Data;
import java.util.List;

@Data
public class DespesaResumoMensalDTO {
    private Long id;
    private CategoriaDTO categoria;
    private String descricao;
    private Double valorTotal;
    private String situacao;

    public static DespesaResumoMensalDTO converterParaDTO(Despesa despesa) {
        DespesaResumoMensalDTO dto = new DespesaResumoMensalDTO();
        dto.setId(despesa.getId());
        dto.setCategoria(CategoriaDTO.converterParaDTO(despesa.getCategoria()));
        dto.setDescricao(despesa.getDescricao());

        List<DespesaParcelaDTO> parcelasDTO = DespesaParcelaDTO.converterListaParaDTO(despesa.getParcelas());

        dto.setValorTotal(calcularValorTotal(parcelasDTO));
        dto.setSituacao(calcularSituacao(parcelasDTO));

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
