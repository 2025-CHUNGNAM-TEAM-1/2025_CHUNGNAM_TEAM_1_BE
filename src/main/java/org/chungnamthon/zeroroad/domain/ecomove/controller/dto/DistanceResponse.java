package org.chungnamthon.zeroroad.domain.ecomove.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "도보 및 자전거 이동 시간 응답 DTO")
public class DistanceResponse {
    @Schema(description = "도보 이동 시간 (초 단위)", example = "3600")
    private int walkingTime;

    @Schema(description = "자전거 이동 시간 (초 단위)", example = "1800")
    private int bikingTime;
}
