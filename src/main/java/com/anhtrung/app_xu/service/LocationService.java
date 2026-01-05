package com.anhtrung.app_xu.service;

import com.anhtrung.app_xu.domain.Location;
import com.anhtrung.app_xu.dto.LocationDto;
import com.anhtrung.app_xu.repo.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<LocationDto> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<LocationDto> getActiveLocations() {
        return locationRepository.findByIsActiveTrue().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private LocationDto toDto(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .code(location.getCode())
                .name(location.getName())
                .address(location.getAddress())
                .lat(location.getLat())
                .lng(location.getLng())
                .openTime(location.getOpenTime())
                .isActive(location.getIsActive())
                .build();
    }
}