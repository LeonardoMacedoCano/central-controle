package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratomensalcartao;

import br.com.lcano.centraldecontrole.batch.JobErrorListener;
import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoMensalCartaoDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class ImportacaoExtratoMensalCartaoConfig {

    @Autowired
    private JobErrorListener jobErrorListener;

    @Bean
    public Job importacaoExtratoMensalCartaoJob(JobRepository jobRepository,
                                                Step importacaoExtratoMensalCartaoStep) {
        return new JobBuilder("importacaoExtratoMensalCartaoJob", jobRepository)
                .incrementer(new org.springframework.batch.core.launch.support.RunIdIncrementer())
                .listener(jobErrorListener)
                .start(importacaoExtratoMensalCartaoStep)
                .build();
    }

    @Bean
    public Step importacaoExtratoMensalCartaoStep(JobRepository jobRepository,
                                                  PlatformTransactionManager transactionManager,
                                                  ImportacaoExtratoMensalCartaoReader reader,
                                                  ImportacaoExtratoMensalCartaoProcessor processor,
                                                  ImportacaoExtratoMensalCartaoWriter writer) {
        return new StepBuilder("importacaoExtratoMensalCartaoStep", jobRepository)
                .<ExtratoMensalCartaoDTO, Lancamento>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
