package org.chungnamthon.zeroroad.domain.member.entity;

import static java.util.Objects.requireNonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.chungnamthon.zeroroad.global.entity.BaseTimeEntity;
import org.chungnamthon.zeroroad.global.exception.BadRequestException;
import org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus;

@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "social_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(nullable = false)
    private Long point;

    @Column(name = "profile_img_url")
    private String profileImgUrl;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(
            String name,
            String email,
            String socialId,
            String profileImgUrl,
            Role role,
            SocialType socialType,
            Long point,
            String refreshToken
    ) {
        this.name = name;
        this.email = email;
        this.socialId = socialId;
        this.role = role;
        this.socialType = socialType;
        this.point = point;
        this.profileImgUrl = profileImgUrl;
        this.refreshToken = refreshToken;
    }

    public static Member createMember(String name, String email, String socialId, SocialType socialType, String profileImgUrl) {
        requireNonNull(name);
        requireNonNull(email);
        requireNonNull(socialId);
        requireNonNull(socialType);

        return Member.builder()
                .name(name)
                .email(email)
                .socialId(socialId)
                .socialType(socialType)
                .profileImgUrl(profileImgUrl)
                .role(Role.GUEST)
                .point(0L)
                .build();
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateBasicProfile(String name, String profileImgUrl) {
        this.name = name;
        this.profileImgUrl = profileImgUrl;
    }
    public void updateRoleToMember() {
        this.role = Role.MEMBER;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void deductPoint(long amount) {
        if (this.point < amount) {
            throw new BadRequestException(ErrorStatus.INSUFFICIENT_POINTS);
        }
        this.point -= amount;
    }

    public void updateProfileImgUrl(String newImageUrl) {
        this.profileImgUrl = newImageUrl;
    }
}