package com.appointment.system.models.DTO;

import lombok.Data;

import java.time.LocalDate;
@Data
public class AppointmentDto {

    private LocalDate date;
    private String reason;
    private String status;
    private Long patientId;
}
