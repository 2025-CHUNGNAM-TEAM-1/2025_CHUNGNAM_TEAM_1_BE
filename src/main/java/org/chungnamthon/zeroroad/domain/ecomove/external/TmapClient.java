package org.chungnamthon.zeroroad.domain.ecomove.external;

import org.chungnamthon.zeroroad.global.exception.EcoMoveException;
import org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TmapClient {

    private static final String BASE_URL = "https://apis.openapi.sk.com/tmap/routes/pedestrian";

    @Value("${tmap.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public int requestWalkingRoute(double startX, double startY, double endX, double endY) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("appKey", apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("startX", startX);
        body.put("startY", startY);
        body.put("endX", endX);
        body.put("endY", endY);
        body.put("reqCoordType", "WGS84GEO");
        body.put("resCoordType", "WGS84GEO");
        body.put("startName", "start");
        body.put("endName", "end");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(BASE_URL, request, Map.class);

        List<Map<String, Object>> features = (List<Map<String, Object>>) response.getBody().get("features");

        if (features == null || features.isEmpty()) {
            throw new EcoMoveException(ErrorStatus.NO_TRANSIT_ROUTE);
        }
        Map<String, Object> properties = (Map<String, Object>) features.get(0).get("properties");

        return (Integer) properties.get("totalTime");
    }
}
