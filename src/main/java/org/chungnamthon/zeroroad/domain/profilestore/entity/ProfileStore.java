package org.chungnamthon.zeroroad.domain.profilestore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "profile_store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileStore {

    @Id
    @Column(name = "profile_id")
    private String profileId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String image;
}
