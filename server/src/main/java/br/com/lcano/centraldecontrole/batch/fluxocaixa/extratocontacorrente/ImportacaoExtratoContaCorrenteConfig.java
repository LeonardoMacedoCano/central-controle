package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratocontacorrente;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoContaCorrenteDTO;
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
public class ImportacaoExtratoContaCorrenteConfig {

    @Bean
    public Job importacaoExtratoContaCorrenteJob(JobRepository jobRepository,
                                                 Step importacaoExtratoContaCorrenteStep) {
        return new JobBuilder("importacaoExtratoContaCorrenteJob", jobRepository)
                .incrementer(new org.springframework.batch.core.launch.support.RunIdIncrementer())
                .start(importacaoExtratoContaCorrenteStep)
                .build();
    }

    @Bean
    public Step importacaoExtratoContaCorrenteStep(JobRepository jobRepository,
                                                   PlatformTransactionManager transactionManager,
                                                   ImportacaoExtratoContaCorrenteReader reader,
                                                   ImportacaoExtratoContaCorrenteProcessor processor,
                                                   ImportacaoExtratoContaCorrenteWriter writer) {
        return new StepBuilder("importacaoExtratoContaCorrenteStep", jobRepository)
                .<ExtratoContaCorrenteDTO, Lancamento>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}