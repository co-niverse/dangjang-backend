package dangjang.challenge.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Spring Security를 설정한다.
 *
 * @author Teo
 * @since 1.0
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
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
			.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());

		return http.build();
	}

	@Bean
	public CorsConfigurationSource configurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("*"));
		configuration.setAllowedMethods(List.of("*"));
		configuration.setAllowedHeaders(List.of("*", "Authorization"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
