package com.javatechie.spring.batch.config;

import com.javatechie.spring.batch.entity.Customer;
import com.javatechie.spring.batch.entity.ProcessingReport;
import com.javatechie.spring.batch.repository.CustomerRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportItemReader implements ItemReader<ProcessingReport> {
    
    private CustomerRepository customerRepository;
    private boolean hasRead = false;
    
    public ReportItemReader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @Override
    public ProcessingReport read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (hasRead) {
            return null;
        }
        
        hasRead = true;
        
        List<Customer> customers = customerRepository.findAll();
        
        ProcessingReport report = new ProcessingReport();
        report.setReportDate(LocalDateTime.now());
        report.setTotalRecordsProcessed(customers.size());
        report.setJobStatus("COMPLETED");
        
        Map<String, Long> countryStats = customers.stream()
                .collect(Collectors.groupingBy(Customer::getCountry, Collectors.counting()));
        
        Map<String, Long> genderStats = customers.stream()
                .collect(Collectors.groupingBy(Customer::getGender, Collectors.counting()));
        
        report.setRecordsByCountry(countryStats);
        report.setRecordsByGender(genderStats);
        
        return report;
    }
}