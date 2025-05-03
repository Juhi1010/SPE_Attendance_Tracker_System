package com.shubhi.attendance_service.repo;
import com.shubhi.attendance_service.entity.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QRCodeRepository extends JpaRepository<QRCode, Long> {
    Optional<QRCode> findByQrData(String qrData); // ✅ correct method
}
