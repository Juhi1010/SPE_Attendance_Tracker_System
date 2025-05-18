package com.juhi.spe_major.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juhi.spe_major.user.config.JWTUtility;
import com.juhi.spe_major.user.model.*;
import com.juhi.spe_major.user.entity.User;
import com.juhi.spe_major.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Component
@RestController
@RequestMapping("/api/users")
public class AuthController {


    private final UserService userService;

    private final JWTUtility jwtUtility;
    private ObjectMapper objectMapper;

    @Autowired
    public AuthController( UserService userService, ObjectMapper objectMapper) {
        this.jwtUtility = new JWTUtility();
        this.userService = userService;
        this.objectMapper = objectMapper;
    }


    @PostMapping(value="/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> registerUser(@RequestPart("user") String user,@RequestPart("image") MultipartFile imageFile) {
        // Register the user
        try {
            userRegisterDto request = objectMapper.readValue(user, userRegisterDto.class);

            UserResponseDTO registeredUser = userService.registerUser(request, imageFile).getBody();

            // Return the response
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid request payload" + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        return userService.loginChecking(request);
    }
    @PostMapping("/generate")
    public ResponseEntity<QRCodeResponse> obtainQRCode(@RequestHeader("Authorization") String token,@RequestBody QRCodeRequest request) {
        String cleanedToken = token.substring(7);
        if (!jwtUtility.validateToken(cleanedToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        QRCodeResponse qrResponse = userService.generateQRCode(request);
        return ResponseEntity.ok(qrResponse);
    }

    @PostMapping(value = "/scan", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> markAttendanceViaQRCode(
            @RequestHeader("Authorization") String token,
            @RequestPart("data") String jsonRequest,
            @RequestPart("image") MultipartFile image
    ) {
        String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        // Validate the JWT token
        if (!jwtUtility.validateToken(cleanedToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Map the QRCodeRequest from the JSON string
        QRCodeRequest request;
        try {
            request = objectMapper.readValue(jsonRequest, QRCodeRequest.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid JSON format for QRCodeRequest: " + e.getMessage());
        }

        String success = userService.markAttendance(request,image);

        return ResponseEntity.ok(success);
    }

//    @PostMapping(value = "/scan", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> markAttendanceViaQRCode(
//            @RequestHeader("Authorization") String token,
//            @RequestPart("data") String jsonRequest,
//            @RequestPart("image") MultipartFile image
//    ) {
//        String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
//
//        // Validate the JWT token
//        if (!jwtUtility.validateToken(cleanedToken)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        // Map the QRCodeRequest from the JSON string
//        QRCodeRequest request;
//        try {
//            request = objectMapper.readValue(jsonRequest, QRCodeRequest.class);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Invalid JSON format for QRCodeRequest: " + e.getMessage());
//        }
//
//        // Call the Feign client to mark the attendance in the attendance service
////        ResponseEntity<String> response = attendanceFeignClient.markAttendanceViaQRCode(jsonRequest, image);
//
//        // Return the response from the Feign client
//        return response;
//    }

//    @PostMapping("/scan")
//    public ResponseEntity<String> markAttendanceViaQRCode(@RequestHeader("Authorization") String token,@RequestBody QRCodeRequest request) {
//        String cleanedToken = token.substring(7);
//        if (!jwtUtility.validateToken(cleanedToken)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        String success = userService.markAttendance(request);
//
//        return ResponseEntity.ok(success);
//    }

}