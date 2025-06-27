package com.zerobase.wifiapi.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    private LocalDateTime searchDate;


    @ManyToOne
    @JoinColumn(name = "mgr_no")
    private Wifi wifi;
}
