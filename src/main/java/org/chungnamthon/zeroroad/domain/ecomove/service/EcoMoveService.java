package org.chungnamthon.zeroroad.domain.ecomove.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.zeroroad.domain.ecomove.controller.dto.SegmentResponse;
import org.chungnamthon.zeroroad.domain.ecomove.controller.dto.TransitRouteResponse;
import org.chungnamthon.zeroroad.global.exception.EcoMoveException;
import org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EcoMoveService {

    private static final String GOOGLE_MAPS_API_URL = "https://maps.googleapis.com/maps/api/directions/json";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${google.maps.api.key}")
    private String apiKey;

    public TransitRouteResponse getTransitRoute(double startX, double startY, double endX, double endY) {

        if (!isValidCoordinate(startX, startY) || !isValidCoordinate(endX, endY)) {
            throw new EcoMoveException(ErrorStatus.INVALID_COORDINATE);
        }
        // Google Maps Directions API 호출 결과 (JSON)
        JsonNode root = fetchDirectionsByMode("transit", startX, startY, endX, endY);
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

    private boolean isValidCoordinate(double x, double y) {
        return x >= -180 && x <= 180 && y >= -90 && y <= 90;
    }

    private JsonNode fetchDirectionsByMode(String mode, double startX, double startY, double endX, double endY) {
        String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_MAPS_API_URL)
                .queryParam("origin", startY + "," + startX)
                .queryParam("destination", endY + "," + endX)
                .queryParam("mode", mode)
                .queryParam("language", "ko")
                .queryParam("key", apiKey)
                .build()
                .toUriString();
        try {
            // Google Maps API로부터 JSON 문자열 응답을 가져온 후 파싱
            String json = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            String status = root.path("status").asText();
            if (!"OK".equals(status)) {
                if ("ZERO_RESULTS".equals(status)) {
                    throw new EcoMoveException(ErrorStatus.NO_TRANSIT_ROUTE);
                }
                throw new EcoMoveException(ErrorStatus.GOOGLE_API_ERROR);
            }
            return root;
        } catch (HttpClientErrorException e) {
            throw new EcoMoveException(ErrorStatus.GOOGLE_API_ERROR);
        } catch (Exception e) {
            throw new EcoMoveException(ErrorStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

