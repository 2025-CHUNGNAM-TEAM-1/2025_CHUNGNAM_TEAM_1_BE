package org.chungnamthon.zeroroad.domain.map.entity;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CulturePlaceDto {
    private Long id;
    private String placeName;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public CulturePlaceDto(CulturePlace culturePlace) {
        this.id = culturePlace.getId();
        this.placeName = culturePlace.getPlaceName();
        this.latitude = culturePlace.getLatitude();
        this.longitude = culturePlace.getLongitude();
    }
}
