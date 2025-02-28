package com.example.demo.controller;



import com.example.demo.entity.Customer;
import com.example.demo.entity.Lesson;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.LessonRepository;
import com.example.demo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerRequest request) {
        try {
            Customer created = customerService.createCustomer(request);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Integer id) {
        return customerService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer id, @Valid @RequestBody CustomerRequest request) {
        try {
            Customer updated = customerService.updateCustomer(id, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    // DTO for request body
    public static record CustomerRequest(
            String username,
            String password,
            Customer.UserRole role,
            String realName,
            Customer.Gender gender,
            String email,
            Integer lessonId
    ) {}
}