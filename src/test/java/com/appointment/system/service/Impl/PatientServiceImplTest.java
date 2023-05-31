package com.appointment.system.service.Impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.appointment.system.models.DTO.PatientDto;
import com.appointment.system.models.Entity.Patient;
import com.appointment.system.repository.PatientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PatientServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PatientServiceImplTest {
    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private PatientServiceImpl patientServiceImpl;


    @Test
    void testAddPatient() {
        Patient patient = new Patient("Name", "jane.doe@example.org", "6625550144");

        when(patientRepository.save(Mockito.<Patient>any())).thenReturn(patient);

        PatientDto patient2 = new PatientDto();
        patient2.setEmail("jane.doe@example.org");
        patient2.setPatientName("Patient Name");
        patient2.setPhone("6625550144");
        assertSame(patient, patientServiceImpl.addPatient(patient2));
        verify(patientRepository).save(Mockito.<Patient>any());
    }


    @Test
    void testAddPatient2() {
        when(patientRepository.save(Mockito.<Patient>any())).thenThrow(new IllegalArgumentException());

        PatientDto patient = new PatientDto();
        patient.setEmail("jane.doe@example.org");
        patient.setPatientName("Patient Name");
        patient.setPhone("6625550144");
        assertThrows(IllegalArgumentException.class, () -> patientServiceImpl.addPatient(patient));
        verify(patientRepository).save(Mockito.<Patient>any());
    }

    @Test
    void testRemovePatient() {
        doNothing().when(patientRepository).deleteById(Mockito.<Long>any());
        when(patientRepository.findById(Mockito.<Long>any()))
                .thenReturn(Optional.of(new Patient("Name", "jane.doe@example.org", "6625550144")));
        patientServiceImpl.removePatient(1L);
        verify(patientRepository).findById(Mockito.<Long>any());
        verify(patientRepository).deleteById(Mockito.<Long>any());
        assertTrue(patientServiceImpl.getAllPatients().isEmpty());
    }

    @Test
    void testRemovePatient2() {
        doThrow(new IllegalArgumentException()).when(patientRepository).deleteById(Mockito.<Long>any());
        when(patientRepository.findById(Mockito.<Long>any()))
                .thenReturn(Optional.of(new Patient("Name", "jane.doe@example.org", "6625550144")));
        assertThrows(IllegalArgumentException.class, () -> patientServiceImpl.removePatient(1L));
        verify(patientRepository).findById(Mockito.<Long>any());
        verify(patientRepository).deleteById(Mockito.<Long>any());
    }

    @Test
    void testRemovePatient3() {
        doNothing().when(patientRepository).deleteById(Mockito.<Long>any());
        when(patientRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientServiceImpl.removePatient(1L));
        verify(patientRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetAllPatients() {
        ArrayList<Patient> patientList = new ArrayList<>();
        when(patientRepository.findAll()).thenReturn(patientList);
        List<Patient> actualAllPatients = patientServiceImpl.getAllPatients();
        assertSame(patientList, actualAllPatients);
        assertTrue(actualAllPatients.isEmpty());
        verify(patientRepository).findAll();
    }

    @Test
    void testGetAllPatients2() {
        when(patientRepository.findAll()).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> patientServiceImpl.getAllPatients());
        verify(patientRepository).findAll();
    }
}

