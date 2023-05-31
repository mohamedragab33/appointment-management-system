package com.appointment.system.repository;

import com.appointment.system.models.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment> findAllByDate(LocalDate date);
    List<Appointment> findAllByPatientNameContainingIgnoreCase(String patientName);
    List<Appointment> findAllByDateAndPatientNameContainingIgnoreCase(LocalDate date, String patientName);
    List<Appointment> findAllByPatientId(Long patientId);
}
