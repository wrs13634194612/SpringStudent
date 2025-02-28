package com.example.demo.controller;


import com.example.demo.entity.Communication;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CommunicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
        import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/communications")
public class CommunicationController {

    @Autowired
    private CommunicationService communicationService;

    @PostMapping("/create")
    public ResponseEntity<?> createCommunication(@Valid @RequestBody CommunicationRequest request) {
        try {
            Communication created = communicationService.createCommunication(request);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Communication> getCommunication(@PathVariable Integer id) {
        return communicationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/all")
    public List<Communication> getAllCommunications() {
        return communicationService.getAllCommunications();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCommunication(@PathVariable Integer id,
                                                 @Valid @RequestBody CommunicationRequest request) {
        try {
            Communication updated = communicationService.updateCommunication(id, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCommunication(@PathVariable Integer id) {
        communicationService.deleteCommunication(id);
        return ResponseEntity.noContent().build();
    }

    // 请求体DTO
    public static record CommunicationRequest(
            @NotBlank String title,
            @NotBlank String content,
            @NotNull Integer senderId,
            Integer receiverId
    ) {}
}