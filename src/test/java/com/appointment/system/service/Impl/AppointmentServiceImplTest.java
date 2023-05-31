package com.appointment.system.service.Impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.appointment.system.models.Entity.Appointment;
import com.appointment.system.repository.AppointmentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AppointmentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AppointmentServiceImplTest {
    @MockBean
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentServiceImpl appointmentServiceImpl;


    /**
     * Method under test: {@link AppointmentServiceImpl#getTodayAppointments()}
     */
    @Test
    void testGetTodayAppointments2() {
        when(appointmentRepository.findAllByDate(Mockito.<LocalDate>any())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> appointmentServiceImpl.getTodayAppointments());
        verify(appointmentRepository).findAllByDate(Mockito.<LocalDate>any());
    }

    /**
     * Method under test: {@link AppointmentServiceImpl#createAppointment(Appointment)}
     */
    @Test
    void testCreateAppointment() {
        Appointment appointment = new Appointment();
        when(appointmentRepository.save(Mockito.<Appointment>any())).thenReturn(appointment);
        assertSame(appointment, appointmentServiceImpl.createAppointment(new Appointment()));
        verify(appointmentRepository).save(Mockito.<Appointment>any());
    }

    /**
     * Method under test: {@link AppointmentServiceImpl#createAppointment(Appointment)}
     */
    @Test
    void testCreateAppointment2() {
        when(appointmentRepository.save(Mockito.<Appointment>any())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> appointmentServiceImpl.createAppointment(new Appointment()));
        verify(appointmentRepository).save(Mockito.<Appointment>any());
    }

    /**
     * Method under test: {@link AppointmentServiceImpl#cancelAppointment(Long, String)}
     */
    @Test
    void testCancelAppointment() {
        when(appointmentRepository.save(Mockito.<Appointment>any())).thenReturn(new Appointment());
        when(appointmentRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Appointment()));
        appointmentServiceImpl.cancelAppointment(1L, "Just cause");
        verify(appointmentRepository).save(Mockito.<Appointment>any());
        verify(appointmentRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AppointmentServiceImpl#cancelAppointment(Long, String)}
     */
    @Test
    void testCancelAppointment2() {
        when(appointmentRepository.save(Mockito.<Appointment>any())).thenThrow(new IllegalArgumentException());
        when(appointmentRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Appointment()));
        assertThrows(IllegalArgumentException.class, () -> appointmentServiceImpl.cancelAppointment(1L, "Just cause"));
        verify(appointmentRepository).save(Mockito.<Appointment>any());
        verify(appointmentRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AppointmentServiceImpl#cancelAppointment(Long, String)}
     */
    @Test
    void testCancelAppointment3() {
        when(appointmentRepository.save(Mockito.<Appointment>any())).thenReturn(new Appointment());
        when(appointmentRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        new IllegalArgumentException();
        assertThrows(IllegalArgumentException.class, () -> appointmentServiceImpl.cancelAppointment(1L, "Just cause"));
        verify(appointmentRepository).findById(Mockito.<Long>any());
    }



    /**
     * Method under test: {@link AppointmentServiceImpl#getAppointments(String, String)}
     */
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


    /**
     * Method under test: {@link AppointmentServiceImpl#getAppointments(String, String)}
     */
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

    /**
     * Method under test: {@link AppointmentServiceImpl#getAppointments(String, String)}
     */
    @Test
    void testGetAppointments4() {
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

    /**
     * Method under test: {@link AppointmentServiceImpl#getAppointments(String, String)}
     */
    @Test
    void testGetAppointments5() {
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

    /**
     * Method under test: {@link AppointmentServiceImpl#getAppointments(String, String)}
     */
    @Test
    void testGetAppointments6() {
        when(appointmentRepository.findAllByDate(Mockito.<LocalDate>any())).thenThrow(new IllegalArgumentException());
        when(appointmentRepository.findAll()).thenReturn(new ArrayList<>());
        when(appointmentRepository.findAllByPatientNameContainingIgnoreCase(Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(appointmentRepository.findAllByDateAndPatientNameContainingIgnoreCase(Mockito.<LocalDate>any(),
                Mockito.<String>any())).thenReturn(new ArrayList<>());
        assertThrows(IllegalArgumentException.class, () -> appointmentServiceImpl.getAppointments("2020-03-01", null));
        verify(appointmentRepository).findAllByDate(Mockito.<LocalDate>any());
    }

    /**
     * Method under test: {@link AppointmentServiceImpl#getPatientAppointments(Long)}
     */
    @Test
    void testGetPatientAppointments() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        when(appointmentRepository.findAllByPatientId(Mockito.<Long>any())).thenReturn(appointmentList);
        List<Appointment> actualPatientAppointments = appointmentServiceImpl.getPatientAppointments(1L);
        assertSame(appointmentList, actualPatientAppointments);
        assertTrue(actualPatientAppointments.isEmpty());
        verify(appointmentRepository).findAllByPatientId(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AppointmentServiceImpl#getPatientAppointments(Long)}
     */
    @Test
    void testGetPatientAppointments2() {
        when(appointmentRepository.findAllByPatientId(Mockito.<Long>any())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> appointmentServiceImpl.getPatientAppointments(1L));
        verify(appointmentRepository).findAllByPatientId(Mockito.<Long>any());
    }
}

