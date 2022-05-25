package com.huyhoang.covid19.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.huyhoang.covid19.authentication.CustomAccessDeniedHandler;
import com.huyhoang.covid19.authentication.JwtAuthenticationTokenFilter;
import com.huyhoang.covid19.authentication.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
		jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
		return jwtAuthenticationTokenFilter;
	}

	@Bean
	public RestAuthenticationEntryPoint restServicesEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	protected void configure(HttpSecurity http) throws Exception {
		// Disable crsf cho đường dẫn /api/**
		http.csrf().ignoringAntMatchers("/api/**");

		// All permit
		http.authorizeRequests().antMatchers("/api/login**").permitAll();
		http.authorizeRequests().antMatchers("/api/signup**").permitAll();
		http.authorizeRequests().antMatchers("/api/getimage**").permitAll();
		http.authorizeRequests().antMatchers("/api/sentmail**").permitAll();
		http.authorizeRequests().antMatchers("/api/resetpassword**").permitAll();
		http.authorizeRequests().antMatchers("/api/user**").permitAll();
		
		// or hasRole('ROLE_USER') or hasRole('ROLE_AUTHOR') or
		// hasRole('ROLE_MODERATOR')
		http.antMatcher("/api/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				// User
				.antMatchers(HttpMethod.GET, "/api/users").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.GET, "/api/users/**")
				.access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_AUTHOR') or hasRole('ROLE_MODERATOR')")
				.antMatchers(HttpMethod.POST, "/api/users").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.DELETE, "/api/users/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.PUT, "/api/users/**").access("hasRole('ROLE_USER')")
				// Follow
				.antMatchers(HttpMethod.POST, "/api/follow/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.DELETE, "/api/follow/**").access("hasRole('ROLE_USER')")
				// Medical
				.antMatchers(HttpMethod.GET, "/api/medical**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.GET, "/api/medical/**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.POST, "/api/medical**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.PUT, "/api/medical**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.DELETE, "/api/medical**").access("hasRole('ROLE_USER')")
				// Post
				.antMatchers(HttpMethod.GET, "/api/posts**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.POST, "/api/posts**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.PUT, "/api/posts**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.DELETE, "/api/posts**")
				.access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
				// Like
				.antMatchers(HttpMethod.POST, "/api/like**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.DELETE, "/api/like**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.GET, "/api/getlike**").access("hasRole('ROLE_USER')")
				// Comment
				.antMatchers(HttpMethod.GET, "/api/comment**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.POST, "/api/comment**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.DELETE, "/api/comment**")
				.access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
				// News
				.antMatchers(HttpMethod.GET, "/api/news**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.POST, "/api/news**").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.PUT, "/api/news**").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.DELETE, "/api/news**").access("hasRole('ROLE_ADMIN')")
				//Category
				.antMatchers(HttpMethod.GET, "/api/category**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.POST, "/api/category**").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.DELETE, "/api/category**").access("hasRole('ROLE_ADMIN')")
				//Department
				.antMatchers(HttpMethod.GET, "/api/departments**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.POST, "/api/departments**").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.DELETE, "/api/departments**").access("hasRole('ROLE_ADMIN')")
				//Doctor
				.antMatchers(HttpMethod.GET, "/api/doctors**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.POST, "/api/doctors**").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.DELETE, "/api/doctors**").access("hasRole('ROLE_ADMIN')")
				//Slot
				.antMatchers(HttpMethod.GET, "/api/slots**").access("hasRole('ROLE_DOCTOR') or hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.POST, "/api/slots**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.DELETE, "/api/slots**").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.GET, "/api/slots/available**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.GET, "/api/slots/user**").access("hasRole('ROLE_USER')")
				.and()
				.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
	}
}
