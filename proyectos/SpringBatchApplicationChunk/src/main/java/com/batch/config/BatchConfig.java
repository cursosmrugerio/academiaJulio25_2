package com.batch.config;

import com.batch.entities.Person;
import com.batch.steps.PersonItemReader;
import com.batch.steps.PersonItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public PersonItemReader itemReader(){
        return new PersonItemReader();

    }

    @Bean
    public PersonItemWriter itemWriter(){
        return new PersonItemWriter();
    }

    @Bean
    public Step readFile(){
        return stepBuilderFactory.get("readFile")
                .<Person, Person>chunk(10)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("readFileWithChunk")
                .start(readFile())
                .build();
    }

}
