package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratomovimentacaob3;

import br.com.lcano.centraldecontrole.batch.JobErrorListener;
import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoMovimentacaoB3DTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static br.com.lcano.centraldecontrole.enums.JobName.IMPORTACAO_EXTRATO_MOVIMENTACAO_B3_JOB;

@Configuration
public class ImportacaoExtratoMovimentacaoB3Config {

    @Autowired
    private JobErrorListener jobErrorListener;

    @Bean(name = "importacaoExtratoMovimentacaoB3Job")
    public Job importacaoExtratoMovimentacaoB3Job(JobRepository jobRepository,
                                            Step importacaoExtratoMovimentacaoB3Step) {
        return new JobBuilder(IMPORTACAO_EXTRATO_MOVIMENTACAO_B3_JOB.getJobId(), jobRepository)
                .incrementer(new org.springframework.batch.core.launch.support.RunIdIncrementer())
                .listener(jobErrorListener)
                .start(importacaoExtratoMovimentacaoB3Step)
                .build();
    }

    @Bean
    public Step importacaoExtratoMovimentacaoB3Step(JobRepository jobRepository,
                                              PlatformTransactionManager transactionManager,
                                              ImportacaoExtratoMovimentacaoB3Reader reader,
                                              ImportacaoExtratoMovimentacaoB3Processor processor,
                                              ImportacaoExtratoMovimentacaoB3Writer writer) {
        return new StepBuilder("importacaoExtratoMovimentacaoB3Step", jobRepository)
                .<ExtratoMovimentacaoB3DTO, Lancamento>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
