package com.appointment.system.models.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
    @AllArgsConstructor
    @Entity
    @Table(name = "appointments")
    public class Appointment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private LocalDate date;

        @ManyToOne
        @JoinColumn(name = "patient_id", nullable = false)
        private Patient patient;

        @Column
        private String cancellationReason;


        public Appointment() {
        }

        public Appointment(LocalDate date, Patient patient) {
            this.date = date;
            this.patient = patient;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Patient getPatient() {
            return patient;
        }

        public void setPatient(Patient patient) {
            this.patient = patient;
        }

        public String getCancellationReason() {
            return cancellationReason;
        }

        public void setCancellationReason(String cancellationReason) {
            this.cancellationReason = cancellationReason;
        }
    }
