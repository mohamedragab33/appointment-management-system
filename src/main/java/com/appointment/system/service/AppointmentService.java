package com.appointment.system.service;

import com.appointment.system.models.DTO.AppointmentDto;
import com.appointment.system.models.DTO.AppointmentResponse;
import com.appointment.system.models.Entity.Appointment;

import java.util.List;

public interface AppointmentService {
    List<AppointmentResponse> getTodayAppointments();
    Appointment createAppointment(AppointmentDto appointmentDto);
    void cancelAppointment(Long appointmentId, String cancellationReason);
    List<Appointment> getAppointments(String date, String patientName);
    List<Appointment> getPatientAppointments(Long patientId);

}
