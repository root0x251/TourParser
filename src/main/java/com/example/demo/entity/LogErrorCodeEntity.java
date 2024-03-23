package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "error_core_log")
public class LogErrorCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String description;
    String date;
    @Column(name = "tour_name")
    String tourName;

    public LogErrorCodeEntity() {
    }

    public LogErrorCodeEntity(String description, String date, String tourName) {
        this.description = description;
        this.date = date;
        this.tourName = tourName;
    }
}
