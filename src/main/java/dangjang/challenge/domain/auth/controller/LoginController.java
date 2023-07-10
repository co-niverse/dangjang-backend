package dangjang.challenge.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dangjang.challenge.domain.auth.dto.request.KakaoLoginParams;
import dangjang.challenge.domain.auth.dto.request.NaverLoginParams;
import dangjang.challenge.domain.auth.service.OAuthLoginService;
import dangjang.challenge.global.dto.SuccessResponse;
import dangjang.challenge.global.dto.content.Content;
import dangjang.challenge.global.exception.NonExistentUserException;
import lombok.RequiredArgsConstructor;

/**
 * @author Eve
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {
	private final OAuthLoginService oAuthLoginService;

	/**
	 * @param params 카카오 accessToken을 받아온다.
	 * @return ResponseEntity 로그인을 성공하면, JWT TOKEN과 사용자 정보(nickname, authID)를 전달한다.
	 * @throws NonExistentUserException 회원가입된 유저가 아닐때 발생하는 오류
	 * @since 1.0
	 */
	@PostMapping("/kakao")
	public ResponseEntity<SuccessResponse> loginKakao(@RequestBody KakaoLoginParams params) {
		Content content = oAuthLoginService.login(params);
		return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK.getReasonPhrase(), content));
	}

	/**
	 * @param params 네이버 accessToken을 받아온다.
	 * @return ResponseEntity 로그인을 성공하면, JWT TOKEN과 사용자 정보(nickname, authID)를 전달한다.
	 * @throws NonExistentUserException 회원가입된 유저가 아닐때 발생하는 오류
	 * @since 1.0
	 */

	//네이버 로그인
	@PostMapping("/naver")
	public ResponseEntity<SuccessResponse> loginNaver(@RequestBody NaverLoginParams params) {
		Content content = oAuthLoginService.login(params);
		return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK.getReasonPhrase(), content));
	}
}

