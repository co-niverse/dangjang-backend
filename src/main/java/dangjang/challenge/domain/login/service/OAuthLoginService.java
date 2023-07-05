package dangjang.challenge.domain.login.service;

import dangjang.challenge.domain.login.model.AuthToken.AuthTokens;
import dangjang.challenge.domain.login.model.AuthToken.AuthTokensGenerator;
import dangjang.challenge.domain.login.model.Params.OAuthLoginParams;
import dangjang.challenge.domain.login.model.response.OAuthInfoResponse;
import dangjang.challenge.domain.login.repository.MemberRepository;
import dangjang.challenge.global.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        System.out.println("OAuthLoginParams : " + params);
        //프로필 정보 가져오기
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        //유저 존재 확인 , 첫 유저 회원가입
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        // AuthTokens 반환
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        Optional<Member> member = memberRepository.findByEmail(oAuthInfoResponse.getEmail());
        // 기존 멤버인지 확인
        if (member.isPresent()) {
            return member.get().getId();
        } else {
            // 기존 멤버가 아닐 때
            return newMember(oAuthInfoResponse);
        }
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return memberRepository.save(member).getId();
    }
}