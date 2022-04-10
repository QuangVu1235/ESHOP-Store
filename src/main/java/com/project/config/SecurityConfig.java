package com.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.project.service.AuthoritiesService;
import com.project.service.UsersService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UsersService service;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
	    InMemoryTokenRepositoryImpl memory = new InMemoryTokenRepositoryImpl();
	    return memory;
	}
	
	// Quan li nguoi du lieu nguoi su dung
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	//
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().cors().disable();
		
		//Phan Quyen Su dung
		http.authorizeHttpRequests()
			.antMatchers("/cart").authenticated()
			.antMatchers("/profile/user/**").hasAuthority("CUST")
			.antMatchers("/admin/admin/**").hasAuthority("DIRE")
			.anyRequest().permitAll().and()
			.exceptionHandling().accessDeniedPage("/404");
	
		
		//Giao dien dang nhap
		http.formLogin()
			.loginProcessingUrl("/check/login")
			.loginPage("/login")
			.defaultSuccessUrl("/index",true)
			.failureUrl("/login?error=true")
			.usernameParameter("username")
			.passwordParameter("password");
		http.rememberMe()
			.rememberMeParameter("remember");
		http.logout()
			.logoutUrl("/logoff")
			.logoutSuccessUrl("/index")
			.addLogoutHandler(new SecurityContextLogoutHandler());
		
//		http.authorizeRequests().and() //
//		.rememberMe().tokenRepository(this.persistentTokenRepository()) //
//		.tokenValiditySeconds(1 * 24 * 60 * 60); // 24h
		 
		
	}
}
