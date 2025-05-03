package com.shubhi.attendance_service.service;


import com.shubhi.attendance_service.Client.FaceFeignClient;
import com.shubhi.attendance_service.dto.QRCodeRequest;
import com.shubhi.attendance_service.dto.QRCodeResponse;
import com.shubhi.attendance_service.entity.Attendance;
import com.shubhi.attendance_service.entity.QRCode;
import com.shubhi.attendance_service.repo.AttendanceRepository;
import com.shubhi.attendance_service.repo.QRCodeRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static org.springframework.boot.autoconfigure.container.ContainerImageMetadata.isPresent;

//@Service
@Component
//@RequiredArgsConstructor
public class Generation {

    private final QRCodeRepository qrCodeSessionRepository;
    private final AttendanceRepository attendanceRepository;
    private final FaceFeignClient faceRecognitionFeignClient;

    public Generation(QRCodeRepository qrCodeSessionRepository,
                      AttendanceRepository attendanceRepository,
                      FaceFeignClient faceRecognitionFeignClient) {
        this.qrCodeSessionRepository = qrCodeSessionRepository;
        this.attendanceRepository = attendanceRepository; // Initialized here
        this.faceRecognitionFeignClient = faceRecognitionFeignClient;
    }

    public boolean markAttendance(QRCodeRequest request, MultipartFile imageFile) {

        try {
            String result = faceRecognitionFeignClient.verifyFace(imageFile, request.getStudentId());
            System.out.println(result);
            if (result.equals(false)) {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Face recognition failed: " + e.getMessage());
        }

        QRCode qrCodeSession = qrCodeSessionRepository.findByQrData(request.getQrData())
                .filter(session -> session.getExpiresAt().isAfter(LocalDateTime.now()))
                .orElse(null);

        if (qrCodeSession == null) {
            return false;
        }

//        Attendance attendance = Attendance.builder()
//                .studentId(request.getStudentId())
//                .courseId(qrCodeSession.getCourseId())
//                .qrCodeId(qrCodeSession.getQrCodeId())
//                .timestamp(LocalDateTime.now())
//                .isPresent(true)
//                .build();
//
//        attendanceRepository.save(attendance);

        Attendance attendance = new Attendance();
        attendance.setStudentId(request.getStudentId());
        attendance.setCourseId(qrCodeSession.getCourseId());
        attendance.setQrCodeId(qrCodeSession.getQrCodeId());
        attendance.setTimestamp(LocalDateTime.now());
        attendance.setIsPresent(true);

//        attendanceRepository.save(attendance);


        attendanceRepository.save(attendance);

        return true;
    }



    public QRCodeResponse generateQRCode(QRCodeRequest request) {
        int otp = 10000 + new Random().nextInt(90000); // generates a number between 10000 and 99999
        String token = String.valueOf(otp);

        QRCode savedSession = new QRCode(
                request.getCourseId(),
                token,
                request.getLecturerId(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10)
        );
        // Save the QR code session to the database
        qrCodeSessionRepository.save(savedSession);

        // Return response with the generated QR code token

        QRCodeResponse response = new QRCodeResponse(
                savedSession.getQrCodeId(),
                savedSession.getQrData(),
                savedSession.getCourseId(),
                savedSession.getCreatedAt(),
                savedSession.getExpiresAt()
        );


        return response;
//        return QRCodeResponse.builder()
//                .qrCodeId(savedSession.getQrCodeId())
//                .qrData(savedSession.getQrData())
//                .courseId(savedSession.getCourseId())
//                .createdAt(savedSession.getCreatedAt())
//                .expiresAt(savedSession.getExpiresAt())
//                .build();
    }
}
