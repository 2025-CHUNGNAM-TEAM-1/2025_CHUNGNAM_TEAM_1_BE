package org.chungnamthon.zeroroad.domain.profilestore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.chungnamthon.zeroroad.domain.profilestore.dto.MemberProfileUpdate;
import org.chungnamthon.zeroroad.domain.profilestore.dto.UserProfile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "Member Profile API", description = "회원 프로필 관련 API 문서입니다.")
public abstract class MemberProfileDocsController {

    @Operation(
            summary = "사용자 프로필 및 상점 아이템 목록 조회 - JWT O",
            description = """
                    로그인한 사용자의 프로필 정보와 구매 가능한/소유한 프로필 이미지를 조회합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 프로필 정보를 조회했습니다.", // description 수정
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserProfile.class),
                            examples = @ExampleObject(
                                    name = "profileViewSuccess",
                                    summary = "profileViewSuccess", // name과 동일하게 설정하여 드롭다운에 name만 보이도록
                                    value = """
                                            {
                                                "userName": "제로로드",
                                                "currentPoints": 1000,
                                                "currentImageId": "image_123",
                                                "profileImages": [
                                                    {
                                                        "imageId": "image_123",
                                                        "imageUrl": "http://example.com/image1.png",
                                                        "price": 0,
                                                        "item": true
                                                    },
                                                    {
                                                        "imageId": "image_456",
                                                        "imageUrl": "http://example.com/image2.png",
                                                        "price": 50,
                                                        "item": false
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", // description 추가
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "UNAUTHORIZED_ERROR",
                                    summary = "UNAUTHORIZED_ERROR", // name과 동일하게 설정
                                    value = """
                                            {
                                                "statusCode": 401,
                                                "message": "ERROR - 인증되지 않은 사용자입니다.",
                                                "validation": {}
                                            }
                                            """
                            )
                    )),

            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음", // description 수정
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "MEMBER_NOT_FOUND",
                                    summary = "MEMBER_NOT_FOUND", // name과 동일하게 설정
                                    value = """
                                            {
                                                "statusCode": 404,
                                                "message": "ERROR - 회원을 찾을 수 없습니다.",
                                                "validation": {}
                                            }
                                            """
                            )
                    ))
    })
    public abstract ResponseEntity<UserProfile> getProfileView(
            @Parameter(hidden = true) Long memberId);

    @Operation(
            summary = "사용자 이름 변경 - JWT O",
            description = "로그인한 사용자의 이름을 변경합니다."
    )
    @RequestBody(
            description = "이름 변경 요청",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = MemberProfileUpdate.class),
                    examples = @ExampleObject(
                            name = "updateProfileNameRequest",
                            summary = "updateProfileNameRequest", // name과 동일하게 설정
                            value = """
                                    {
                                        "name": "새로운닉네임"
                                    }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 사용자 이름을 변경했습니다.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserProfile.class),
                            examples = @ExampleObject(
                                    name = "updateProfileNameResponse",
                                    summary = "updateProfileNameResponse", // name과 동일하게 설정
                                    value = """
                                            {
                                                "userName": "새로운닉네임",
                                                "currentPoints": 1000,
                                                "currentImageId": "image_123",
                                                "profileImages": []
                                            }
                                            """
                            )
                    )),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", // description 수정
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "BAD_REQUEST",
                                    summary = "BAD_REQUEST", // name과 동일하게 설정
                                    value = """
                                            {
                                                "statusCode": 400,
                                                "message": "ERROR - 잘못된 요청입니다.",
                                                "validation": {}
                                            }
                                            """
                            )
                    )),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", // description 수정
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "UNAUTHORIZED_ERROR",
                                    summary = "UNAUTHORIZED_ERROR", // name과 동일하게 설정
                                    value = """
                                            {
                                                "statusCode": 401,
                                                "message": "ERROR - 인증되지 않은 사용자입니다.",
                                                "validation": {}
                                            }
                                            """
                            )
                    ))
    })
    public abstract ResponseEntity<UserProfile> updateProfileName(
            @Parameter(hidden = true) Long memberId,
            MemberProfileUpdate request);
}