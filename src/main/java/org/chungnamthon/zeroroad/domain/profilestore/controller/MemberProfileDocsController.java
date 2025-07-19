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

import org.chungnamthon.zeroroad.domain.profilestore.dto.ImageRequest;
import org.chungnamthon.zeroroad.domain.profilestore.dto.MemberProfileUpdate; // dto 패키지로 변경
import org.chungnamthon.zeroroad.domain.profilestore.dto.UserProfile; // dto 패키지로 변경
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
            @ApiResponse(responseCode = "200", description = "프로필 정보를 조회했습니다.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserProfile.class),
                            examples = @ExampleObject(
                                    name = "profileViewSuccess",
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
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "UNAUTHORIZED_ERROR",
                                    value = """
                                            {
                                                "statusCode": 401,
                                                "message": "ERROR - 인증되지 않은 사용자입니다.",
                                                "validation": {}
                                            }
                                            """
                            )
                    )),

            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "MEMBER_NOT_FOUND",
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
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "BAD_REQUEST",
                                    value = """
                                            {
                                                "statusCode": 400,
                                                "message": "ERROR - 잘못된 요청입니다.",
                                                "validation": {}
                                            }
                                            """
                            )
                    )),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "UNAUTHORIZED_ERROR",
                                    summary = "UNAUTHORIZED_ERROR",
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

    @Operation(
            summary = "프로필 이미지 구매 - JWT O",
            description = """
                    프로필 이미지를 구매하고 즉시 적용합니다. 포인트가 부족하면 실패합니다.
                    """
    )
    @RequestBody(
            description = "구매할 이미지 ID",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(type = "object", example = "{\"imageId\": \"user_2\"}"),
                    examples = @ExampleObject(
                            name = "purchaseImageRequest",
                            value = """
                                    {
                                        "imageId": "image_new_item_id"
                                    }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구매 및 적용 완료 후 변경된 사용자 정보 반환",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserProfile.class),
                            examples = @ExampleObject(
                                    name = "purchaseImageSuccess",
                                    value = """
                                            {
                                                "userName": "제로로드",
                                                "currentPoints": 950,
                                                "currentImageId": "image_new_item_id",
                                                "profileImages": [
                                                    {
                                                        "imageId": "image_123",
                                                        "imageUrl": "http://example.com/image1.png",
                                                        "price": 0,
                                                        "item": true
                                                    },
                                                    {
                                                        "imageId": "image_new_item_id",
                                                        "imageUrl": "http://example.com/new_item.png",
                                                        "price": 50,
                                                        "item": true
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )),
            @ApiResponse(responseCode = "400", description = "포인트 부족 또는 잘못된 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "INSUFFICIENT_POINTS_ERROR", // 이름 변경
                                            summary = "포인트 부족 오류",
                                            value = """
                                                    {
                                                        "statusCode": 400,
                                                        "message": "ERROR - 포인트가 부족합니다.",
                                                        "validation": {}
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "BAD_REQUEST",
                                            summary = "잘못된 이미지 ID",
                                            value = """
                                                    {
                                                        "statusCode": 400,
                                                        "message": "ERROR - 유효하지 않은 이미지 ID입니다.",
                                                        "validation": {}
                                                    }
                                                    """
                                    )
                            }
                    )),
            @ApiResponse(responseCode = "404", description = "프로필 이미지를 찾을 수 없음", // 404 응답 추가
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "PROFILE_IMAGE_NOT_FOUND",
                                    value = """
                                            {
                                                "statusCode": 404,
                                                "message": "ERROR - 프로필 이미지를 찾을 수 없습니다.",
                                                "validation": {}
                                            }
                                            """
                            )
                    )),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "UNAUTHORIZED_ERROR",
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
    public abstract ResponseEntity<UserProfile> purchaseProfileImage(
            @Parameter(hidden = true) Long memberId,
            ImageRequest request);

}