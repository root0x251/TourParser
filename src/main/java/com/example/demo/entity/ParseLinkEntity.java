package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "link_for_parse")
public class ParseLinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "link", columnDefinition = "TEXT")
    String link;

    public ParseLinkEntity(String link) {
        this.link = link;
    }
}
