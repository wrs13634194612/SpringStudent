package com.example.demo.service;


import com.example.demo.controller.LessonController.LessonRequest;
import com.example.demo.entity.Lesson;
import com.example.demo.entity.Specialization;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.SpecializationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Transactional
    public Lesson createLesson(LessonRequest request) {
        Specialization specialization = specializationRepository.findById(request.specializationId())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        Lesson lesson = new Lesson();
        lesson.setLessonName(request.lessonName());
        lesson.setGrade(request.grade());
        lesson.setSpecialization(specialization);

        return lessonRepository.save(lesson);
    }

    public Optional<Lesson> getById(Integer id) {
        return lessonRepository.findById(id);
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @Transactional
    public Lesson updateLesson(Integer id, LessonRequest request) {
        return lessonRepository.findById(id).map(lesson -> {
            lesson.setLessonName(request.lessonName());
            lesson.setGrade(request.grade());
            specializationRepository.findById(request.specializationId())
                    .ifPresent(lesson::setSpecialization);
            return lessonRepository.save(lesson);
        }).orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    @Transactional
    public void deleteLesson(Integer id) {
        lessonRepository.deleteById(id);
    }
}