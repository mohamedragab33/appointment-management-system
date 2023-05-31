package com.appointment.system.service.Impl;


import com.appointment.system.models.DTO.AppointmentDto;
import com.appointment.system.models.DTO.AppointmentResponse;
import com.appointment.system.models.Entity.Appointment;
import com.appointment.system.models.Entity.Patient;
import com.appointment.system.repository.AppointmentRepository;
import com.appointment.system.repository.PatientRepository;
import com.appointment.system.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository ;
    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<AppointmentResponse> getTodayAppointments() {
        LocalDate today = LocalDate.now();
        List<AppointmentResponse> responses = new ArrayList<>();
        var appointments=  appointmentRepository.findAllByDate(today);
         if (appointments != null ){
             appointments.forEach (it ->{
                 AppointmentResponse appointmentResponse =AppointmentResponse.builder().patient(it.getPatient()).date(it.getDate()).build();
                 responses.add(appointmentResponse); });
         }

        return responses ;
    }

    @Override
    public Appointment createAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        Patient patient =patientRepository.findById(appointmentDto.getPatientId()).orElseThrow(() -> new IllegalArgumentException("Invalid Patient ID"));
        appointment.setReason(appointmentDto.getReason());
        appointment.setDate(appointmentDto.getDate());
        appointment.setStatus(appointmentDto.getStatus());
        appointment.setPatient(patient);

        return appointmentRepository.save(appointment);
    }

    @Override
    public void cancelAppointment(Long appointmentId, String cancellationReason) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment ID"));

        appointment.setReason(cancellationReason);
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAppointments(String date, String patientName) {
        if (date != null && patientName != null) {
            LocalDate appointmentDate = LocalDate.parse(date);
            return appointmentRepository.findAllByDateAndPatientNameContainingIgnoreCase(appointmentDate, patientName);
        } else if (date != null) {
            LocalDate appointmentDate = LocalDate.parse(date);
            return appointmentRepository.findAllByDate(appointmentDate);
        } else if (patientName != null) {
            return appointmentRepository.findAllByPatientNameContainingIgnoreCase(patientName);
        } else {
            return appointmentRepository.findAll();
        }
    }

    @Override
    public List<Appointment> getPatientAppointments(Long patientId) {
        return appointmentRepository.findAllByPatientId(patientId);
    }
}
