package com.zerobase.wifiapi.repository;

import com.zerobase.wifiapi.entity.Wifi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WifiRepository extends JpaRepository<Wifi, Long> {
}
