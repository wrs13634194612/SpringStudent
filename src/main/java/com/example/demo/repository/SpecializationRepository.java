package com.example.demo.repository;


import com.example.demo.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {
    boolean existsByName(String name);
}