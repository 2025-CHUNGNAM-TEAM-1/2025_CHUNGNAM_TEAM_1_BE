package org.chungnamthon.zeroroad.domain.profilestore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.chungnamthon.zeroroad.domain.member.entity.Member;
import org.chungnamthon.zeroroad.global.entity.BaseTimeEntity;

@Table(name = "member_owned_profile_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class MemberOwnedProfileItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "profile_store_id", nullable = false)
    private ProfileStore profileStore;

    @Builder
    public MemberOwnedProfileItem(Member member, ProfileStore profileStore) {
        this.member = member;
        this.profileStore = profileStore;
    }
}