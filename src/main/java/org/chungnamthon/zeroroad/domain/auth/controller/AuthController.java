package org.chungnamthon.zeroroad.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.zeroroad.domain.auth.controller.dto.ReissueTokenRequest;
import org.chungnamthon.zeroroad.domain.auth.service.AuthService;
import org.chungnamthon.zeroroad.global.jwt.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController extends AuthDocsController {

    private final AuthService authService;

    @Override
    @PostMapping("/auth/reissue")
    public ResponseEntity<TokenResponse> reissue(@Valid @RequestBody ReissueTokenRequest request) {
        TokenResponse response = authService.reissue(request);

        return ResponseEntity.ok(response);
    }

}