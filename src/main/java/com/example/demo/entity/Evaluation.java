package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "evaluation",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "curriculum_id"}))
@Data
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Customer student;

    @ManyToOne
    @JoinColumn(name = "curriculum_id", nullable = false)
    private Curriculum curriculum;

    @Column(columnDefinition = "DECIMAL(5,2) UNSIGNED DEFAULT 0")
    private BigDecimal evaluation;

    private String semester;

    @CreationTimestamp
    @Column(name = "record_time", updatable = false)
    private LocalDateTime recordTime;
}
