package org.chungnamthon.zeroroad.domain.ecomove.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.zeroroad.domain.ecomove.controller.dto.DistanceResponse;
import org.chungnamthon.zeroroad.domain.ecomove.controller.dto.SegmentResponse;
import org.chungnamthon.zeroroad.domain.ecomove.controller.dto.TransitRouteResponse;
import org.chungnamthon.zeroroad.domain.ecomove.external.GoogleDirectionsClient;
import org.chungnamthon.zeroroad.domain.ecomove.external.TmapClient;
import org.chungnamthon.zeroroad.global.exception.EcoMoveException;
import org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EcoMoveService {

    private final GoogleDirectionsClient googleDirectionsClient;
    private final TmapClient tmapClient;

    public TransitRouteResponse getTransitRoute(double startX, double startY, double endX, double endY) {

        if (!isValidCoordinate(startX, startY) || !isValidCoordinate(endX, endY)) {
            throw new EcoMoveException(ErrorStatus.INVALID_COORDINATE);
        }
        // Google Maps Directions API 호출 결과 (JSON)
        JsonNode root = googleDirectionsClient.fetchDirectionsByMode("transit", startX, startY, endX, endY);
        // 첫 번째 경로의 첫 번째 Leg 안의 steps 배열을 가져옴
        JsonNode steps = root.path("routes").path(0).path("legs").path(0).path("steps");
        // 대중교통 소요 시간
        int transitDuration = 0;
        // 대중교통 내 도보 소요 시간
        int transitWalkingDuration = 0;

        List<SegmentResponse> segmentResponses = new ArrayList<>();

        for (JsonNode step : steps) {
            // WALKING, TRANSIT 등
            String mode = step.path("travel_mode").asText();
            // 초 단위
            int duration = step.path("duration").path("value").asInt();

            SegmentResponse segmentResponse = new SegmentResponse();
            segmentResponse.setMode(mode);
            segmentResponse.setDuration(duration);

            // 교통수단 구간
            if ("TRANSIT".equals(mode)) {
                transitDuration += duration;

                String line = step.path("transit_details").path("line").path("short_name").asText();
                String type = step.path("transit_details").path("line").path("vehicle").path("type").asText();
                int stops = step.path("transit_details").path("num_stops").asInt();

                segmentResponse.setDescription(line + "번 " + type + " (" + stops + "개 정류장)");
            } else {
                // 도보 구간
                transitWalkingDuration += duration;
                segmentResponse.setDescription("도보");
            }
            segmentResponses.add(segmentResponse);
        }

        // 결과 응답 객체 구성
        TransitRouteResponse result = new TransitRouteResponse();
        result.setTransitDuration(transitDuration);
        result.setTransitWalkingDuration(transitWalkingDuration);
        result.setSegmentResponses(segmentResponses);
        return result;
    }

    public DistanceResponse getEstimate(double startX,double startY,double endX,double endY){

        if (!isValidCoordinate(startX, startY) || !isValidCoordinate(endX, endY)) {
            throw new EcoMoveException(ErrorStatus.INVALID_COORDINATE);
        }

        int walkingTime = tmapClient.requestWalkingRoute(startX, startY, endX, endY);

        DistanceResponse distanceResponse = new DistanceResponse();
        distanceResponse.setWalkingTime(walkingTime);
        distanceResponse.setBikingTime((int) (walkingTime*0.5)); // 자전거는 도보의 50% 빠른 시간으로 가정

        return distanceResponse;
    }

    private boolean isValidCoordinate(double x, double y) {
        return x >= -180 && x <= 180 && y >= -90 && y <= 90;
    }
}

