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

    /**
     * Method under test: {@link PatientServiceImpl#addPatient(PatientDto)}
     */
    @Test
    void testAddPatient() {
        Patient patient = new Patient("Name");
        when(patientRepository.save(Mockito.<Patient>any())).thenReturn(patient);

        PatientDto patient2 = new PatientDto();
        patient2.setPatientName("Patient Name");
        assertSame(patient, patientServiceImpl.addPatient(patient2));
        verify(patientRepository).save(Mockito.<Patient>any());
    }

    /**
     * Method under test: {@link PatientServiceImpl#addPatient(PatientDto)}
     */
    @Test
    void testAddPatient2() {
        when(patientRepository.save(Mockito.<Patient>any())).thenThrow(new IllegalArgumentException());

        PatientDto patient = new PatientDto();
        patient.setPatientName("Patient Name");
        assertThrows(IllegalArgumentException.class, () -> patientServiceImpl.addPatient(patient));
        verify(patientRepository).save(Mockito.<Patient>any());
    }

    /**
     * Method under test: {@link PatientServiceImpl#removePatient(Long)}
     */
    @Test
    void testRemovePatient() {
        doNothing().when(patientRepository).deleteById(Mockito.<Long>any());
        when(patientRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Patient("Name")));
        patientServiceImpl.removePatient(1L);
        verify(patientRepository).findById(Mockito.<Long>any());
        verify(patientRepository).deleteById(Mockito.<Long>any());
        assertTrue(patientServiceImpl.getAllPatients().isEmpty());
    }

    /**
     * Method under test: {@link PatientServiceImpl#removePatient(Long)}
     */
    @Test
    void testRemovePatient2() {
        doThrow(new IllegalArgumentException()).when(patientRepository).deleteById(Mockito.<Long>any());
        when(patientRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Patient("Name")));
        assertThrows(IllegalArgumentException.class, () -> patientServiceImpl.removePatient(1L));
        verify(patientRepository).findById(Mockito.<Long>any());
        verify(patientRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link PatientServiceImpl#removePatient(Long)}
     */
    @Test
    void testRemovePatient3() {
        doNothing().when(patientRepository).deleteById(Mockito.<Long>any());
        when(patientRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> patientServiceImpl.removePatient(1L));
        verify(patientRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link PatientServiceImpl#getAllPatients()}
     */
    @Test
    void testGetAllPatients() {
        ArrayList<Patient> patientList = new ArrayList<>();
        when(patientRepository.findAll()).thenReturn(patientList);
        List<Patient> actualAllPatients = patientServiceImpl.getAllPatients();
        assertSame(patientList, actualAllPatients);
        assertTrue(actualAllPatients.isEmpty());
        verify(patientRepository).findAll();
    }

    /**
     * Method under test: {@link PatientServiceImpl#getAllPatients()}
     */
    @Test
    void testGetAllPatients2() {
        when(patientRepository.findAll()).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> patientServiceImpl.getAllPatients());
        verify(patientRepository).findAll();
    }
}

