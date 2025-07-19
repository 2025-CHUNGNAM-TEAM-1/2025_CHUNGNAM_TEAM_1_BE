package org.chungnamthon.zeroroad.domain.profilestore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {
    private String userName;
    private Long currentPoints;
    private String currentImageId;
    private List<ProfileImageItemDto> profileImages;
}