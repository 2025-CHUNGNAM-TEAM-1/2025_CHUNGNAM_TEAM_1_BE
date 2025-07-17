package org.chungnamthon.zeroroad.domain.member.controller;

import static org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus.BAD_REQUEST;
import static org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus.DUPLICATE_NICKNAME;
import static org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus.INTERNAL_SERVER_ERROR;
import static org.chungnamthon.zeroroad.global.exception.dto.ErrorStatus.UNAUTHORIZED_ERROR;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.chungnamthon.zeroroad.domain.member.controller.dto.MemberProfileRequest;
import org.chungnamthon.zeroroad.global.annotation.ApiExceptions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Member API", description = "회원 관련 API 문서입니다.")
public abstract class MemberDocsController {

    @Operation(
            summary = "프로필 정보 등록 요청 - JWT O",
            description = """
                    프로필 사진, 이름 입력해 요청합니다.
                    마이페이지에서 프로필 변경 시 해당 API를 사용합니다.
                    """
    )
    @RequestBody(
            description = "프로필 정보 등록 요청",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = MemberProfileRequest.class)
            )
    )
    @ApiExceptions(values = {
            BAD_REQUEST,
            UNAUTHORIZED_ERROR,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<Void> register(MemberProfileRequest request,
            @RequestBody(
                    description = "사용자 프로필 사진  (이미지)",
                    required = false,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(type = "string", format = "binary")
                    )
            ) MultipartFile image, Long memberId);

    @Operation(
            summary = "닉네임 중복 확인 - JWT O"
    )
    @Parameter(
            name = "name",
            description = "중복 확인할 닉네임",
            in = ParameterIn.QUERY,
            required = true
    )
    @ApiResponse(
            responseCode = "204",
            description = "닉네임 중복 확인 성공"
    )
    @ApiExceptions(values = {
            BAD_REQUEST,
            UNAUTHORIZED_ERROR,
            DUPLICATE_NICKNAME,
            INTERNAL_SERVER_ERROR
    })
    public abstract ResponseEntity<Void> checkDuplicateName(String name);

}