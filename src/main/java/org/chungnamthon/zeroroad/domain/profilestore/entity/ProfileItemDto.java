package org.chungnamthon.zeroroad.domain.profilestore.entity;

import lombok.Getter;

@Getter
public class ProfileItemDto {

    private String profildId;
    private String itemName;
    private int price;
    private String image;

    public ProfileItemDto(ProfileStore entity) {
        this.profildId = entity.getProfileId();
        this.itemName = entity.getItemName();
        this.price = entity.getPrice();
        this.image = entity.getImage();

    }
}
