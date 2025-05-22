package com.juhi.spe_major.user.feign;

import com.juhi.spe_major.user.model.QRCodeRequest;
import com.juhi.spe_major.user.model.QRCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

//@FeignClient("ATTENDANCE-SERVICE")
@FeignClient(name = "attendance-service", url = "http://attendance-service:8083")
public interface UserFeignClient {
    @PostMapping("/api/qrcodes/generate")
    public ResponseEntity<QRCodeResponse> generateQRCode(@RequestBody QRCodeRequest request);

    // Updated method for marking attendance
    @PostMapping(value = "/api/qrcodes/mark", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> markAttendanceViaQRCode(
            @RequestPart("data") String jsonRequest,
            @RequestPart("image") MultipartFile image
    );
}

