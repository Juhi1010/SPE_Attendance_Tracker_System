package com.shubhi.attendance_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Builder
public class QRCodeResponse {
    private Long qrCodeId;
    private String qrData;
    private String courseId;
//    private Long lecturerId;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public QRCodeResponse() {

    }

    public QRCodeResponse(Long qrCodeId, String qrData, String courseId, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.qrCodeId = qrCodeId;
        this.qrData = qrData;
        this.courseId = courseId;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    // Getters (optional, as needed)
    public Long getQrCodeId() {
        return qrCodeId;
    }

    public String getQrData() {
        return qrData;
    }

    public String getCourseId() {
        return courseId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
