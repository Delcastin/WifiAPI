package com.zerobase.wifiapi.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "search_history")
@Data
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double lat;
    private double lnt;

    private LocalDateTime searchDate;


    @ManyToOne
    @JoinColumn(name = "mgr_no")
    private Wifi wifi;
}
