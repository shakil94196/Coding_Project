package com.example.project.Coding_Project;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/batteries")
@Validated
public class BatteryController {
    @Autowired
    private BatteryService batteryService;

    @PostMapping
    public ResponseEntity<?> addBatteries(@Valid @RequestBody List<Battery> batteries) {
        List<Battery> invalidBatteries = batteries.stream()
                .filter(battery -> !isValid(battery))
                .collect(Collectors.toList());

        if (!invalidBatteries.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("invalidBatteries", invalidBatteries));
        }

        List<Battery> savedBatteries = batteryService.saveAll(batteries);
        return ResponseEntity.ok(savedBatteries);
    }

    @GetMapping
    public ResponseEntity<?> getBatteriesByPostcodeRange(@RequestParam String postcodeRange) {
        String[] range = postcodeRange.split("-");
        String startPostcode = range[0];
        String endPostcode = range[1];

        List<Battery> batteries = batteryService.getBatteriesByPostcodeRange(startPostcode, endPostcode);
        List<String> batteryNames = batteries.stream()
                .map(Battery::getName)
                .sorted()
                .collect(Collectors.toList());

        int totalWattCapacity = batteries.stream()
                .mapToInt(Battery::getWattCapacity)
                .sum();

        double averageWattCapacity = batteries.stream()
                .mapToInt(Battery::getWattCapacity)
                .average()
                .orElse(0);

        Map<String, Object> response = Map.of(
                "batteryNames", batteryNames,
                "statistics", Map.of(
                        "totalWattCapacity", totalWattCapacity,
                        "averageWattCapacity", averageWattCapacity
                )
        );

        return ResponseEntity.ok(response);
    }

    private boolean isValid(Battery battery) {


        return battery.getName() != null && !battery.getName().isEmpty() &&
                battery.getPostcode() != null && battery.getPostcode().matches("\\d{5}") &&
                battery.getWattCapacity() > 0;
    }
}
