package org.chungnamthon.zeroroad.domain.ecomove.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.zeroroad.global.exception.EcoMoveException;
import org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GoogleDirectionsClient {

    private static final String GOOGLE_MAPS_API_URL = "https://maps.googleapis.com/maps/api/directions/json";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${google.maps.api.key}")
    private String apiKey;

    public JsonNode fetchDirectionsByMode(String mode, double startX, double startY, double endX, double endY) {
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
