package com.coniverse.dangjang.global.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.coniverse.dangjang.domain.auth.filter.JwtValidationFilter;
import com.coniverse.dangjang.domain.auth.handler.JwtAccessDeniedHandler;
import com.coniverse.dangjang.domain.auth.handler.JwtAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

/**
 * Spring Security를 설정한다.
 *
 * @author TEO, EVE
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtValidationFilter jwtValidationFilter;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Value("${api.version.uri-prefix}")
	private final String uriPrefix;
	@Value("${cors.allowed-origins}")
	private final String allowedOrigins;

	/**
	 * SecurityFilterChain 설정
	 * <p>
	 * csrf : OAuth2 + JWT를 사용하기 때문에 Stateless하다. 따라서 csrf token을 사용하지 않는다.
	 * <br/>
	 * formLogin : 로그인 시 JWT를 생성하는 filter를 적용하므로 formLogin을 사용하지 않는다.
	 * <br/>
	 * httpBasic : request header에 id, password를 그대로 노출하므로 보안에 취약하다. JWT를 사용하기 때문에 사용하지 않는다.
	 * <br/>
	 * SessionCreationPolicy.STATELESS : 세션을 생성하지 않는다.
	 *
	 * @since 1.0.0
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(
				HeadersConfigurer.FrameOptionsConfig::sameOrigin))
			.csrf(AbstractHttpConfigurer::disable)
			.cors(corsConfigurer -> corsConfigurer.configurationSource(configurationSource()))
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterAt(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(authorize -> authorize
				// version
				.requestMatchers(HttpMethod.GET, uriPrefix + "/version/**").permitAll() //TODO 제거
				.requestMatchers(HttpMethod.GET, uriPrefix + "/*/version/**").permitAll()

				// auth
				.requestMatchers(HttpMethod.POST, uriPrefix + "/auth/logout").authenticated() //TODO 제거
				.requestMatchers(HttpMethod.POST, uriPrefix + "/*/auth/logout").authenticated()
				.requestMatchers(HttpMethod.POST, uriPrefix + "/api/auth/**").permitAll() //TODO 제거
				.requestMatchers(HttpMethod.POST, uriPrefix + "/*/api/auth/**").permitAll()

				// user
				.requestMatchers(HttpMethod.POST, uriPrefix + "/signup/**").permitAll() //TODO 제거
				.requestMatchers(HttpMethod.POST, uriPrefix + "/*/signup/**").permitAll()
				.requestMatchers(HttpMethod.POST, uriPrefix + "/user/**").authenticated() //TODO 제거
				.requestMatchers(HttpMethod.POST, uriPrefix + "/*/user/**").authenticated()
				.requestMatchers(HttpMethod.DELETE, uriPrefix + "/user/**").authenticated() //TODO 제거
				.requestMatchers(HttpMethod.DELETE, uriPrefix + "/*/user/**").authenticated()
				.requestMatchers(HttpMethod.GET, uriPrefix + "/user/duplicateNickname").permitAll() //TODO 제거
				.requestMatchers(HttpMethod.GET, uriPrefix + "/*/user/duplicateNickname").permitAll()
				.requestMatchers(HttpMethod.GET, uriPrefix + "/user/mypage/**").authenticated() //TODO 제거
				.requestMatchers(HttpMethod.GET, uriPrefix + "/*/user/mypage/**").authenticated()

				// health-metric
				.requestMatchers(uriPrefix + "/health-metric/**").authenticated() //TODO 제거
				.requestMatchers(uriPrefix + "/*/health-metric/**").authenticated()

				// health-connect
				.requestMatchers(HttpMethod.POST, uriPrefix + "/health-connect/**").authenticated() //TODO 제거
				.requestMatchers(HttpMethod.POST, uriPrefix + "/*/health-connect/**").authenticated()
				.requestMatchers(HttpMethod.PATCH, uriPrefix + "/health-connect/**").authenticated() //TODO 제거
				.requestMatchers(HttpMethod.PATCH, uriPrefix + "/*/health-connect/**").authenticated()

				// guide
				.requestMatchers(HttpMethod.GET, uriPrefix + "/guide/**").authenticated() //TODO 제거
				.requestMatchers(HttpMethod.GET, uriPrefix + "/*/guide/**").authenticated()

				// point
				.requestMatchers(HttpMethod.POST, uriPrefix + "/point/**").authenticated() //TODO 제거
				.requestMatchers(HttpMethod.POST, uriPrefix + "/*/point/**").authenticated()
				.requestMatchers(HttpMethod.GET, uriPrefix + "/point/**").authenticated() //TODO 제거
				.requestMatchers(HttpMethod.GET, uriPrefix + "/*/point/**").authenticated()

				// log
				.requestMatchers(HttpMethod.POST, uriPrefix + "/log/**").permitAll() //TODO 제거
				.requestMatchers(HttpMethod.POST, uriPrefix + "/*/log/**").permitAll()

				// notification
				.requestMatchers(HttpMethod.GET, uriPrefix + "/notification/**").authenticated() //TODO 제거
				.requestMatchers(HttpMethod.GET, uriPrefix + "/*/notification/**").authenticated()
				.requestMatchers(HttpMethod.PATCH, uriPrefix + "/notification/**").authenticated() //TODO 제거
				.requestMatchers(HttpMethod.PATCH, uriPrefix + "/*/notification/**").authenticated()

				// etc
				.anyRequest().permitAll()
			)
			.exceptionHandling(handler -> handler.accessDeniedHandler(jwtAccessDeniedHandler)
				.authenticationEntryPoint(jwtAuthenticationEntryPoint));
		return http.build();
	}

	/**
	 * CORS 설정
	 *
	 * @since 1.0.0
	 */
	@Bean
	public CorsConfigurationSource configurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of(allowedOrigins));
		configuration.setAllowedMethods(List.of("*"));
		configuration.setAllowedHeaders(List.of("*", "Authorization"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
