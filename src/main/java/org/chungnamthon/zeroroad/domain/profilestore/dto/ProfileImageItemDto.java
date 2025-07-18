package org.chungnamthon.zeroroad.domain.profilestore.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.chungnamthon.zeroroad.domain.profilestore.entity.ProfileStore;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileImageItemDto {
    private String imageId;
    private String imageUrl;
    private int price;
    private boolean item;

    public ProfileImageItemDto(ProfileStore entity, boolean hasItem) {
        this.imageId = entity.getProfileId();
        this.imageUrl = entity.getImage();
        this.price = entity.getPrice();
        this.item = hasItem;
    }
}