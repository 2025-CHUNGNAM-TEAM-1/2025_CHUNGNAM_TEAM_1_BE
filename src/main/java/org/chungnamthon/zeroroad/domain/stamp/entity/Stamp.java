package org.chungnamthon.zeroroad.domain.stamp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.chungnamthon.zeroroad.domain.map.entity.Map;
import org.chungnamthon.zeroroad.domain.member.entity.Member;
import org.chungnamthon.zeroroad.global.entity.BaseTimeEntity;
import java.time.LocalDateTime;

@Table(name = "stamp")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Stamp extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean collected = false;

    @Column(name = "collected_at")
    private LocalDateTime collectedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id",nullable = false)
    private Map map;

    @Builder(access = AccessLevel.PRIVATE)
    private Stamp(Long id, boolean collected, LocalDateTime collectedAt, Member member, Map map) {
        this.id = id;
        this.collected = collected;
        this.collectedAt = collectedAt;
        this.member = member;
        this.map = map;
    }

    public static Stamp createStamp(Member member, Map map) {
        return Stamp.builder()
                .member(member)
                .map(map)
                .collected(false)
                .collectedAt(null)
                .build();
    }

}
