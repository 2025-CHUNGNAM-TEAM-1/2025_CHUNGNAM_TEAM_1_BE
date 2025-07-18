package org.chungnamthon.zeroroad.domain.map.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CulturePlaceService {

    private final CulturePlaceRepository culturePlaceRepository;

    public List<CulturePlaceDto> findAllPlacesForMap() {
        List<CulturePlace> culturePlaces = culturePlaceRepository.findAll();

        return culturePlaces.stream()
                .map(CulturePlaceDto::new)
                .collect(Collectors.toList());
    }

    public List<CulturePlaceDto> searchPlaces(String keyword) {
        List<CulturePlace> places = culturePlaceRepository.findByPlaceName(keyword);

        return places.stream()
                .map(CulturePlaceDto::new)
                .collect(Collectors.toList());
    }

}
