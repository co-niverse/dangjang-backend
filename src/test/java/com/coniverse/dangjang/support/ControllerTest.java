package com.coniverse.dangjang.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;

import com.coniverse.dangjang.domain.auth.controller.LoginController;
import com.coniverse.dangjang.domain.auth.service.JwtTokenProvider;
import com.coniverse.dangjang.domain.auth.service.OauthLoginService;
import com.coniverse.dangjang.domain.guide.bloodsugar.controller.BloodSugarGuideController;
import com.coniverse.dangjang.domain.guide.bloodsugar.service.BloodSugarGuideSearchService;
import com.coniverse.dangjang.domain.guide.common.controller.GuideController;
import com.coniverse.dangjang.domain.guide.common.service.DayGuideService;
import com.coniverse.dangjang.domain.guide.exercise.controller.ExerciseGuideController;
import com.coniverse.dangjang.domain.guide.exercise.service.ExerciseGuideSearchService;
import com.coniverse.dangjang.domain.guide.weight.controller.WeightGuideController;
import com.coniverse.dangjang.domain.guide.weight.service.WeightGuideSearchService;
import com.coniverse.dangjang.domain.healthmetric.controller.HealthConnectController;
import com.coniverse.dangjang.domain.healthmetric.controller.HealthMetricController;
import com.coniverse.dangjang.domain.healthmetric.service.HealthConnectRegisterService;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricChartSearchService;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricRegisterService;
import com.coniverse.dangjang.domain.intro.controller.IntroController;
import com.coniverse.dangjang.domain.intro.service.IntroService;
import com.coniverse.dangjang.domain.user.controller.SignupController;
import com.coniverse.dangjang.domain.user.controller.UserController;
import com.coniverse.dangjang.domain.user.service.UserSignupService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * WebMvc Test parent class
 * <br/>
 * contollers에 테스트할 controller를 등록하고, controller에서 주입하는 bean들을 MockBean으로 등록한다.
 *
 * @author TEO, EVE
 * @since 1.0.0
 */
@WebMvcTest(
	controllers = {
		IntroController.class,
		HealthMetricController.class,
		LoginController.class,
		SignupController.class,
		UserController.class,
		BloodSugarGuideController.class,
		WeightGuideController.class,
		ExerciseGuideController.class,
		BloodSugarGuideController.class,
		HealthConnectController.class,
		GuideController.class
	},
	includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@ComponentScan(basePackages = "com.coniverse.dangjang.domain.auth.handler")
@MockBean(JpaMetamodelMappingContext.class)
public class ControllerTest {
	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected ObjectMapper objectMapper;
	@MockBean
	private IntroService introService;
	@MockBean
	private OauthLoginService oAuthLoginService;
	@MockBean
	private HealthMetricRegisterService healthMetricRegisterService;
	@MockBean
	private UserSignupService userSignupService;
	@MockBean
	private JwtTokenProvider jwtTokenProvider;
	@MockBean
	private BloodSugarGuideSearchService bloodSugarGuideSearchService;
	@MockBean
	private WeightGuideSearchService weightGuideSearchService;
	@MockBean
	private ExerciseGuideSearchService exerciseGuideSearchService;
	@MockBean
	private HealthConnectRegisterService healthConnectRegisterService;
	@MockBean
	private HealthMetricChartSearchService healthMetricChartSearchService;
	@MockBean
	private DayGuideService dayGuideService;
}
