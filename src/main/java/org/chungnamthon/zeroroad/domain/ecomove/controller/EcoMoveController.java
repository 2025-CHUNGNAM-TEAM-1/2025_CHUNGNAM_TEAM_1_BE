package org.chungnamthon.zeroroad.domain.ecomove.controller;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.zeroroad.domain.ecomove.controller.dto.TransitRouteResponse;
import org.chungnamthon.zeroroad.domain.ecomove.service.EcoMoveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eco-moves")
public class EcoMoveController extends EcoMoveDocsController{

    private final EcoMoveService ecoMoveService;

    @Override
    @GetMapping("/estimation/transit")
    public ResponseEntity<TransitRouteResponse> getTransitRouteInfo(@RequestParam double startX, @RequestParam double startY, @RequestParam double endX, @RequestParam double endY) {
        return ResponseEntity.ok(ecoMoveService.getTransitRoute(startX, startY, endX, endY));
    }
}
