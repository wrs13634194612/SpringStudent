package com.example.demo.service;



import com.example.demo.controller.EvaluationController.EvaluationRequest;
import com.example.demo.entity.*;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.CurriculumRepository;
import com.example.demo.repository.EvaluationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Transactional
    public Evaluation createEvaluation(EvaluationRequest request) {
        // 检查唯一约束
        if (evaluationRepository.existsByStudentIdAndCurriculumId(
                request.studentId(), request.curriculumId())) {
            throw new DataIntegrityViolationException("该学生在此课程下已存在评价");
        }
        Customer student = customerRepository.findById(request.studentId().intValue())
                .orElseThrow(() -> new RuntimeException("学生不存在"));

        Curriculum curriculum = curriculumRepository.findById(request.curriculumId().intValue())
                .orElseThrow(() -> new RuntimeException("课程不存在"));

        Evaluation evaluation = new Evaluation();
        evaluation.setStudent(student);
        evaluation.setCurriculum(curriculum);
        evaluation.setEvaluation(request.evaluation() != null ?
                request.evaluation() : BigDecimal.ZERO);
        evaluation.setSemester(request.semester());

        return evaluationRepository.save(evaluation);
    }

    public Optional<Evaluation> getById(Long id) {
        return evaluationRepository.findById(id)
                .map(eval -> {
                    // 触发关联加载
                    eval.getStudent().getId();
                    eval.getCurriculum().getId();
                    return eval;
                });
    }

    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    @Transactional
    public Evaluation updateEvaluation(Long id, EvaluationRequest request) {
        return evaluationRepository.findById(id).map(eval -> {
            // 更新关联实体
            if (request.studentId() != null) {
                Customer student = customerRepository.findById(request.studentId().intValue())
                        .orElseThrow(() -> new RuntimeException("学生不存在"));
                eval.setStudent(student);
            }

            if (request.curriculumId() != null) {
                Curriculum curriculum = curriculumRepository.findById(request.curriculumId().intValue())
                        .orElseThrow(() -> new RuntimeException("课程不存在"));
                eval.setCurriculum(curriculum);
            }

            if (request.evaluation() != null) {
                eval.setEvaluation(request.evaluation());
            }

            if (request.semester() != null) {
                eval.setSemester(request.semester());
            }

            return evaluationRepository.save(eval);
        }).orElseThrow(() -> new RuntimeException("评价记录不存在"));
    }

    @Transactional
    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }
}