package com.appointment.system.service.Impl;

import com.appointment.system.models.DTO.AppointmentDto;
import com.appointment.system.models.DTO.AppointmentResponse;
import com.appointment.system.models.Entity.Appointment;
import com.appointment.system.models.Entity.Patient;
import com.appointment.system.repository.AppointmentRepository;
import com.appointment.system.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AppointmentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AppointmentServiceImplTest {
    @MockBean
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentServiceImpl appointmentServiceImpl;

    @MockBean
    private PatientRepository patientRepository;

    @Test
    void testGetTodayAppointments() {
        when(appointmentRepository.findAllByDate(Mockito.<LocalDate>any())).thenReturn(new ArrayList<>());
        assertTrue(appointmentServiceImpl.getTodayAppointments().isEmpty());
        verify(appointmentRepository).findAllByDate(Mockito.<LocalDate>any());
    }

    @Test
    void testGetTodayAppointments2() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(new Appointment());
        when(appointmentRepository.findAllByDate(Mockito.<LocalDate>any())).thenReturn(appointmentList);
        List<AppointmentResponse> actualTodayAppointments = appointmentServiceImpl.getTodayAppointments();
        assertEquals(1, actualTodayAppointments.size());
        AppointmentResponse getResult = actualTodayAppointments.get(0);
        assertNull(getResult.getCancellationReason());
        assertNull(getResult.getPatient());
        assertNull(getResult.getDate());
        verify(appointmentRepository).findAllByDate(Mockito.<LocalDate>any());
    }

    @Test
    void testGetTodayAppointments3() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(new Appointment());
        appointmentList.add(new Appointment());
        when(appointmentRepository.findAllByDate(Mockito.<LocalDate>any())).thenReturn(appointmentList);
        List<AppointmentResponse> actualTodayAppointments = appointmentServiceImpl.getTodayAppointments();
        assertEquals(2, actualTodayAppointments.size());
        AppointmentResponse getResult = actualTodayAppointments.get(0);
        assertNull(getResult.getPatient());
        AppointmentResponse getResult2 = actualTodayAppointments.get(1);
        assertNull(getResult2.getPatient());
        assertNull(getResult2.getDate());
        assertNull(getResult2.getCancellationReason());
        assertNull(getResult.getDate());
        assertNull(getResult.getCancellationReason());
        verify(appointmentRepository).findAllByDate(Mockito.<LocalDate>any());
    }

    @Test
    void testGetTodayAppointments4() {
        when(appointmentRepository.findAllByDate(Mockito.<LocalDate>any())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> appointmentServiceImpl.getTodayAppointments());
        verify(appointmentRepository).findAllByDate(Mockito.<LocalDate>any());
    }

    @Test
    void testCreateAppointment() {
        when(patientRepository.findById(Mockito.<Long>any()))
                .thenReturn(Optional.of(new Patient("Name", "jane.doe@example.org", "6625550144")));
        Appointment appointment = new Appointment();
        when(appointmentRepository.save(Mockito.<Appointment>any())).thenReturn(appointment);

        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setDate(LocalDate.of(1970, 1, 1));
        appointmentDto.setPatientId(1L);
        appointmentDto.setReason("Just cause");
        appointmentDto.setStatus("Status");
        assertSame(appointment, appointmentServiceImpl.createAppointment(appointmentDto));
        verify(patientRepository).findById(Mockito.<Long>any());
        verify(appointmentRepository).save(Mockito.<Appointment>any());
    }


    @Test
    void testCreateAppointment2() {
        when(patientRepository.findById(Mockito.<Long>any()))
                .thenReturn(Optional.of(new Patient("Name", "jane.doe@example.org", "6625550144")));
        when(appointmentRepository.save(Mockito.<Appointment>any())).thenThrow(new IllegalArgumentException());

        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setDate(LocalDate.of(1970, 1, 1));
        appointmentDto.setPatientId(1L);
        appointmentDto.setReason("Just cause");
        appointmentDto.setStatus("Status");
        assertThrows(IllegalArgumentException.class, () -> appointmentServiceImpl.createAppointment(appointmentDto));
        verify(patientRepository).findById(Mockito.<Long>any());
        verify(appointmentRepository).save(Mockito.<Appointment>any());
    }

    @Test
    void testCreateAppointment3() {
        when(patientRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        when(appointmentRepository.save(Mockito.<Appointment>any())).thenReturn(new Appointment());

        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setDate(LocalDate.of(1970, 1, 1));
        appointmentDto.setPatientId(1L);
        appointmentDto.setReason("Just cause");
        appointmentDto.setStatus("Status");
        assertThrows(IllegalArgumentException.class, () -> appointmentServiceImpl.createAppointment(appointmentDto));
        verify(patientRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testCancelAppointment() {
        when(appointmentRepository.save(Mockito.<Appointment>any())).thenReturn(new Appointment());
        when(appointmentRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Appointment()));
        appointmentServiceImpl.cancelAppointment(1L, "Just cause");
        verify(appointmentRepository).save(Mockito.<Appointment>any());
        verify(appointmentRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testCancelAppointment2() {
        when(appointmentRepository.save(Mockito.<Appointment>any())).thenThrow(new IllegalArgumentException());
        when(appointmentRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Appointment()));
        assertThrows(IllegalArgumentException.class, () -> appointmentServiceImpl.cancelAppointment(1L, "Just cause"));
        verify(appointmentRepository).save(Mockito.<Appointment>any());
        verify(appointmentRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testCancelAppointment3() {
        when(appointmentRepository.save(Mockito.<Appointment>any())).thenReturn(new Appointment());
        when(appointmentRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        new IllegalArgumentException();
        assertThrows(IllegalArgumentException.class, () -> appointmentServiceImpl.cancelAppointment(1L, "Just cause"));
        verify(appointmentRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testGetAppointments() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        when(appointmentRepository.findAllByDateAndPatientNameContainingIgnoreCase(Mockito.<LocalDate>any(),
                Mockito.<String>any())).thenReturn(appointmentList);
        List<Appointment> actualAppointments = appointmentServiceImpl.getAppointments("2020-03-01", "Patient Name");
        assertSame(appointmentList, actualAppointments);
        assertTrue(actualAppointments.isEmpty());
        verify(appointmentRepository).findAllByDateAndPatientNameContainingIgnoreCase(Mockito.<LocalDate>any(),
                Mockito.<String>any());
    }


    @Test
    void testGetAppointments2() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        when(appointmentRepository.findAllByPatientNameContainingIgnoreCase(Mockito.<String>any()))
                .thenReturn(appointmentList);
        when(appointmentRepository.findAllByDateAndPatientNameContainingIgnoreCase(Mockito.<LocalDate>any(),
                Mockito.<String>any())).thenReturn(new ArrayList<>());
        List<Appointment> actualAppointments = appointmentServiceImpl.getAppointments(null, "Patient Name");
        assertSame(appointmentList, actualAppointments);
        assertTrue(actualAppointments.isEmpty());
        verify(appointmentRepository).findAllByPatientNameContainingIgnoreCase(Mockito.<String>any());
    }

    @Test
    void testGetAppointments3() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        when(appointmentRepository.findAll()).thenReturn(appointmentList);
        when(appointmentRepository.findAllByPatientNameContainingIgnoreCase(Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(appointmentRepository.findAllByDateAndPatientNameContainingIgnoreCase(Mockito.<LocalDate>any(),
                Mockito.<String>any())).thenReturn(new ArrayList<>());
        List<Appointment> actualAppointments = appointmentServiceImpl.getAppointments(null, null);
        assertSame(appointmentList, actualAppointments);
        assertTrue(actualAppointments.isEmpty());
        verify(appointmentRepository).findAll();
    }

    @Test
    void testGetAppointments4() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        when(appointmentRepository.findAllByDate(Mockito.<LocalDate>any())).thenReturn(appointmentList);
        when(appointmentRepository.findAll()).thenReturn(new ArrayList<>());
        when(appointmentRepository.findAllByPatientNameContainingIgnoreCase(Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(appointmentRepository.findAllByDateAndPatientNameContainingIgnoreCase(Mockito.<LocalDate>any(),
                Mockito.<String>any())).thenReturn(new ArrayList<>());
        List<Appointment> actualAppointments = appointmentServiceImpl.getAppointments("2020-03-01", null);
        assertSame(appointmentList, actualAppointments);
        assertTrue(actualAppointments.isEmpty());
        verify(appointmentRepository).findAllByDate(Mockito.<LocalDate>any());
    }


    @Test
    void testGetAppointments5() {
        when(appointmentRepository.findAllByDate(Mockito.<LocalDate>any())).thenThrow(new IllegalArgumentException());
        when(appointmentRepository.findAll()).thenReturn(new ArrayList<>());
        when(appointmentRepository.findAllByPatientNameContainingIgnoreCase(Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(appointmentRepository.findAllByDateAndPatientNameContainingIgnoreCase(Mockito.<LocalDate>any(),
                Mockito.<String>any())).thenReturn(new ArrayList<>());
        assertThrows(IllegalArgumentException.class, () -> appointmentServiceImpl.getAppointments("2020-03-01", null));
        verify(appointmentRepository).findAllByDate(Mockito.<LocalDate>any());
    }

    @Test
    void testGetPatientAppointments() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        when(appointmentRepository.findAllByPatientId(Mockito.<Long>any())).thenReturn(appointmentList);
        List<Appointment> actualPatientAppointments = appointmentServiceImpl.getPatientAppointments(1L);
        assertSame(appointmentList, actualPatientAppointments);
        assertTrue(actualPatientAppointments.isEmpty());
        verify(appointmentRepository).findAllByPatientId(Mockito.<Long>any());
    }

    @Test
    void testGetPatientAppointments2() {
        when(appointmentRepository.findAllByPatientId(Mockito.<Long>any())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> appointmentServiceImpl.getPatientAppointments(1L));
        verify(appointmentRepository).findAllByPatientId(Mockito.<Long>any());
    }
}

