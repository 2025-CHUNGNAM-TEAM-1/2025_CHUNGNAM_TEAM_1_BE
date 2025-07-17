package org.chungnamthon.zeroroad.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.zeroroad.domain.auth.controller.dto.ReissueTokenRequest;
import org.chungnamthon.zeroroad.domain.member.entity.Member;
import org.chungnamthon.zeroroad.domain.member.repository.MemberRepository;
import org.chungnamthon.zeroroad.global.exception.UnAuthenticationException;
import org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus;
import org.chungnamthon.zeroroad.global.jwt.dto.TokenResponse;
import org.chungnamthon.zeroroad.global.jwt.provider.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    // TODO: 재발급 로직 강화. 블랙 리스트 추가 예정
    @Transactional
    public TokenResponse reissue(ReissueTokenRequest request) {
        if (!jwtProvider.isValidateToken(request.refreshToken())) {
            throw new UnAuthenticationException(ErrorStatus.INVALID_TOKEN);
        }
        Member member = memberRepository.findByRefreshToken(request.refreshToken());

        TokenResponse tokenResponse = jwtProvider.createTokens(member.getId(), member.getRole());

        member.updateRefreshToken(tokenResponse.refreshToken());

        return tokenResponse;
    }

}