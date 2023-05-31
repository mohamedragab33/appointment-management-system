package com.appointment.system.models.DTO;

import com.appointment.system.models.Entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponse {
    private LocalDate date;
    private Patient patient;
    private String cancellationReason;

}
