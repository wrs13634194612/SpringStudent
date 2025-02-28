package com.example.demo.service;



import com.example.demo.controller.CustomerController.CustomerRequest;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Lesson;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.LessonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    public Customer createCustomer(CustomerRequest request) {
        Lesson lesson = null;
        if (request.lessonId() != null) {
            lesson = lessonRepository.findById(request.lessonId())
                    .orElseThrow(() -> new RuntimeException("Lesson not found"));
        }

        Customer customer = new Customer();
        customer.setUsername(request.username());
        customer.setPassword(request.password());
        customer.setRole(request.role());
        customer.setRealName(request.realName());
        customer.setGender(request.gender());
        customer.setEmail(request.email());
        customer.setLesson(lesson);

        try {
            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Username already exists");
        }
    }

    public Optional<Customer> getById(Integer id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer updateCustomer(Integer id, CustomerRequest request) {
        return customerRepository.findById(id).map(customer -> {
            if (request.username() != null) customer.setUsername(request.username());
            if (request.password() != null) customer.setPassword(request.password());
            if (request.role() != null) customer.setRole(request.role());
            if (request.realName() != null) customer.setRealName(request.realName());
            if (request.gender() != null) customer.setGender(request.gender());
            if (request.email() != null) customer.setEmail(request.email());

            if (request.lessonId() != null) {
                Lesson lesson = lessonRepository.findById(request.lessonId())
                        .orElseThrow(() -> new RuntimeException("Lesson not found"));
                customer.setLesson(lesson);
            } else {
                customer.setLesson(null);
            }

            try {
                return customerRepository.save(customer);
            } catch (DataIntegrityViolationException e) {
                throw new IllegalArgumentException("Username already exists");
            }
        }).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Transactional
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}