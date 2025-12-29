package com.anhtrung.app_xu.controller;

import com.anhtrung.app_xu.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllLocations(
            @RequestParam(required = false, defaultValue = "false") Boolean activeOnly
    ) {
        var locations = activeOnly ? 
                locationService.getActiveLocations() : 
                locationService.getAllLocations();
        
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Lấy danh sách điểm thu gom thành công",
                "data", locations
        ));
    }
}