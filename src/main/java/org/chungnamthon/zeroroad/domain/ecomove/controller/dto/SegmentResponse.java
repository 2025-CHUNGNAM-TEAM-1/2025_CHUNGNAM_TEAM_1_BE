package org.chungnamthon.zeroroad.domain.ecomove.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "이동 구간 정보 (도보 또는 대중교통)")
public class SegmentResponse {
    @Schema(description = "이동 방식 (예: WALKING, TRANSIT)", example = "WALKING")
    private String mode;

    @Schema(description = "해당 구간 설명", example = "정류장까지 도보 또는 10번 버스 (7개 정류장)")
    private String description;

    @Schema(description = "해당 구간의 소요 시간 (초)", example = "180")
    private int duration;
}
