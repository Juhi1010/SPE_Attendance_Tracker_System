package com.shubhi.attendance_service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubhi.attendance_service.dto.QRCodeRequest;
import com.shubhi.attendance_service.dto.QRCodeResponse;
import com.shubhi.attendance_service.service.Generation;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/qrcodes")
//@RequiredArgsConstructor
public class QR_code_controller {

//    @Autowired
    private final Generation qrCodeService;
//    @Autowired
    private ObjectMapper objectMapper;

    // Manually created constructor to inject dependencies
    public QR_code_controller(Generation qrCodeService, ObjectMapper objectMapper) {
        this.qrCodeService = qrCodeService;  // Initializing qrCodeService
        this.objectMapper = objectMapper;    // Initializing objectMapper
    }

    /**
     * Generate a QR Code for a course by a lecturer.
     */
    @PostMapping("/generate")
    public ResponseEntity<QRCodeResponse> generateQRCode(@RequestBody QRCodeRequest request) {
        QRCodeResponse qrResponse = qrCodeService.generateQRCode(request);
        return ResponseEntity.ok(qrResponse);
    }

    /**
     * Mark attendance by scanning QR code.
     */
//    @PostMapping(value = "/mark", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> markAttendanceViaQRCode(
//            @RequestPart("data") QRCodeRequest request,
//            @RequestPart("image") MultipartFile image
//    ) {
//        boolean success = qrCodeService.markAttendance(request, image);
//        if (success) {
//            return ResponseEntity.ok("Attendance marked successfully!");
//        } else {
//            return ResponseEntity.badRequest().body("Face verification failed or invalid/expired QR code.");
//        }
//    }
    @PostMapping(value = "/mark", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> markAttendanceViaQRCode(
            @RequestPart("data") String jsonRequest,
            @RequestPart("image") MultipartFile image
    ) {
        try {
            QRCodeRequest request = objectMapper.readValue(jsonRequest, QRCodeRequest.class);
            boolean success = qrCodeService.markAttendance(request, image);
            if (success) {
                return ResponseEntity.ok("Attendance marked successfully!");
            } else {
                return ResponseEntity.ok("Face verification failed or invalid/expired QR code.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid request payload: " + e.getMessage());
        }
    }
}

