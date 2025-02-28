package com.example.demo.service;


import com.example.demo.controller.CurriculumController.CurriculumRequest;
import com.example.demo.entity.Curriculum;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Lesson;
import com.example.demo.repository.CurriculumRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.LessonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CurriculumService {

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    public Curriculum createCurriculum(CurriculumRequest request) {
        Customer teacher = customerRepository.findById(request.teacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Lesson lesson = lessonRepository.findById(request.lessonId())
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        Curriculum curriculum = new Curriculum();
        curriculum.setCurriculumName(request.curriculumName());
        curriculum.setTeacher(teacher);
        curriculum.setLesson(lesson);
        curriculum.setCredit(request.credit() != null ? request.credit() : 2);
        curriculum.setStartDate(request.startDate());

        return curriculumRepository.save(curriculum);
    }

    public Optional<Curriculum> getById(Integer id) {
        return curriculumRepository.findById(id);
    }

    public List<Curriculum> getAllCurriculums() {


        List<Curriculum> list2 = new ArrayList<>();


        list2.addAll(curriculumRepository.findAll());

        System.out.println(list2);

        return list2;
    }

    @Transactional
    public Curriculum updateCurriculum(Integer id, CurriculumRequest request) {
        return curriculumRepository.findById(id).map(curriculum -> {
            if (request.curriculumName() != null) {
                curriculum.setCurriculumName(request.curriculumName());
            }

            if (request.teacherId() != null) {
                Customer teacher = customerRepository.findById(request.teacherId())
                        .orElseThrow(() -> new RuntimeException("Teacher not found"));
                curriculum.setTeacher(teacher);
            }

            if (request.lessonId() != null) {
                Lesson lesson = lessonRepository.findById(request.lessonId())
                        .orElseThrow(() -> new RuntimeException("Lesson not found"));
                curriculum.setLesson(lesson);
            }

            if (request.credit() != null) {
                curriculum.setCredit(request.credit());
            }

            if (request.startDate() != null) {
                curriculum.setStartDate(request.startDate());
            }

            return curriculumRepository.save(curriculum);
        }).orElseThrow(() -> new RuntimeException("Curriculum not found"));
    }

    @Transactional
    public void deleteCurriculum(Integer id) {
        curriculumRepository.deleteById(id);
    }
}