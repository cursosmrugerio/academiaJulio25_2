package com.batch.service;

import com.batch.entities.Person;

import java.util.List;

public interface IPersonService {

    public Iterable<Person> saveAll(List<Person> personsList);

}
