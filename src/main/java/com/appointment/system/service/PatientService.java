package com.appointment.system.service;


import com.appointment.system.models.DTO.PatientDto;
import com.appointment.system.models.Entity.Patient;

import java.util.List;

public interface PatientService {
    Patient addPatient(PatientDto patient);

    void removePatient(Long patientId);

    List<Patient> getAllPatients();
}
