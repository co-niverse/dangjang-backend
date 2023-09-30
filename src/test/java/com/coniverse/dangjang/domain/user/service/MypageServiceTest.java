package com.coniverse.dangjang.domain.user.service;

import static com.coniverse.dangjang.fixture.PointFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.point.repository.UserPointRepository;
import com.coniverse.dangjang.domain.user.dto.response.MypageResponse;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

/**
 * 마이페이지 서비스 테스트
 *
 * @author EVE
 * @version 1.0.0
 */
@SpringBootTest
public class MypageServiceTest {
	@Autowired
	private MypageService mypageService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserPointRepository userPointRepository;
	private User 유저;

	@Test
	public void getMypage() {
		// given
		유저 = userRepository.save(헬스커넥트_연동_유저(LocalDate.now()));
		int 유저_포인트 = userPointRepository.save(유저_포인트_생성(유저.getOauthId(), 1000)).getPoint();
		// when
		MypageResponse response = mypageService.getMypage(유저.getOauthId());
		// then
		assertThat(response.nickname()).isEqualTo(유저.getNickname());
		assertThat(response.point()).isEqualTo(유저_포인트);
	}
}
