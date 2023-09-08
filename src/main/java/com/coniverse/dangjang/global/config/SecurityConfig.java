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

/**
 * Spring Security를 설정한다.
 *
 * @author TEO, EVE
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Value("${cors.allowed-origins}")
	private String allowedOrigins;
	private final JwtValidationFilter jwtValidationFilter;

	public SecurityConfig(JwtValidationFilter jwtValidationFilter) {
		this.jwtValidationFilter = jwtValidationFilter;
	}

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
			.sessionManagement(
				sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(
					SessionCreationPolicy.STATELESS))
			.addFilterAt(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(HttpMethod.GET, "/api/intro/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/signup/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/duplicateNickname/**").permitAll() // TODO 수정
				.requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/api-docs/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/health-metric/**").authenticated()
				.anyRequest().permitAll()
			);
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
