package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratofaturacartao;

import br.com.lcano.centraldecontrole.util.DateUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImportacaoExtratoFaturaCartaoJobStarter {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job importacaoExtratoFaturaCartaoJob;

    @Autowired
    DateUtil dateUtil;

    public void startJob(Long arquivoId, Long usuarioId, Date dataVencimento) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("arquivoId", arquivoId)
                .addLong("usuarioId", usuarioId)
                .addDate("startDate", dateUtil.getDataAtual())
                .addDate("dataVencimento", dataVencimento)
                .toJobParameters();
        jobLauncher.run(importacaoExtratoFaturaCartaoJob, jobParameters);
    }
}
