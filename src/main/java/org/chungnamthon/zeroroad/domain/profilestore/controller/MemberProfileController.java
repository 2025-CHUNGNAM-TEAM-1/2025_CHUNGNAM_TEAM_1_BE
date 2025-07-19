package org.chungnamthon.zeroroad.domain.profilestore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.zeroroad.domain.profilestore.dto.ImageRequest;
import org.chungnamthon.zeroroad.domain.profilestore.dto.MemberProfileUpdate;
import org.chungnamthon.zeroroad.domain.profilestore.dto.UserProfile;
import org.chungnamthon.zeroroad.domain.profilestore.service.MemberProfileService;
import org.chungnamthon.zeroroad.global.annotation.AuthMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/profile")
public class MemberProfileController extends MemberProfileDocsController {

    private final MemberProfileService memberProfileService;

    @Override
    @GetMapping
    public ResponseEntity<UserProfile> getProfileView(
            @AuthMember Long memberId
    ) {
        UserProfile response = memberProfileService.getUserProfile(memberId);
        return ResponseEntity.ok(response);
    }

    @Override
    @PatchMapping("/edits")
    public ResponseEntity<UserProfile> updateProfileName(
            @AuthMember Long memberId,
            @Valid @RequestBody MemberProfileUpdate request
    ) {
        UserProfile response = memberProfileService.updateMemberName(memberId, request);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/image")
    public ResponseEntity<UserProfile> purchaseProfileImage(
            @AuthMember Long memberId,
            @Valid @RequestBody ImageRequest request
    ) {
        UserProfile response = memberProfileService.purchaseProfileImage(memberId, request.getImageId());
        return ResponseEntity.ok(response);
    }
}