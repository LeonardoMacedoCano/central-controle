package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratomovimentacaob3;

import br.com.lcano.centraldecontrole.util.DateUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ImportacaoExtratoMovimentacaoB3JobStarter {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("importacaoExtratoMovimentacaoB3Job")
    private Job importacaoExtratoMovimentacaoB3Job;

    @Autowired
    private DateUtil dateUtil;

    public void startJob(Long arquivoId, Long usuarioId) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("arquivoId", arquivoId)
                .addLong("usuarioId", usuarioId)
                .addDate("startDate", dateUtil.getDataAtual())
                .addString("jobIdentifier", "importacaoExtratoMovimentacaoB3Job")
                .addLong("run.id", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(importacaoExtratoMovimentacaoB3Job, jobParameters);
    }
}
