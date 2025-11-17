package com.example.enrollmentservice.controller;

import com.example.enrollmentservice.entity.Enrollment;
import com.example.enrollmentservice.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.*;

@RestController
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository repo;

    @Autowired
    private WebClient webClient;

    @PostMapping("/enroll")
    public ResponseEntity<?> enroll(@RequestBody Map<String, Long> body){
        Long studentId = body.get("studentId");
        Long courseId = body.get("courseId");

        if(studentId == null || courseId == null)
            return ResponseEntity.badRequest().body("studentId & courseId required");

        // Validate student
        try {
            webClient.get()
                    .uri("http://localhost:8081/students/{id}", studentId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException.NotFound ex){
            return ResponseEntity.status(404).body("Invalid student ID");
        }

        // Validate course
        try {
            webClient.get()
                    .uri("http://localhost:8082/courses/{id}", courseId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException.NotFound ex){
            return ResponseEntity.status(404).body("Invalid course ID");
        }

        // Save enrollment
        Enrollment saved = repo.save(new Enrollment(studentId, courseId));

        // Notify
        Map<String, Long> notifyObj = Map.of("studentId", studentId, "courseId", courseId);

        try {
            webClient.post()
                    .uri("http://localhost:8085/notify/enrollment")
                    .bodyValue(notifyObj)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e){
            System.out.println("Notification Failed: " + e.getMessage());
        }

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/enrollments/student/{id}")
    public ResponseEntity<List<Enrollment>> getByStudent(@PathVariable Long id){
        return ResponseEntity.ok(repo.findByStudentId(id));
    }
}
