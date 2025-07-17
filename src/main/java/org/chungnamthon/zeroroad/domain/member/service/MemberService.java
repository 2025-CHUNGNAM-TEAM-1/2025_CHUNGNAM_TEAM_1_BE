package org.chungnamthon.zeroroad.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.zeroroad.domain.member.controller.dto.MemberProfileRequest;
import org.chungnamthon.zeroroad.domain.member.entity.Member;
import org.chungnamthon.zeroroad.domain.member.repository.MemberRepository;
import org.chungnamthon.zeroroad.global.exception.BadRequestException;
import org.chungnamthon.zeroroad.global.exception.DuplicationException;
import org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus;
import org.chungnamthon.zeroroad.s3.S3Provider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3Provider s3Provider;

    @Transactional
    public void updateRefreshToken(Long memberId, String refreshToken) {
        Member member = memberRepository.findById(memberId);
        member.updateRefreshToken(refreshToken);
    }

    @Transactional
    public void updateProfile(MemberProfileRequest request, MultipartFile profileImage, Long memberId) {
        Member member = memberRepository.findById(memberId);

        if (!isNullProfileImage(member)) {
            s3Provider.deleteImage(member.getProfileImgUrl());
        }
        if (isEmptyRequestProfileImage(profileImage)) {
            member.updateBasicProfile(request.name(), null);
            member.updateRoleToMember();
            return;
        }

        String uploadImage = s3Provider.uploadImage(profileImage);
        member.updateBasicProfile(request.name(), uploadImage);
        member.updateRoleToMember();
    }


    public void checkDuplicateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BadRequestException(ErrorStatus.BAD_REQUEST);
        }
        if (memberRepository.existsByName(name)) {
            throw new DuplicationException(ErrorStatus.DUPLICATE_NICKNAME);
        }
    }

    private boolean isNullProfileImage(Member member) {
        return member.getProfileImgUrl() == null;
    }

    private boolean isEmptyRequestProfileImage(MultipartFile profileImage) {
        return profileImage == null || profileImage.isEmpty();
    }

}