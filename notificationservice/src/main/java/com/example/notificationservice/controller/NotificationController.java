package com.example.notificationservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/notify")
public class NotificationController {

    @PostMapping("/enrollment")
    public ResponseEntity<String> notifyEnroll(@RequestBody Map<String, Object> body){
        Object s = body.get("studentId");
        Object c = body.get("courseId");

        String msg = "Student " + s + " enrolled into Course " + c;
        System.out.println(msg);

        return ResponseEntity.ok(msg);
    }
}
