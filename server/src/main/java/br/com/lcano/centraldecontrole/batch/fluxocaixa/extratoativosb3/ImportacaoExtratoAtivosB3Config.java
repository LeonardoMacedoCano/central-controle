package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoativosb3;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoAtivosB3DTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class ImportacaoExtratoAtivosB3Config {

    @Bean
    public Job importacaoExtratoAtivosB3Job(JobRepository jobRepository,
                                            Step importacaoExtratoAtivosB3Step) {
        return new JobBuilder("importacaoExtratoAtivosB3Job", jobRepository)
                .incrementer(new org.springframework.batch.core.launch.support.RunIdIncrementer())
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
