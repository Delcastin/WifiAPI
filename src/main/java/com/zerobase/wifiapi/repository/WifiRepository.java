package com.zerobase.wifiapi.repository;

import com.zerobase.wifiapi.entity.Wifi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WifiRepository extends JpaRepository<Wifi, Long> {

    Optional<Wifi> findByMgrNo(String mgrNo);
}
