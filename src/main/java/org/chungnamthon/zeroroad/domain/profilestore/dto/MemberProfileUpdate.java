package org.chungnamthon.zeroroad.domain.profilestore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberProfileUpdate {

    @NotBlank(message = "사용자 이름은 비워둘 수 없습니다.")
    private String name;

}