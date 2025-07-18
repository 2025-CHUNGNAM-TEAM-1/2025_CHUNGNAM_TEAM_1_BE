package org.chungnamthon.zeroroad.domain.profilestore.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.zeroroad.domain.member.entity.Member;
import org.chungnamthon.zeroroad.domain.member.repository.MemberRepository;
import org.chungnamthon.zeroroad.domain.profilestore.entity.MemberOwnedProfileItem;
import org.chungnamthon.zeroroad.domain.profilestore.dto.MemberProfileUpdate;
import org.chungnamthon.zeroroad.domain.profilestore.entity.ProfileStore;
import org.chungnamthon.zeroroad.domain.profilestore.dto.UserProfile;
import org.chungnamthon.zeroroad.domain.profilestore.dto.ProfileImageItemDto;
import org.chungnamthon.zeroroad.domain.profilestore.respository.MemberOwnedProfileItemRepository;

import org.chungnamthon.zeroroad.domain.profilestore.respository.ProfileStoreRepository;
import org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberProfileService {

    private final MemberRepository memberRepository;
    private final ProfileStoreRepository profileStoreRepository;
    private final MemberOwnedProfileItemRepository memberOwnedProfileItemRepository;

    @Transactional(readOnly = true)
    public UserProfile getUserProfile(Long memberId) {
        Member currentMember = memberRepository.findById(memberId);

        List<MemberOwnedProfileItem> ownedItems = memberOwnedProfileItemRepository.findByMember(currentMember);
        List<String> ownedImageIds = ownedItems.stream()
                .map(item -> item.getProfileStore().getProfileId())
                .collect(Collectors.toList());
        List<ProfileStore> allProfileItems = profileStoreRepository.findAll();

        List<ProfileImageItemDto> profileImageDtos = allProfileItems.stream()
                .map(entity -> {
                    boolean hasItem = ownedImageIds.contains(entity.getProfileId());
                    return new ProfileImageItemDto(entity, hasItem);
                })
                .collect(Collectors.toList());

        return UserProfile.builder()
                .userName(currentMember.getName())
                .currentPoints(currentMember.getPoint())
                .currentImageId(currentMember.getProfileImgUrl())
                .profileImages(profileImageDtos)
                .build();
    }

    @Transactional
    public UserProfile updateMemberName(Long memberId, MemberProfileUpdate request) {
        Member member = memberRepository.findById(memberId);
        member.updateBasicProfile(request.getName(), member.getProfileImgUrl());

        return getUserProfile(memberId);
    }

}