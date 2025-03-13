package br.com.lcano.centraldecontrole.batch;

import br.com.lcano.centraldecontrole.domain.JobErrorLog;
import br.com.lcano.centraldecontrole.enums.JobName;
import br.com.lcano.centraldecontrole.service.JobErrorLogService;
import br.com.lcano.centraldecontrole.service.NotificacaoService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class JobErrorListener implements JobExecutionListener {

    private final JobErrorLogService jobErrorLogService;
    private final NotificacaoService notificacaoService;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            String jobName = jobExecution.getJobInstance().getJobName();
            String jobDescricao = JobName.getDescricaoByJobId(jobName);

            List<Throwable> failureExceptions = jobExecution.getAllFailureExceptions();
            if (failureExceptions.isEmpty()) {
                notificacaoService.createAndSaveNotificacao(String.format("Erro na rotina de %s.", jobDescricao), null);
                return;
            }

            Throwable lastException = failureExceptions.get(0);
            JobErrorLog jobErrorLog = jobErrorLogService.createAndSaveJobErrorLog(
                    jobDescricao,
                    lastException.getMessage(),
                    ExceptionUtils.getStackTrace(lastException)
            );

            notificacaoService.createAndSaveNotificacao(
                    String.format("Erro na rotina de %s.", jobDescricao),
                    String.format("/job-error/%d", jobErrorLog.getId())
            );
        }
    }
}
