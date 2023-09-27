package com.coniverse.dangjang.domain.point.service;

import static com.coniverse.dangjang.fixture.PointFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.healthmetric.enums.HealthConnect;
import com.coniverse.dangjang.domain.point.dto.request.UsePointRequest;
import com.coniverse.dangjang.domain.point.dto.response.ProductsResponse;
import com.coniverse.dangjang.domain.point.enums.EarnPoint;
import com.coniverse.dangjang.domain.point.enums.PointType;
import com.coniverse.dangjang.domain.point.repository.PointProductRepository;
import com.coniverse.dangjang.domain.point.repository.PointRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.global.exception.InvalidTokenException;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * 포인트 테스트
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PointServiceTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PointService pointService;
	@Autowired
	private PointProductRepository pointProductRepository;
	@Autowired
	private PointRepository pointRepository;
	@Autowired
	private PointSearchService pointSearchService;
	@Autowired
	private EntityManager em;

	private User 유저;
	private LocalDate today = LocalDate.now();

	@BeforeAll
	public void setUp() {
		pointProductRepository.saveAll(전체_포인트_상품_목록());

	}

	@AfterEach
	public void tearDown() {
		pointRepository.deleteAll();
		userRepository.deleteAll();

	}

	@Order(100)
	@Test
	public void 회원가입_포인트_적립을_받는다() {
		//given
		유저 = userRepository.save(포인트_유저(today));
		int AccessPoint = 유저.getPoint() + EarnPoint.REGISTER.getChangePoint();

		//when
		pointService.addSignupPoint(유저.getOauthId());

		//then
		User 접속한_유저 = userRepository.findById(유저.getOauthId()).get();
		assertThat(접속한_유저.getPoint()).isEqualTo(AccessPoint);

	}

	@Order(200)
	@Test
	public void 하루에_한번_접속하면_포인트_적립을_받는다() throws InterruptedException {
		//given
		유저 = userRepository.save(포인트_유저(today.minusDays(1)));
		int AccessPoint = 유저.getPoint() + EarnPoint.ACCESS.getChangePoint();
		//when
		pointService.addAccessPoint(유저.getOauthId());

		//TODO 비동기 메서드의 테스트 하는 방법 수정
		Thread.sleep(3000);

		//then
		User 접속한_유저 = userRepository.findById(유저.getOauthId()).get();
		assertThat(접속한_유저.getPoint()).isEqualTo(AccessPoint);
	}

	@Order(300)
	@Test
	@Transactional
	public void Health_Connect_연동_포인트_적립을_받는다() {
		//given
		유저 = userRepository.save(포인트_유저(today));
		유저.setHealthConnect(HealthConnect.NEVER_CONNECTED);
		int accessPoint = 유저.getPoint() + EarnPoint.HEALTH_CONNECT.getChangePoint();

		//when
		pointService.addHealthConnectPoint(유저);
		em.flush();
		//then
		User 접속한_유저 = userRepository.findById(유저.getOauthId()).get();
		assertThat(접속한_유저.getPoint()).isEqualTo(accessPoint);
	}

	@Order(400)
	@Test
	public void 포인트가_부족하면_에러를_발생한다() {
		//given
		유저 = userRepository.save(포인트_유저(today));

		UsePointRequest request = new UsePointRequest(유저.getOauthId(), "스타벅스_오천원_금액권");

		//when&then
		InvalidTokenException exception = assertThrows(InvalidTokenException.class, () -> {
			pointService.purchaseProduct(유저.getOauthId(), request);
		});
	}

	@Order(500)
	@Transactional
	@ParameterizedTest
	@ValueSource(strings = {"스타벅스_오천원_금액권", "CU_오천원_금액권", "네이버페이_오천원_금액권"})
	public void 포인트_상품을_구매한다(String product) {
		//given
		유저 = userRepository.save(포인트_유저(today));
		userRepository.updatePointByOauthId(유저.getOauthId(), 6000);
		int balancePoint = userRepository.findById(유저.getOauthId()).get().getPoint();
		int accessPoint = balancePoint - 5000;

		UsePointRequest request = new UsePointRequest(유저.getOauthId(), product);

		//when
		pointService.purchaseProduct(유저.getOauthId(), request);
		em.flush();

		//then
		User 접속한_유저 = userRepository.findById(유저.getOauthId()).get();
		assertThat(접속한_유저.getPoint()).isEqualTo(accessPoint);
	}

	@Order(600)
	@Test
	public void 포인트_상품을_조회한다() {
		//given
		유저 = userRepository.save(포인트_유저(today));
		int productSize = pointSearchService.findAllByType(PointType.USE).size();
		//when
		ProductsResponse response = pointService.getProducts(유저.getOauthId());
		//then
		assertThat(response.balancedPoint()).isEqualTo(유저.getPoint());
		assertThat(response.products()).hasSize(productSize);
	}

}
