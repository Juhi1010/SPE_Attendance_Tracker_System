package com.shubhi.attendance_service.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "FACE-RECOGNITION-SERVICE")
public interface FaceFeignClient {

    @PostMapping(value = "/verify-face", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String verifyFace(@RequestPart("image") MultipartFile image,
                      @RequestParam("studentId") Long studentId);
}
