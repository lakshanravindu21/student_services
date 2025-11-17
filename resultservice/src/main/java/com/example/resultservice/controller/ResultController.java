package com.example.resultservice.controller;

import com.example.resultservice.entity.Result;
import com.example.resultservice.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private ResultRepository repo;

    @PostMapping
    public ResponseEntity<Result> create(@RequestBody Result r){
        return ResponseEntity.ok(repo.save(r));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<List<Result>> getByStudent(@PathVariable Long id){
        return ResponseEntity.ok(repo.findByStudentId(id));
    }
}
