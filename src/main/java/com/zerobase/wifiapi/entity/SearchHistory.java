package com.zerobase.wifiapi.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "search_history")
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double lat;
    private double lnt;

    private LocalDateTime searchDate;
}
