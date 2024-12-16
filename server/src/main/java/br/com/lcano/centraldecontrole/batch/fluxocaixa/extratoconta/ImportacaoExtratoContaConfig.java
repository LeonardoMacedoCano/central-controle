package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoconta;

import br.com.lcano.centraldecontrole.dto.LancamentoDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoContaDTO;
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
public class ImportacaoExtratoContaConfig {

    @Bean
    public Job importacaoExtratoContaJob(JobRepository jobRepository,
                                         Step importacaoExtratoContaStep) {
        return new JobBuilder("importacaoExtratoContaJob", jobRepository)
                .incrementer(new org.springframework.batch.core.launch.support.RunIdIncrementer())
                .start(importacaoExtratoContaStep)
                .build();
    }

    @Bean
    public Step importacaoExtratoContaStep(JobRepository jobRepository,
                                           PlatformTransactionManager transactionManager,
                                           ImportacaoExtratoContaReader reader,
                                           ImportacaoExtratoContaProcessor processor,
                                           ImportacaoExtratoContaWriter writer) {
        return new StepBuilder("importacaoExtratoContaStep", jobRepository)
                .<ExtratoContaDTO, LancamentoDTO>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
