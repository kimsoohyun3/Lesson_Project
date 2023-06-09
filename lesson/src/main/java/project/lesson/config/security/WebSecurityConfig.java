package project.lesson.config.security;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.AllArgsConstructor;
import project.lesson.filter.JwtAuthenticationFilter;

@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
			.and()
			.httpBasic()
			.disable()
			.csrf()
			.disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers(
				"/member/join",
				"/auth/**",
				"/member/find/{email}",
				"/member/info/{memberId}",
				"/v1/teacherPost/{postId}",
				"/teacherPost/myPosts/{memberId}",
				"/v1/teacherPosts",
				"/v1/studentPost/{postId}",
				"/v1/studentPosts",
				"/v1/notice/**",
				"/v1/notices",
				"/oauth/kakao/**",
				"/member/modify-password"
			)
			.permitAll()
			.anyRequest()
			.hasAnyRole("USER", "ADMIN")
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
			.and()
			.exceptionHandling()
			.accessDeniedHandler(new CustomAccessDeniedHandler());

		http.addFilterBefore(
			jwtAuthenticationFilter,
			UsernamePasswordAuthenticationFilter.class
		);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
			"/v2/api-docs",
			"/configuration/ui",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/configuration/security",
			"/webjars/**",
			"/swagger/**"
		);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOriginPatterns(Collections.singletonList("*")); // 변경된 부분
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
