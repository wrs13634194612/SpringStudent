package com.example.demo.controller;


import com.example.demo.entity.Curriculum;
import com.example.demo.service.CurriculumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/curriculums")
public class CurriculumController {

    @Autowired
    private CurriculumService curriculumService;

    @PostMapping("/create")
    public ResponseEntity<?> createCurriculum(@Valid @RequestBody CurriculumRequest request) {
        try {
            Curriculum created = curriculumService.createCurriculum(request);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Curriculum> getCurriculum(@PathVariable Integer id) {
        return curriculumService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/all")
    public List<Curriculum> getAllCurriculums() {
        return curriculumService.getAllCurriculums();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCurriculum(@PathVariable Integer id,
                                              @Valid @RequestBody CurriculumRequest request) {
        try {
            Curriculum updated = curriculumService.updateCurriculum(id, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCurriculum(@PathVariable Integer id) {
        curriculumService.deleteCurriculum(id);
        return ResponseEntity.noContent().build();
    }

    // 请求体DTO
    public static record CurriculumRequest(
            @NotBlank String curriculumName,
            @NotNull Integer teacherId,
            @NotNull Integer lessonId,
            @Min(0) @Max(255) Integer credit,
            LocalDate startDate
    ) {}
}