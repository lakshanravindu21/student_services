package com.example.enrollmentservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private Long courseId;

    public Enrollment(){}

    public Enrollment(Long studentId, Long courseId){
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public Long getStudentId(){ return studentId; }
    public void setStudentId(Long studentId){ this.studentId = studentId; }

    public Long getCourseId(){ return courseId; }
    public void setCourseId(Long courseId){ this.courseId = courseId; }
}
