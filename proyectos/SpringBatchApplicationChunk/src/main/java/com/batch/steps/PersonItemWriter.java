package com.batch.steps;

import com.batch.entities.Person;
import com.batch.service.IPersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class PersonItemWriter implements ItemWriter<Person> {

    @Autowired
    private IPersonService personService;

    @Override
    public void write(Chunk<? extends Person> chunk) throws Exception {

        chunk.forEach(person -> log.info("Processing person: {}", person.toString()));

        @SuppressWarnings("unchecked")
        List<Person> persons = (List<Person>) chunk.getItems();
        personService.saveAll(persons);

    }
}
