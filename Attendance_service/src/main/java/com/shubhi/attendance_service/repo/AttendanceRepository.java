package com.shubhi.attendance_service.repo;
import com.shubhi.attendance_service.entity.Attendance;
import com.shubhi.attendance_service.entity.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {}

