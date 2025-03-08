package br.com.lcano.centraldecontrole.batch;

import br.com.lcano.centraldecontrole.domain.JobErrorLog;
import br.com.lcano.centraldecontrole.repository.JobErrorLogRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobErrorListener implements JobExecutionListener {

    @Autowired
    private JobErrorLogRepository jobErrorLogRepository;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private UsuarioUtil usuarioUtil;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            JobErrorLog errorLog = new JobErrorLog();
            errorLog.setJobName(jobExecution.getJobInstance().getJobName());
            errorLog.setErrorTimestamp(dateUtil.getDataAtual());
            errorLog.setUsuario(usuarioUtil.getUsuarioAutenticado());

            if (!jobExecution.getAllFailureExceptions().isEmpty()) {
                Throwable lastException = jobExecution.getAllFailureExceptions().get(0);
                errorLog.setErrorMessage(lastException.getMessage());
                errorLog.setStackTrace(ExceptionUtils.getStackTrace(lastException));
            } else {
                errorLog.setErrorMessage("Job falhou, mas nenhuma exceção foi registrada.");
                errorLog.setStackTrace("Nenhum stack trace disponível.");
            }

            jobErrorLogRepository.save(errorLog);
        }
    }
}
