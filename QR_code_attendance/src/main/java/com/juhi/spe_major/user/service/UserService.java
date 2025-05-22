package com.juhi.spe_major.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juhi.spe_major.user.feign.UserFeignClient;
import com.juhi.spe_major.user.config.JWTUtility;
import com.juhi.spe_major.user.model.*;
import com.juhi.spe_major.user.entity.User;
import com.juhi.spe_major.user.repository.UserRepository;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;
    private JWTUtility jwtUtility;
    @Autowired
    UserFeignClient userFeignClient;

    @Autowired
    public UserService(UserRepository userRepository, JWTUtility jwtUtility) {
        this.userRepository = userRepository;
        this.jwtUtility = jwtUtility;  // Inject JWTUtility here
    }

//    @Value("${images.folder.path}")
//    private String imageDirPath;

    // Register User Method
    public ResponseEntity<UserResponseDTO> registerUser(userRegisterDto dto, MultipartFile imageFile) {
//        if (!"STUDENT".equalsIgnoreCase(dto.getRole())) {
//            return ResponseEntity.badRequest().body(new UserResponseDTO(null, null, "Image path is required for STUDENT role"));
//        }

        User existingUser = userRepository.findByEmailAndRole(dto.getEmail(), dto.getRole());

        if (existingUser != null) {
            return ResponseEntity.badRequest().body(new UserResponseDTO(null, null, "User already exists with this email and role"));
        }


        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());
        user.setImagePath(dto.getImagePath());
        user.setPassword("tree");

        User savedUser = userRepository.save(user);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
//                String fileName = savedUser.getUserid() + ".jpg";
////                String imageDirPath = "QR_code_attendance/images";
//                String projectRootDir = Paths.get("").toAbsolutePath().getParent().toString();  // Get the parent directory of the project
//                String imageDirPath = Paths.get(projectRootDir, "images").toString();

                String fileName = savedUser.getUserid() + ".jpg";
                String imageDirPath = "/app/images";

//                Path imagePath = Paths.get(imageDirPath, fileName);
//                Files.write(imagePath, imageBytes); // or your image save logic



                File imageDir = new File(imageDirPath);

                if (!imageDir.exists()) {
                    boolean dirCreated = imageDir.mkdirs();
                    if (!dirCreated) {
                        throw new IOException("Failed to create image directory.");
                    }
                }

                Path imagePath = imageDir.toPath().resolve(fileName);
                Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

                savedUser.setImagePath(fileName);
                userRepository.save(savedUser);

            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserResponseDTO(null, null, "Error saving image file: " + e.getMessage()));
            }
        }

        UserResponseDTO responseDTO = new UserResponseDTO(savedUser.getUserid(), savedUser.getEmail(), savedUser.getPassword());
        return ResponseEntity.ok(responseDTO);
    }

    // Login Method
    public ResponseEntity<?> loginChecking(LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate password
        if (!request.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // Generate JWT token
        String token = jwtUtility.generateToken(user.getEmail(), user.getRole()); // Get email and role from user

        return ResponseEntity.ok(Map.of(
                "message", "Login Successful",
                "token", token
        ));
    }
    public QRCodeResponse generateQRCode(QRCodeRequest request){
        QRCodeResponse qrCodeResponse =userFeignClient.generateQRCode(request).getBody();
        return qrCodeResponse;
    }
    public String markAttendance(QRCodeRequest request, MultipartFile image) {
        try {
            // Convert the QRCodeRequest to JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(request);

            // Call the Feign client method with the JSON request and image
            ResponseEntity<String> response = userFeignClient.markAttendanceViaQRCode(jsonRequest, image);

            // Return the result from the Feign client response
            return response.getBody();
        } catch (Exception e) {
            // Handle any exceptions that might occur during the Feign client call or object mapping
            return "Error: " + e.getMessage();
        }
    }

    // QRCodeController.java

}