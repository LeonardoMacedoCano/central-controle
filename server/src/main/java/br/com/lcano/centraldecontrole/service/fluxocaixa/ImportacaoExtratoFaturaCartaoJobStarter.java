package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.util.DateUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportacaoExtratoFaturaCartaoJobStarter {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importacaoExtratoFaturaCartaoJob;

    @Autowired
    private DateUtil dateUtil;

    public void startJob(Long arquivoId) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("arquivoId", arquivoId)
                .addDate("startDate", dateUtil.getDataAtual())
                .toJobParameters();
        jobLauncher.run(importacaoExtratoFaturaCartaoJob, jobParameters);
    }
}
