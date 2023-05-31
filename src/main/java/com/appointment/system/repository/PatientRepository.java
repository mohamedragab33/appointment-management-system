package com.appointment.system.repository;

import com.appointment.system.models.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository  extends JpaRepository<Patient, Long> {
}
