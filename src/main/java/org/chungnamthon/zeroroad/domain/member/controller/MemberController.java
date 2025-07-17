package org.chungnamthon.zeroroad.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.zeroroad.domain.member.controller.dto.MemberProfileRequest;
import org.chungnamthon.zeroroad.domain.member.service.MemberService;
import org.chungnamthon.zeroroad.global.annotation.AuthMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/members")
@RequiredArgsConstructor
@RestController
public class MemberController extends MemberDocsController {

    private final MemberService memberService;

    @Override
    @PostMapping
    public ResponseEntity<Void> register(
            @Valid @RequestPart MemberProfileRequest request,
            @RequestPart(required = false) MultipartFile image,
            @AuthMember Long memberId
    ) {
        memberService.updateProfile(request, image, memberId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/check-name")
    public ResponseEntity<Void> checkDuplicateName(@RequestParam String name) {
        memberService.checkDuplicateName(name);

        return ResponseEntity.ok().build();
    }

}