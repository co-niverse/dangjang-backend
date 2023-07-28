package com.coniverse.dangjang.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;

import com.coniverse.dangjang.domain.auth.controller.LoginController;
import com.coniverse.dangjang.domain.auth.service.OauthLoginService;
import com.coniverse.dangjang.domain.healthMetric.controller.HealthMetricRegistrationController;
import com.coniverse.dangjang.domain.healthMetric.service.bloodSugar.BloodSugarRegistrationService;
import com.coniverse.dangjang.domain.healthMetric.util.CreatedAtUtil;
import com.coniverse.dangjang.domain.intro.controller.IntroController;
import com.coniverse.dangjang.domain.intro.service.IntroService;
import com.coniverse.dangjang.domain.user.controller.SignUpController;
import com.coniverse.dangjang.domain.user.controller.UserController;
import com.coniverse.dangjang.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * WebMvc Test parent class
 * <br/>
 * contollers에 테스트할 controller를 등록하고, controller에서 주입하는 bean들을 MockBean으로 등록한다.
 *
 * @author TEO
 * @since 1.0.0
 */
@WebMvcTest(
	controllers = {
		IntroController.class,
		LoginController.class,
		HealthMetricRegistrationController.class,
		LoginController.class,
		SignUpController.class,
		UserController.class
	},
	includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
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
	private BloodSugarRegistrationService bloodSugarService;
	@MockBean
	private CreatedAtUtil createdAtUtil;
	@MockBean
	private UserService userService;

}
