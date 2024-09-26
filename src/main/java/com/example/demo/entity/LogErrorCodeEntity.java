package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "error_core_log")
public class LogErrorCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String description;
    String date;
    @Column(name = "tour_name")
    String tourName;

    public LogErrorCodeEntity(String description, String date, String tourName) {
        this.description = description;
        this.date = date;
        this.tourName = tourName;
    }
}
