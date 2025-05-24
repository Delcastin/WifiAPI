package com.zerobase.wifiapi.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookmark")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mgrNo; // Wifi의 PK
    private String wifiName;

    private LocalDateTime addedAt;
}
