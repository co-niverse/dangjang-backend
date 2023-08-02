package com.coniverse.dangjang.domain.user.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * 회원가입 Request Parm
 *
 * @author EVE
 * @since 1.0.0
 */

public record SignUpRequest(@NotBlank(message = "oauth 접근 토큰은 필수로 입력해야 합니다.") String accessToken,
							@Pattern(regexp = "^[a-z]*$", message = "provider는 소문자여야 합니다.") @NotBlank(message = "oauth provider는 필수로 입력해야 합니다.") String provider,
							@Pattern(regexp = "^[a-zA-Z]{1,8}$", message = "닉네임은 영어 1~8글자 이내로 이루어져있어야 합니다.") String nickname,
							@NotNull(message = "성별은 필수로 입력해야 합니다.") Boolean gender,
							@NotNull(message = "생년월일은 필수로 입력해야 합니다.") @Past(message = "생년월일은 현재보다 과거이어야 합니다.") LocalDate birthday,

							@PositiveOrZero(message = "키는 0cm 이상이어야 합니다.") int height,
							@PositiveOrZero(message = "몸무게는 0kg 이상이어야 합니다.") int weight,
							@Pattern(regexp = "^[A-Z]*${1,5}", message = "하루 활동량은 대문자로 입력해야 합니다.") @NotBlank(message = "하루 활동량은 필수로 입력해야 합니다.") String activityAmount,
							@NotNull(message = "당뇨여부는 필수로 입력해야 합니다.") Boolean diabetes,
							@PositiveOrZero(message = "당뇨기간은 0년 이상이여야 합니다.") @NotNull(message = "당뇨기간은 필수로 입력해야 합니다.") int diabetesYear,
							@NotNull(message = "약여부는 필수로 입력해야 합니다.") Boolean medicine,
							@NotNull(message = "주사여부는 필수로 입력해야 합니다.") Boolean injection,
							@NotNull(message = "질병정보는 필수로 입력해야 합니다.") List<@NotBlank(message = "질병정보는 필수로 입력해야 합니다.") String> diseases) {

}
