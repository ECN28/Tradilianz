package com.yildiz.tradilianz.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//Deaktiviere Spring Security vorerst zum Testen
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers("/").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/delete/**").authenticated().and().httpBasic().and().csrf().disable();
	}

}
