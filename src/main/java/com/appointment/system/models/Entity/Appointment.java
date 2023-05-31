package com.appointment.system.models.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Entity
    @Table(name = "appointments")
    public class Appointment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private LocalDate date;
        private String reason;
        private String status;

        @ManyToOne
        @JoinColumn(name = "patient_id")
        private Patient patient;

        public Appointment(LocalDate date, String status, Patient patient) {
            this.date = date;
            this.status = status;
            this.patient = patient;
        }

        public Appointment(LocalDate date, Patient patient) {
            this.date = date;
            this.patient = patient;
        }

    }
