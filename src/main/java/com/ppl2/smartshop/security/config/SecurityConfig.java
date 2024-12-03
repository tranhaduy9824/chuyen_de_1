package com.ppl2.smartshop.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.ppl2.smartshop.security.service.UserService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	@Autowired
	private UserService userDetailsService;
	@Autowired
	private CustomSuccessLoginHandler customSuccessLoginHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http, UserService userDetailService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder()).and().build();

	}

	@Bean
	public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
		return new HttpSessionSecurityContextRepository();

	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and().securityContext()
				.securityContextRepository(httpSessionSecurityContextRepository()).and()
				.authorizeRequests((requests) -> requests
						.antMatchers("/home", "/login", "/register", "/product-list", "/product-detail/**", "/faq",
								"/contact", "/policy", "/product-search", "/404", "/403", "/", "/shop-detail/**")
						.permitAll().antMatchers("/user/profile", "/cart/**", "/cart", "/order/history")
						.hasRole("CUSTOMER").antMatchers("/shop/**").hasRole("SHOP").antMatchers("/admin/**")
						.hasRole("ADMIN").anyRequest().authenticated());

		http.formLogin((form) -> form.loginPage("/login").successHandler(customSuccessLoginHandler)
				.failureUrl("/login?error=true").permitAll()) // Điều hướng đến trang đăng nhập với thông báo lỗi)
				.logout((logout) -> logout.logoutSuccessUrl("/login").permitAll().deleteCookies("JSESSIONID")
						.invalidateHttpSession(true))
				.rememberMe((rememberMe) -> rememberMe.rememberMeParameter("remember").tokenValiditySeconds(86400 * 30)
						.userDetailsService(userDetailsService)
						.authenticationSuccessHandler(customSuccessLoginHandler));

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.debug(false).ignoring().antMatchers("/assets/**", "/css/**", "/js/**", "/img/**", "/lib/**",
				"/favicon.ico");
	}
}