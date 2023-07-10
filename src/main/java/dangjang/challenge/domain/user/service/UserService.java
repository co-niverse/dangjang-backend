package dangjang.challenge.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dangjang.challenge.domain.user.dto.UserInfo;
import dangjang.challenge.domain.user.entity.User;
import dangjang.challenge.domain.user.infrastructure.OAuthInfoResponse;
import dangjang.challenge.domain.user.repository.UserRepository;
import dangjang.challenge.global.exception.NonExistentUserException;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * 기존 유저 확인
	 *
	 * @param oAuthInfoResponse 카카오,네이버에서 사용자 정보 조회한 데이터 (authID ,Provider)
	 * @return userInfo 기존 유저일 때, 유저 정보를 리턴
	 * @throws NonExistentUserException 회원가입된 유저가 아닐때 발생하는 오류
	 * @since 1.0
	 */

	public UserInfo findUser(OAuthInfoResponse oAuthInfoResponse) {
		/**
		 * 기존 유저 여부 확인
		 * @since 1.0
		 */
		Optional<User> user = userRepository.findByOauth(oAuthInfoResponse.getUserId());
		/**
		 * 유저일 때, 유저 정보 return
		 * @since 1.0
		 */
		if (user.isPresent()) {
			UserInfo userInfo = new UserInfo(user.get().getOauth(), user.get().getNickname());
			return userInfo;
		} else {
			/**
			 * 기존 유저가 아닐 때, 404ERR
			 * @since 1.0
			 */
			throw new NonExistentUserException();
		}
	}

	/**
	 * 새로운 유저 회원가입
	 *
	 * @param oAuthInfoResponse 카카오,네이버에서 사용자 정보 조회한 데이터 (authID ,Provider)
	 * @return 새로 가입된 유저 회원가입
	 * @since 1.0
	 */

	public Long newMember(OAuthInfoResponse oAuthInfoResponse) {
		User user = User.builder()
			.oauth(oAuthInfoResponse.getUserId())
			.nickname("nickname")
			.oAuthProvider(oAuthInfoResponse.getOAuthProvider())
			.build();

		return userRepository.save(user).getId();
	}
}
