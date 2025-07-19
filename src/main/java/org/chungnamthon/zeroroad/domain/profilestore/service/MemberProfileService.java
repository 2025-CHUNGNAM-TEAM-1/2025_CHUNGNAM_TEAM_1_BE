package org.chungnamthon.zeroroad.domain.profilestore.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.zeroroad.domain.member.entity.Member;
import org.chungnamthon.zeroroad.domain.member.repository.MemberRepository; // 이곳의 MemberRepository
import org.chungnamthon.zeroroad.domain.profilestore.dto.MemberProfileUpdate;
import org.chungnamthon.zeroroad.domain.profilestore.dto.ProfileImageItemDto;
import org.chungnamthon.zeroroad.domain.profilestore.dto.UserProfile;
import org.chungnamthon.zeroroad.domain.profilestore.entity.MemberOwnedProfileItem;
import org.chungnamthon.zeroroad.domain.profilestore.respository.MemberOwnedProfileItemRepository;
import org.chungnamthon.zeroroad.domain.profilestore.entity.ProfileStore;
import org.chungnamthon.zeroroad.domain.profilestore.respository.ProfileStoreRepository;

import org.chungnamthon.zeroroad.global.exception.BadRequestException;
import org.chungnamthon.zeroroad.global.exception.NotFoundException;
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

    @Transactional
    public UserProfile purchaseProfileImage(Long memberId, String imageId) {
        // memberRepository.findById()는 이미 Member 객체를 반환하므로 .orElseThrow를 제거합니다.
        Member member = memberRepository.findById(memberId);

        ProfileStore profileItem = profileStoreRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.PROFILE_IMAGE_NOT_FOUND));

        boolean alreadyOwned = memberOwnedProfileItemRepository.findByMember(member).stream()
                .anyMatch(item -> item.getProfileStore().getProfileId().equals(imageId));

        if (alreadyOwned) {
            if (!member.getProfileImgUrl().equals(imageId)) {
                member.updateProfileImgUrl(imageId);
            }
            return getUserProfile(memberId);
        }

        if (member.getPoint() < profileItem.getPrice()) {
            throw new BadRequestException(ErrorStatus.INSUFFICIENT_POINTS);
        }

        member.deductPoint(profileItem.getPrice());

        MemberOwnedProfileItem ownedItem = MemberOwnedProfileItem.builder()
                .member(member)
                .profileStore(profileItem)
                .build();
        memberOwnedProfileItemRepository.save(ownedItem);

        member.updateProfileImgUrl(imageId);

        return getUserProfile(memberId);
    }
}