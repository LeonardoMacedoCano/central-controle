package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratofaturacartao;

import br.com.lcano.centraldecontrole.batch.JobErrorListener;
import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoFaturaCartaoDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static br.com.lcano.centraldecontrole.enums.JobName.IMPORTACAO_EXTRATO_FATURA_CARTAO_JOB;

@Configuration
public class ImportacaoExtratoFaturaCartaoConfig {

    @Autowired
    private JobErrorListener jobErrorListener;

    @Bean(name = "importacaoExtratoFaturaCartaoJob")
    public Job importacaoExtratoFaturaCartaoJob(JobRepository jobRepository,
                                                Step importacaoExtratoFaturaCartaoStep) {
        return new JobBuilder(IMPORTACAO_EXTRATO_FATURA_CARTAO_JOB.getJobId(), jobRepository)
                .incrementer(new org.springframework.batch.core.launch.support.RunIdIncrementer())
                .listener(jobErrorListener)
                .start(importacaoExtratoFaturaCartaoStep)
                .build();
    }

    @Bean
    public Step importacaoExtratoFaturaCartaoStep(JobRepository jobRepository,
                                                  PlatformTransactionManager transactionManager,
                                                  ImportacaoExtratoFaturaCartaoReader reader,
                                                  ImportacaoExtratoFaturaCartaoProcessor processor,
                                                  ImportacaoExtratoFaturaCartaoWriter writer) {
        return new StepBuilder("importacaoExtratoFaturaCartaoStep", jobRepository)
                .<ExtratoFaturaCartaoDTO, Lancamento>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
