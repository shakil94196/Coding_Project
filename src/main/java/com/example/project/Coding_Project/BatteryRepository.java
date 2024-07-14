package com.example.project.Coding_Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {
    List<Battery> findByPostcodeBetween(String startPostcode, String endPostcode);
}
