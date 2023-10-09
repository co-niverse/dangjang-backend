package com.coniverse.dangjang.domain.point.service;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.PointFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthConnectRegisterRequest;
import com.coniverse.dangjang.domain.healthmetric.service.HealthConnectService;
import com.coniverse.dangjang.domain.point.dto.request.UsePointRequest;
import com.coniverse.dangjang.domain.point.dto.response.ProductListResponse;
import com.coniverse.dangjang.domain.point.entity.UserPoint;
import com.coniverse.dangjang.domain.point.enums.EarnPoint;
import com.coniverse.dangjang.domain.point.enums.PointType;
import com.coniverse.dangjang.domain.point.repository.PointHistoryRepository;
import com.coniverse.dangjang.domain.point.repository.PointProductRepository;
import com.coniverse.dangjang.domain.point.repository.PurchaseHistoryRepository;
import com.coniverse.dangjang.domain.point.repository.UserPointRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.global.exception.InvalidPointException;

/**
 * 포인트 Service 테스트
 *
 * @author EVE
 * @since 1.0.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PointHistoryServiceTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserPointRepository userPointRepository;
	@Autowired
	private PointService pointService;
	@Autowired
	private PointProductRepository pointProductRepository;
	@Autowired
	private PointHistoryRepository pointHistoryRepository;
	@Autowired
	private PointSearchService pointSearchService;
	@Autowired
	private HealthConnectService healthConnectService;
	@Autowired
	private PurchaseHistoryRepository purchaseHistoryRepository;

	private User 유저;
	private LocalDate today = LocalDate.now();

	@BeforeAll
	void setUp() {
		pointProductRepository.saveAll(전체_포인트_상품_목록());
	}

	@AfterEach
	void tearDown() {
		pointHistoryRepository.deleteAll();
		purchaseHistoryRepository.deleteAll();
		userPointRepository.deleteAll();
		userRepository.deleteAll();

	}

	@Order(100)
	@Test
	void 회원가입_포인트_적립을_받는다() {
		//given
		유저 = userRepository.save(포인트_유저(today));
		int accessPoint = EarnPoint.REGISTER.getChangePoint();

		//when
		pointService.addSignupPoint(유저.getOauthId());

		//then
		UserPoint 접속한_유저_포인트 = pointSearchService.findUserPointByOauthId(유저.getOauthId());
		assertThat(접속한_유저_포인트.getPoint()).isEqualTo(accessPoint);
	}

	@Order(200)
	@Test
	void 하루에_한번_접속하면_포인트_적립을_받는다() throws InterruptedException {
		//given
		유저 = userRepository.save(포인트_유저(today.minusDays(1)));
		UserPoint 유저_포인트 = userPointRepository.save(유저_포인트_생성(유저.getOauthId(), 500));
		int accessPoint = 유저_포인트.getPoint() + EarnPoint.ACCESS.getChangePoint();
		CountDownLatch latch = new CountDownLatch(1);
		//when
		pointService.addAccessPoint(유저.getOauthId());
		latch.await(1, TimeUnit.SECONDS);

		//then
		User 접속한_유저 = userRepository.findById(유저.getOauthId()).get();
		UserPoint 접속한_유저_포인트 = pointSearchService.findUserPointByOauthId(접속한_유저.getOauthId());
		assertThat(접속한_유저_포인트.getPoint()).isEqualTo(accessPoint);
	}

	@Order(300)
	@Test
	@Transactional
	void Health_Connect_연동_포인트_적립을_받는다() {
		//given
		유저 = userRepository.save(헬스커넥트_연동_유저());
		UserPoint 유저_포인트 = userPointRepository.save(유저_포인트_생성(유저.getOauthId(), 500));
		int accessPoint = 유저_포인트.getPoint() + EarnPoint.HEALTH_CONNECT.getChangePoint();

		//when
		pointService.addHealthConnectPoint(유저);
		//then
		User 접속한_유저 = userRepository.findById(유저.getOauthId()).get();
		UserPoint 접속한_유저_포인트 = pointSearchService.findUserPointByOauthId(접속한_유저.getOauthId());
		assertThat(접속한_유저_포인트.getPoint()).isEqualTo(accessPoint);
	}

	@Order(400)
	@Test
	void 구매중_포인트가_부족하면_에러를_발생한다() {
		//given
		유저 = userRepository.save(포인트_유저(today));
		userPointRepository.save(유저_포인트_생성(유저.getOauthId(), 500));
		UsePointRequest request = new UsePointRequest(유저.getOauthId(), "스타벅스 오천원 금액권");
		//when&then
		assertThatThrownBy(() -> {
			pointService.purchaseProduct(유저.getOauthId(), request);
		}).isInstanceOf(InvalidPointException.class);
	}

	@Order(500)
	@ParameterizedTest
	@ValueSource(strings = {"스타벅스 오천원 금액권", "CU 오천원 금액권", "네이버페이 오천원 금액권"})
	void 포인트_상품을_구매한다(String product) {
		//given
		유저 = userRepository.save(포인트_유저(today));
		UserPoint 유저_포인트 = userPointRepository.save(유저_포인트_생성(유저.getOauthId(), 6000));
		int balancePoint = 유저_포인트.getPoint();
		int accessPoint = balancePoint - 5000;

		UsePointRequest request = new UsePointRequest(유저.getOauthId(), product);

		//when
		pointService.purchaseProduct(유저.getOauthId(), request);

		//then
		User 접속한_유저 = userRepository.findById(유저.getOauthId()).get();
		UserPoint 접속한_유저_포인트 = pointSearchService.findUserPointByOauthId(접속한_유저.getOauthId());
		int 구매_내역_횟수 = purchaseHistoryRepository.findAll().size();
		assertThat(접속한_유저_포인트.getPoint()).isEqualTo(accessPoint);
		assertThat(구매_내역_횟수).isEqualTo(1);
		purchaseHistoryRepository.deleteAll();
	}

	@Order(550)
	@Test
	void 존재하지_않는_포인트_상품을_구매하면_예외를_던진다() {
		//given
		유저 = userRepository.save(포인트_유저(today));
		UserPoint 유저_포인트 = userPointRepository.save(유저_포인트_생성(유저.getOauthId(), 6000));

		UsePointRequest request = new UsePointRequest(유저.getOauthId(), "두찜_만원");

		//when
		assertThatThrownBy(() -> pointService.purchaseProduct(유저.getOauthId(), request))
			.isInstanceOf(InvalidPointException.class);
	}

	@Order(600)
	@Test
	void 포인트_상품을_조회한다() {
		//given
		유저 = userRepository.save(포인트_유저(today));
		UserPoint 유저_포인트 = userPointRepository.save(유저_포인트_생성(유저.getOauthId(), 500));
		int productSize = pointSearchService.findAllByType(PointType.USE).size();
		//when
		ProductListResponse response = pointService.getProducts(유저.getOauthId());
		//then
		assertThat(response.balancedPoint()).isEqualTo(유저_포인트.getPoint());
		assertThat(response.productList()).hasSize(productSize);
	}

	@Order(700)
	@Test
	void 동시_상품구매_요청_한번만_수행한다() throws InterruptedException {
		//given
		유저 = userRepository.save(포인트_유저(today));
		UserPoint 유저_포인트 = userPointRepository.save(유저_포인트_생성(유저.getOauthId(), 6000));
		UsePointRequest request = new UsePointRequest(유저.getOauthId(), "스타벅스 오천원 금액권");
		ExecutorService executorService = Executors.newFixedThreadPool(30);
		AtomicInteger successCount = new AtomicInteger();
		AtomicInteger failedCount = new AtomicInteger();
		CountDownLatch latch = new CountDownLatch(30);

		for (int i = 0; i < 100; i++) {
			executorService.submit(() -> {
				try {
					pointService.purchaseProduct(유저.getOauthId(), request);
					successCount.incrementAndGet();
				} catch (Exception e) {
					failedCount.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();
		//then
		User 접속한_유저 = userRepository.findById(유저.getOauthId()).get();
		System.out.println("point log start =====================");
		System.out.println("success count : " + successCount.get());
		System.out.println("failed count : " + failedCount.get());
		int 포인트_로그_내역_횟수 = pointHistoryRepository.findAll().size();
		int 구매_내역_횟수 = purchaseHistoryRepository.findAll().size();
		System.out.println("point log finish =====================");
		UserPoint 접속한_유저_포인트 = pointSearchService.findUserPointByOauthId(접속한_유저.getOauthId());
		assertThat(접속한_유저_포인트.getPoint()).isEqualTo(1000);
		assertThat(구매_내역_횟수).isOne();
		assertThat(포인트_로그_내역_횟수).isOne();

	}

	@Order(800)
	@Test
	void 동시_접속_포인트를_한번만_얻는다() throws InterruptedException {
		//given
		유저 = userRepository.save(포인트_유저(today.minusDays(1)));
		UserPoint 유저_포인트 = userPointRepository.save(유저_포인트_생성(유저.getOauthId(), 0));
		AtomicInteger successCount = new AtomicInteger();
		AtomicInteger failedCount = new AtomicInteger();
		CountDownLatch latch = new CountDownLatch(30);
		for (int i = 0; i < 100; i++) {
			try {
				pointService.addAccessPoint(유저.getOauthId());
				successCount.incrementAndGet();
			} catch (Exception e) {
				failedCount.incrementAndGet();
			}
		}
		latch.await(1, TimeUnit.SECONDS);

		//then
		User 접속한_유저 = userRepository.findById(유저.getOauthId()).get();
		System.out.println("point log start =====================");
		System.out.println("success count : " + successCount.get());
		System.out.println("failed count : " + failedCount.get());
		System.out.println("point log finish =====================");
		int 포인트_로그_내역_횟수 = pointHistoryRepository.findAll().size();
		UserPoint 접속한_유저_포인트 = pointSearchService.findUserPointByOauthId(접속한_유저.getOauthId());
		assertThat(접속한_유저_포인트.getPoint()).isEqualTo(100);
		assertThat(포인트_로그_내역_횟수).isOne();
	}

	@Order(900)
	@Test
	void 동시_헬스커넥트_연결요청_포인트를_한번만_얻는다() throws InterruptedException {
		//given
		유저 = userRepository.save(포인트_유저(today));
		UserPoint 유저_포인트 = userPointRepository.save(유저_포인트_생성(유저.getOauthId(), 0));
		HealthConnectRegisterRequest request = 헬스_커넥트_연동_요청(true);
		// when
		ExecutorService executorService = Executors.newFixedThreadPool(30);
		AtomicInteger successCount = new AtomicInteger();
		AtomicInteger failedCount = new AtomicInteger();
		CountDownLatch latch = new CountDownLatch(30);
		for (int i = 0; i < 30; i++) {
			executorService.submit(() -> {
				try {
					healthConnectService.interlockHealthConnect(request, 유저.getOauthId());
					successCount.incrementAndGet();
				} catch (Exception e) {
					System.out.println("error : " + e.getMessage());
					failedCount.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();

		//then
		User 연동된_유저 = userRepository.findById(유저.getOauthId()).get();
		System.out.println("point log start =====================");
		System.out.println("success count : " + successCount.get());
		System.out.println("failed count : " + failedCount.get());
		System.out.println("point log finish =====================");
		int 포인트_로그_내역_횟수 = pointHistoryRepository.findAll().size();
		UserPoint 접속한_유저_포인트 = pointSearchService.findUserPointByOauthId(연동된_유저.getOauthId());
		assertThat(접속한_유저_포인트.getPoint()).isEqualTo(500);
		assertThat(포인트_로그_내역_횟수).isOne();
	}

}
