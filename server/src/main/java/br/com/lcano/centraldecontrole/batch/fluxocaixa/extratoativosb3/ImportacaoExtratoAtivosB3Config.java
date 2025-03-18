package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoativosb3;

import br.com.lcano.centraldecontrole.batch.JobErrorListener;
import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoAtivosB3DTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static br.com.lcano.centraldecontrole.enums.JobName.IMPORTACAO_EXTRATO_ATIVOS_B3_JOB;

@Configuration
public class ImportacaoExtratoAtivosB3Config {

    @Autowired
    private JobErrorListener jobErrorListener;

    @Bean(name = "importacaoExtratoAtivosB3Job")
    public Job importacaoExtratoAtivosB3Job(JobRepository jobRepository,
                                            Step importacaoExtratoAtivosB3Step) {
        return new JobBuilder(IMPORTACAO_EXTRATO_ATIVOS_B3_JOB.getJobId(), jobRepository)
                .incrementer(new org.springframework.batch.core.launch.support.RunIdIncrementer())
                .listener(jobErrorListener)
                .start(importacaoExtratoAtivosB3Step)
                .build();
    }

    @Bean
    public Step importacaoExtratoAtivosB3Step(JobRepository jobRepository,
                                              PlatformTransactionManager transactionManager,
                                              ImportacaoExtratoAtivosB3Reader reader,
                                              ImportacaoExtratoAtivosB3Processor processor,
                                              ImportacaoExtratoAtivosB3Writer writer) {
        return new StepBuilder("importacaoExtratoAtivosB3Step", jobRepository)
                .<ExtratoAtivosB3DTO, Lancamento>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
