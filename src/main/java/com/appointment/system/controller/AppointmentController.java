package com.appointment.system.controller;

import com.appointment.system.models.DTO.AppointmentResponse;
import com.appointment.system.models.Entity.Appointment;
import com.appointment.system.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/today")
    public ResponseEntity<List<AppointmentResponse>> getTodayAppointments() {
        List<AppointmentResponse> todayAppointments = appointmentService.getTodayAppointments();
        return ResponseEntity.ok(todayAppointments);
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.createAppointment(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable("id") Long appointmentId,
                                                  @RequestParam("reason") String cancellationReason) {
        appointmentService.cancelAppointment(appointmentId, cancellationReason);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointments(@RequestParam(value = "date", required = false) String date,
                                                             @RequestParam(value = "patientName", required = false) String patientName) {
        List<Appointment> filteredAppointments = appointmentService.getAppointments(date, patientName);
        return ResponseEntity.ok(filteredAppointments);
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<List<Appointment>> getPatientAppointments(@PathVariable("id") Long patientId) {
        List<Appointment> patientAppointments = appointmentService.getPatientAppointments(patientId);
        return ResponseEntity.ok(patientAppointments);
    }
}