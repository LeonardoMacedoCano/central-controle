package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoconta;

import br.com.lcano.centraldecontrole.util.DateUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportacaoExtratoContaJobStarter {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job importacaoExtratoContaJob;

    @Autowired
    DateUtil dateUtil;

    public void startJob(Long arquivoId, Long usuarioId) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("arquivoId", arquivoId)
                .addLong("usuarioId", usuarioId)
                .addDate("startDate", dateUtil.getDataAtual())
                .toJobParameters();
        jobLauncher.run(importacaoExtratoContaJob, jobParameters);
    }
}
