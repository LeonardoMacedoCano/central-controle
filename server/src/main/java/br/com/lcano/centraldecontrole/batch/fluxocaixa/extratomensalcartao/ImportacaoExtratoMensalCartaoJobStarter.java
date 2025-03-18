package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratomensalcartao;

import br.com.lcano.centraldecontrole.util.DateUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImportacaoExtratoMensalCartaoJobStarter {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("importacaoExtratoMensalCartaoJob")
    private Job importacaoExtratoMensalCartaoJob;

    @Autowired
    private DateUtil dateUtil;

    public void startJob(Long arquivoId, Long usuarioId, Date dataVencimento) throws Exception {
        JobParametersBuilder parametersBuilder = new JobParametersBuilder()
                .addLong("arquivoId", arquivoId)
                .addLong("usuarioId", usuarioId)
                .addDate("startDate", dateUtil.getDataAtual())
                .addString("jobIdentifier", "importacaoExtratoMensalCartaoJob")
                .addLong("run.id", System.currentTimeMillis());

        if (dataVencimento != null) {
            parametersBuilder.addDate("dataVencimento", dataVencimento);
        }

        JobParameters jobParameters = parametersBuilder.toJobParameters();
        jobLauncher.run(importacaoExtratoMensalCartaoJob, jobParameters);
    }
}
