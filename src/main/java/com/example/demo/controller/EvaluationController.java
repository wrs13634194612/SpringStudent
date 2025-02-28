package com.example.demo.controller;


import com.example.demo.entity.Evaluation;
import com.example.demo.service.EvaluationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
        import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/create")
    public ResponseEntity<?> createEvaluation(@Valid @RequestBody EvaluationRequest request) {
        try {
            Evaluation created = evaluationService.createEvaluation(request);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Evaluation> getEvaluation(@PathVariable Long id) {
        return evaluationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/all")
    public List<Evaluation> getAllEvaluations() {
        return evaluationService.getAllEvaluations();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEvaluation(@PathVariable Long id,
                                              @Valid @RequestBody EvaluationRequest request) {
        try {
            Evaluation updated = evaluationService.updateEvaluation(id, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        evaluationService.deleteEvaluation(id);
        return ResponseEntity.noContent().build();
    }

    // 请求体DTO
    public static record EvaluationRequest(
            @NotNull Long studentId,
            @NotNull Long curriculumId,
            @DecimalMin("0.00") @DecimalMax("100.00") BigDecimal evaluation,
            String semester
    ) {}
}