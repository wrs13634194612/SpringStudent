package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customername", unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 7)
    private UserRole role;

    @Column(name = "real_name", nullable = false, length = 20)
    private String realName;

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private Gender gender;

    @Column(length = 50)
    private String email;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    public enum UserRole {
        ADMIN, TEACHER, STUDENT
    }

    public enum Gender {
        MALE, FEMALE
    }
}