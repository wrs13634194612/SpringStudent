package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "curriculum")
@Data
public class Curriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "curriculum_name", nullable = false, length = 100)
    private String curriculumName;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Customer teacher;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(columnDefinition = "TINYINT UNSIGNED DEFAULT 2")
    private Integer credit;

    @Column(name = "start_date")
    private LocalDate startDate;
}
