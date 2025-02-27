package com.example.demo.service;


import com.example.demo.entity.Specialization;
import com.example.demo.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecializationService {

    private final SpecializationRepository specializationRepository;

    // 创建专业
    @Transactional
    public Specialization createSpecialization(Specialization specialization) {
        if (specializationRepository.existsByName(specialization.getName())) {
            throw new RuntimeException("专业名称已存在: " + specialization.getName());
        }
        return specializationRepository.save(specialization);
    }

    // 获取所有专业
    public List<Specialization> getAllSpecializations() {



        return specializationRepository.findAll();


    }

    // 根据ID获取专业
    public Specialization getSpecializationById(Integer id) {
        return specializationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("专业未找到，ID: " + id));
    }

    // 更新专业
    @Transactional
    public Specialization updateSpecialization(Integer id, Specialization updated) {
        Specialization existing = getSpecializationById(id);

        // 检查名称是否重复（排除自身）
        if (!existing.getName().equals(updated.getName()) &&
                specializationRepository.existsByName(updated.getName())) {
            throw new RuntimeException("专业名称已存在: " + updated.getName());
        }

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        return specializationRepository.save(existing);
    }

    // 删除专业
    @Transactional
    public void deleteSpecialization(Integer id) {
        if (!specializationRepository.existsById(id)) {
            throw new RuntimeException("删除失败，专业未找到，ID: " + id);
        }
        specializationRepository.deleteById(id);
    }
}