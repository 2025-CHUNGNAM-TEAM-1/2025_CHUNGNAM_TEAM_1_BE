package org.chungnamthon.zeroroad.domain.ecomove.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.chungnamthon.zeroroad.domain.ecomove.controller.dto.TransitRouteResponse;
import org.chungnamthon.zeroroad.global.annotation.ApiExceptions;
import org.springframework.http.ResponseEntity;
import static org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus.*;

@Tag(name = "EcoMove API", description = "에코 이동 수단 API")
public abstract class EcoMoveDocsController {

    @Operation(
            summary = "대중교통 경로 소요시간 및 구간 조회",
            description = "출발지와 도착지를 기반으로 대중교통의 소요 시간, 도보 시간, 구간별 정보를 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "대중교통 소요 시간 반환 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TransitRouteResponse.class)
            )
    )
    @ApiExceptions(values = {
            INVALID_COORDINATE,
            UNSUPPORTED_TRAVEL_MODE,
            GOOGLE_API_ERROR,
            NO_TRANSIT_ROUTE
    })
    public abstract ResponseEntity<TransitRouteResponse> getTransitRouteInfo(
            @Parameter(description = "출발지 X좌표 (경도)", example = "126.9784") double startX,
            @Parameter(description = "출발지 Y좌표 (위도)", example = "37.5665") double startY,
            @Parameter(description = "도착지 X좌표 (경도)", example = "126.9900") double endX,
            @Parameter(description = "도착지 Y좌표 (위도)", example = "37.5700") double endY
    );
}