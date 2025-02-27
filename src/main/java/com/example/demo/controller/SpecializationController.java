package com.example.demo.controller;

import com.example.demo.entity.Specialization;
import com.example.demo.service.SpecializationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {

    private final SpecializationService specializationService;

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    // 创建专业


    @PostMapping("/create")
    public ResponseEntity<Specialization> create(@RequestBody Specialization specialization) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(specializationService.createSpecialization(specialization));
    }

    // 获取所有专业
    @GetMapping("/all")
    public ResponseEntity<List<Specialization>> getAll() {
        return ResponseEntity.ok(specializationService.getAllSpecializations());
    }

    // 根据ID获取专业
    @GetMapping("/search/{id}")
    public ResponseEntity<Specialization> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(specializationService.getSpecializationById(id));
    }

    // 更新专业
    @PutMapping("/update/{id}")
    public ResponseEntity<Specialization> update(
            @PathVariable Integer id,
            @RequestBody Specialization specialization) {
        return ResponseEntity.ok(specializationService.updateSpecialization(id, specialization));
    }

    // 删除专业
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        specializationService.deleteSpecialization(id);
        return ResponseEntity.noContent().build();
    }
}