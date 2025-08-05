package com.batch.config;

import com.batch.entities.Person;
import com.batch.steps.PersonItemReader;
import com.batch.steps.PersonItemWriter;
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
public class BatchConfig {

    @Bean
    public PersonItemReader itemReader(){
        return new PersonItemReader();

    }

    @Bean
    public PersonItemWriter itemWriter(){
        return new PersonItemWriter();
    }

    @Bean
    public Step readFile(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("readFile")
                .repository(jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step readFile){
        return new JobBuilder("readFileWithChunk")
                .repository(jobRepository)
                .start(readFile)
                .build();
    }

}
