//package com.shubhi.attendance_service.entity;
//
//
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Entity
//@Table(name = "qr_codes")
//@Data
////@NoArgsConstructor
////@AllArgsConstructor
//@Builder
//public class QRCode {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long qrCodeId;
//
//    @Column(nullable = false)
//    private String courseId;
//
//    @Column(nullable = false)
//    private Long lecturerId;
//
//    @Column(nullable = false, unique = true, length = 2048)
//    private String qrData;  // This holds the token or encoded string used in the QR.
//
//    @Column(nullable = false)
//    private LocalDateTime createdAt;
//
//    @Column(nullable = false)
//    private LocalDateTime expiresAt;
//    public QRCode() {
//        // Empty constructor required by JPA
//    }
//
//    public QRCode(String courseId, String qrData, Long lecturerId, LocalDateTime createdAt, LocalDateTime expiresAt) {
//        this.courseId = courseId;
//        this.qrData = qrData;
//        this.lecturerId = lecturerId;
//        this.createdAt = createdAt;
//        this.expiresAt = expiresAt;
//    }
//
//    public Long getQrCodeId() {
//        return qrCodeId;
//    }
//
//    public String getCourseId() {
//        return courseId;
//    }
//
//    public Long getLecturerId() {
//        return lecturerId;
//    }
//
//    public String getQrData() {
//        return qrData;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public LocalDateTime getExpiresAt() {
//        return expiresAt;
//    }
//
//}


package com.shubhi.attendance_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "qr_codes")
public class QRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qrCodeId;

    private String courseId;
    private String qrData;
    private Long lecturerId;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    // No-args constructor
    public QRCode() {}

    // All-args constructor (including ID) – only use if needed
    public QRCode(Long qrCodeId, String courseId, String qrData, Long lecturerId, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.qrCodeId = qrCodeId;
        this.courseId = courseId;
        this.qrData = qrData;
        this.lecturerId = lecturerId;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    // Constructor used in your service
    public QRCode(String courseId, String qrData, Long lecturerId, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.courseId = courseId;
        this.qrData = qrData;
        this.lecturerId = lecturerId;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    // Getters and Setters
    public Long getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(Long qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getQrData() {
        return qrData;
    }

    public void setQrData(String qrData) {
        this.qrData = qrData;
    }

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
