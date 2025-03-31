package br.com.lcano.centraldecontrole.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JobName {
    IMPORTACAO_EXTRATO_MOVIMENTACAO_B3_JOB("importacaoExtratoMovimentacaoB3Job", "Importação do extrato de movimentação da B3"),
    IMPORTACAO_EXTRATO_CONTA_CORRENTE_JOB("importacaoExtratoContaCorrenteJob", "Importação do extrato de conta corrente"),
    IMPORTACAO_EXTRATO_FATURA_CARTAO_JOB("importacaoExtratoFaturaCartaoJob", "Importação do extrato mensal da fatura do cartão");

    private final String jobId;
    private final String descricao;

    public static String getDescricaoByJobId(String jobId) {
        for (JobName job : values()) {
            if (job.getJobId().equals(jobId)) {
                return job.getDescricao();
            }
        }
        return "Descrição não encontrada";
    }
}
