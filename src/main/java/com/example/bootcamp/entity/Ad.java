package com.example.bootcamp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double minimumPrice;
    private double currentPrice;
    private String image;
    @Enumerated(EnumType.STRING)
    private AdStatus status;

    @ManyToOne
    private User user;

    @ManyToOne
    private User userBought;
}
