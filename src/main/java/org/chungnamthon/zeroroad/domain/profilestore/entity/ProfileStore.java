package org.chungnamthon.zeroroad.domain.profilestore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.chungnamthon.zeroroad.global.entity.BaseTimeEntity;

@Table(name = "profile_store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ProfileStore extends BaseTimeEntity {

    @Id
    @Column(name = "profile_id", nullable = false)
    private String profileId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String image;

    @Builder
    public ProfileStore(String profileId, String itemName, int price, String image) {
        this.profileId = profileId;
        this.itemName = itemName;
        this.price = price;
        this.image = image;
    }
}
