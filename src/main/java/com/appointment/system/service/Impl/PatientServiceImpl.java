package com.appointment.system.service.Impl;

import com.appointment.system.models.DTO.PatientDto;
import com.appointment.system.models.Entity.Patient;
import com.appointment.system.repository.PatientRepository;
import com.appointment.system.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository ;
    @Override
    public Patient addPatient(PatientDto patient) {
        Patient patient1 = new Patient(patient.getPatientName());
        return patientRepository.save(patient1);
    }

    @Override
    public void removePatient(Long patientId) {
       patientRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Invalid Patient ID"));
        patientRepository.deleteById(patientId);
    }

    @Override
    public List<Patient> getAllPatients() {
     return patientRepository.findAll();
    }


}
