package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.JobErrorLog;
import br.com.lcano.centraldecontrole.dto.JobErrorLogDTO;
import br.com.lcano.centraldecontrole.repository.JobErrorLogRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class JobErrorLogService extends AbstractGenericService<JobErrorLog, Long> {
    private JobErrorLogRepository repository;
    private DateUtil dateUtil;
    private UsuarioUtil usuarioUtil;

    @Override
    protected JobErrorLogRepository getRepository() {
        return repository;
    }

    @Override
    protected JobErrorLogDTO getDtoInstance() {
        return new JobErrorLogDTO();
    }

    public JobErrorLog createAndSaveJobErrorLog(String jobName, String errorMessage, String stackTrace) {
        JobErrorLog errorLog = new JobErrorLog();

        errorLog.setJobName(jobName);
        errorLog.setErrorTimestamp(dateUtil.getDataHoraAtual());
        errorLog.setUsuario(usuarioUtil.getUsuarioAutenticado());
        errorLog.setErrorMessage(errorMessage);
        errorLog.setStackTrace(stackTrace);

        return repository.save(errorLog);
    }
}
