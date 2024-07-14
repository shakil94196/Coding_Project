package com.example.project.Coding_Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatteryService {
    @Autowired
    private BatteryRepository batteryRepository;

    public List<Battery> saveAll(List<Battery> batteries) {
        return batteryRepository.saveAll(batteries);
    }

    public List<Battery> getBatteriesByPostcodeRange(String startPostcode, String endPostcode) {
        return batteryRepository.findByPostcodeBetween(startPostcode, endPostcode);
    }
}

