package com.juhi.spe_major.user.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//
//public class QRCodeRequest {
//
//        private String courseId;
//        private Long lecturerId;
//        private Long studentId;  // Optional: for attendance scan
//        private String qrData;   // Used during scan
//
//
//    }

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class QRCodeRequest implements Serializable {

    @JsonProperty("courseId")
    private String courseId;

    @JsonProperty("lecturerId")
    private Long lecturerId;

    @JsonProperty("studentId")
    private Long studentId;

    @JsonProperty("qrData")
    private String qrData;

    // No-args constructor
    public QRCodeRequest() {
    }

    // All-args constructor
    public QRCodeRequest(String courseId, Long lecturerId, Long studentId, String qrData) {
        this.courseId = courseId;
        this.lecturerId = lecturerId;
        this.studentId = studentId;
        this.qrData = qrData;
    }

    // Getters
    public String getCourseId() {
        return courseId;
    }

    public Long getLecturerId() {
        return lecturerId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getQrData() {
        return qrData;
    }

    // Setters
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setQrData(String qrData) {
        this.qrData = qrData;
    }
}