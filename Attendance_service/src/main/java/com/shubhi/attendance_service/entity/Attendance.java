//package com.shubhi.attendance_service.entity;
//
//
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "attendance")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Attendance {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long attendanceId;
//
//    @Column(nullable = false)
//    private Long studentId;
//
//    @Column(nullable = false)
//    private String courseId;
//
//    @Column(nullable = false)
//    private Long qrCodeId;
//
//    @Column(nullable = false)
//    private LocalDateTime timestamp;
//
//    @Column(nullable = false)
//    private Boolean isPresent;
//
//    // Getter and Setter for attendanceId
//    public Long getAttendanceId() {
//        return attendanceId;
//    }
//
//    public void setAttendanceId(Long attendanceId) {
//        this.attendanceId = attendanceId;
//    }
//
//    // Getter and Setter for studentId
//    public Long getStudentId() {
//        return studentId;
//    }
//
//    public void setStudentId(Long studentId) {
//        this.studentId = studentId;
//    }
//
//    // Getter and Setter for courseId
//    public String getCourseId() {
//        return courseId;
//    }
//
//    public void setCourseId(String courseId) {
//        this.courseId = courseId;
//    }
//
//    // Getter and Setter for qrCodeId
//    public Long getQrCodeId() {
//        return qrCodeId;
//    }
//
//    public void setQrCodeId(Long qrCodeId) {
//        this.qrCodeId = qrCodeId;
//    }
//
//    // Getter and Setter for timestamp
//    public LocalDateTime getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(LocalDateTime timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    // Getter and Setter for isPresent
//    public Boolean getIsPresent() {
//        return isPresent;
//    }
//
//    public void setIsPresent(Boolean isPresent) {
//        this.isPresent = isPresent;
//    }
//
//}

package com.shubhi.attendance_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private String courseId;

    @Column(nullable = false)
    private Long qrCodeId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Boolean isPresent;

    public Attendance() {
        // Empty constructor required by JPA
    }

    // Getter and Setter for attendanceId
    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    // Getter and Setter for studentId
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    // Getter and Setter for courseId
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    // Getter and Setter for qrCodeId
    public Long getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(Long qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    // Getter and Setter for timestamp
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Getter and Setter for isPresent
    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean isPresent) {
        this.isPresent = isPresent;
    }

    // Optional: If needed, manually define a public all-arguments constructor
    public Attendance(Long studentId, String courseId, Long qrCodeId, LocalDateTime timestamp, Boolean isPresent) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.qrCodeId = qrCodeId;
        this.timestamp = timestamp;
        this.isPresent = isPresent;
    }

}
