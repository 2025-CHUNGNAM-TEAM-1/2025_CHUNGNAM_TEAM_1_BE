package org.chungnamthon.zeroroad.domain.ecomove.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@Schema(description = "대중교통 구간 정보 응답 DTO")
public class TransitRouteResponse {
    @Schema(description = "도보 구간 총 소요 시간 (초 단위)", example = "400")
    private int transitWalkingDuration;

    @Schema(description = "대중교통 구간 총 소요 시간 (초 단위)", example = "1200")
    private int transitDuration;

    @Schema(description = "구간별 이동 정보 리스트 (도보, 버스, 지하철 등)")
    private List<SegmentResponse> segmentResponses;
}
