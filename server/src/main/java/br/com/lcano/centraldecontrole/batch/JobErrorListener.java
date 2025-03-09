package br.com.lcano.centraldecontrole.batch;

import br.com.lcano.centraldecontrole.domain.JobErrorLog;
import br.com.lcano.centraldecontrole.service.JobErrorLogService;
import br.com.lcano.centraldecontrole.service.NotificacaoService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobErrorListener implements JobExecutionListener {

    @Autowired
    private JobErrorLogService jobErrorLogService;

    @Autowired
    private NotificacaoService notificacaoService;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            // TODO - Fazer um enum com depara pegar uma descricao do jobName e nao o proprio jobName
            String jobName = jobExecution.getJobInstance().getJobName();

            if (jobExecution.getAllFailureExceptions().isEmpty()) {
                notificacaoService.createAndSaveNotificacao(String.format("Erro na rotina %s. Teste mensagem erro desconhecido", jobName), null);
            }

            if (!jobExecution.getAllFailureExceptions().isEmpty()) {
                Throwable lastException = jobExecution.getAllFailureExceptions().get(0);

                JobErrorLog jobErrorLog = jobErrorLogService.createAndSaveJobErrorLog(
                        jobName,
                        lastException.getMessage(),
                        ExceptionUtils.getStackTrace(lastException)
                );

                notificacaoService.createAndSaveNotificacao("Teste", String.format("teste id %d", jobErrorLog.getId()));
            }
        }
    }
}
