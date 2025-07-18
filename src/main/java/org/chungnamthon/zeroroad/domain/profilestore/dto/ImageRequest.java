package org.chungnamthon.zeroroad.domain.profilestore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class ImageRequest {
    @NotBlank(message = "이미지 ID는 필수입니다.")
    private String imageId;
}