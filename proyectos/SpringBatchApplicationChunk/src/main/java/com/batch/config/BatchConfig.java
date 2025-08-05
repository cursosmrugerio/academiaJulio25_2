package com.batch.config;

import com.batch.entities.Person;
import com.batch.steps.PersonItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public FlatFileItemReader<Person> itemReader(){
        return new FlatFileItemReaderBuilder<Person>()
                .name("readerPersons")
                .resource(resourceLoader.getResource("classpath:persons.csv"))
                .linesToSkip(1)
                .delimited()
                .names("name", "lastName", "age")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }

    @Bean
    public PersonItemWriter itemWriter(){
        return new PersonItemWriter();
    }

    @Bean
    public Step readFile(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("readFile")
                .repository(jobRepository)
                .<Person, Person>chunk(10)
                .reader(itemReader())
                .writer(itemWriter())
                .transactionManager(transactionManager)
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
