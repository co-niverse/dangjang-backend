package dangjang.challenge.domain.login.controller;

import dangjang.challenge.domain.login.model.AuthToken.AuthTokens;
import dangjang.challenge.domain.login.model.Params.KakaoLoginParams;
import dangjang.challenge.domain.login.model.Params.NaverLoginParams;
import dangjang.challenge.domain.login.service.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {
    private final OAuthLoginService oAuthLoginService;

    //카카오 로그인
    @PostMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    //네이버 로그인
    @PostMapping("/naver")
    public ResponseEntity<AuthTokens> loginNaver(@RequestBody NaverLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }
}


