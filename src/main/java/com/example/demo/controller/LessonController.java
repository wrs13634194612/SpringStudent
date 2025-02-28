package com.example.demo.controller;



import com.example.demo.entity.Lesson;
import com.example.demo.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @PostMapping("/create")
    public ResponseEntity<?> createLesson(@RequestBody LessonRequest request) {
        try {
            Lesson created = lessonService.createLesson(request);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Lesson> getLesson(@PathVariable Integer id) {
        return lessonService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLesson(@PathVariable Integer id, @RequestBody LessonRequest request) {
        try {
            Lesson updated = lessonService.updateLesson(id, request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Integer id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }

    // DTO for request body
    public static record LessonRequest(
            String lessonName,
            Integer grade,
            Integer specializationId
    ) {}
}