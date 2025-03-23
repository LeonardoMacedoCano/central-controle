package br.com.lcano.centraldecontrole.dto;

import br.com.lcano.centraldecontrole.domain.JobErrorLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class JobErrorLogDTO extends BaseDTO<JobErrorLog>{
    private Long id;
    private String jobName;
    private LocalDateTime errorTimestamp;
    private String errorMessage;
    private String stackTrace;

    @Override
    public JobErrorLogDTO fromEntity(JobErrorLog entity) {
        this.id = entity.getId();
        this.jobName = entity.getJobName();
        this.errorTimestamp = entity.getErrorTimestamp();
        this.errorMessage = entity.getErrorMessage();
        this.stackTrace = entity.getStackTrace();

        return this;
    }

    @Override
    public JobErrorLog toEntity() {
        JobErrorLog entity = new JobErrorLog();

        entity.setId(this.id);
        entity.setJobName(this.jobName);
        entity.setErrorTimestamp(this.errorTimestamp);
        entity.setErrorMessage(this.errorMessage);
        entity.setStackTrace(this.stackTrace);

        return entity;
    }
}
