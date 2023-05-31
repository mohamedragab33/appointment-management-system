package com.appointment.system.controller;

import com.appointment.system.models.DTO.PatientDto;
import com.appointment.system.models.Entity.Patient;
import com.appointment.system.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;



    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }




    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody PatientDto patient) {
        Patient newPatient = patientService.addPatient(patient);
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePatient(@PathVariable("id") Long patientId) {
        patientService.removePatient(patientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}