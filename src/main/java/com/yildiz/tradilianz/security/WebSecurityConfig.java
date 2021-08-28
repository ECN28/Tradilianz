package com.yildiz.tradilianz.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yildiz.tradilianz.security.jwt.AuthEntryPointJwt;
import com.yildiz.tradilianz.security.jwt.AuthTokenFilter;
import com.yildiz.tradilianz.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		/*
		 * securedEnabled = true,
		 * jsr250Enabled = true
		 */
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	private UserDetailsServiceImpl userDetailsService;
	private AuthEntryPointJwt unauthorizedHandler;
	private AuthTokenFilter authenticationJwtTokenFilter;
	
	public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler,
			AuthTokenFilter authenticationJwtTokenFilter) {
		this.userDetailsService = userDetailsService;
		this.unauthorizedHandler = unauthorizedHandler;
		this.authenticationJwtTokenFilter = authenticationJwtTokenFilter;
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			.antMatchers("/api/test/**").permitAll()
			.antMatchers("/error/**").permitAll()
			.antMatchers("/auth/**").permitAll()
			.antMatchers("/retailers**").permitAll()
			.antMatchers("/retailers/**").permitAll()
			.antMatchers("/customers**").permitAll()
			.antMatchers("/customers/**").permitAll()
			.antMatchers("/products**").permitAll()
			.antMatchers("/products/**").permitAll()
			.antMatchers("/orders**").permitAll()
			.antMatchers("/orders/**").permitAll()
			.anyRequest().authenticated();
		
		http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
}
