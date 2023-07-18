package com.coniverse.dangjang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;

import com.coniverse.dangjang.domain.auth.controller.LoginController;
import com.coniverse.dangjang.domain.auth.service.OauthLoginService;
import com.coniverse.dangjang.domain.intro.controller.IntroController;
import com.coniverse.dangjang.domain.intro.service.IntroService;

/**
 * WebMvc Test parent class
 * <br/>
 * contollers에 테스트할 controller를 등록하고, controller에서 주입하는 bean들을 MockBean으로 등록한다.
 *
 * @author TEO
 * @since 1.0
 */
@WebMvcTest(
	controllers = {
		IntroController.class,
		LoginController.class
	},
	includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@MockBean(JpaMetamodelMappingContext.class)
public class ControllerTest {
	@Autowired
	protected MockMvc mockMvc;
	@MockBean
	private IntroService introService;
	@MockBean
	private OauthLoginService oAuthLoginService;
}
