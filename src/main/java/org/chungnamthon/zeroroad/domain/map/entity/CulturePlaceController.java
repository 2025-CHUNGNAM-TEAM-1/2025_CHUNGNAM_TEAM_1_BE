package org.chungnamthon.zeroroad.domain.map.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/culture")
@RequiredArgsConstructor
@RestController
public class CulturePlaceController {

    private final CulturePlaceService culturePlaceService;

    @GetMapping("/places/map")
    public List<CulturePlaceDto> getPlacesForMap() {
        return culturePlaceService.findAllPlacesForMap();
    }

    @GetMapping("/places/search")
    public List<CulturePlaceDto> getSearchPlaces(@RequestParam("keyword") String keyword){
        return culturePlaceService.searchPlaces(keyword);
    }
}
