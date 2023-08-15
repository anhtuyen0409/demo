package com.nguyenanhtuyen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nguyenanhtuyen.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//set dich vu de tim user trong db va set passwordencoder
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		//cac trang khong yeu cau login
		http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();
		
		//trang userInfo yeu cau phai login voi vai tro ROLE_USER or ROLE_ADMIN
		//neu chua login se redirect toi trang login
		http.authorizeRequests().antMatchers("/userInfo").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
		
		//trang chi danh do admin
		http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");
		
		//neu nguoi dung da login voi role la ROLE_USER
		//nhung truy cap vao trang yeu cau role la ROLE_ADMIN
		//khi do ngoai le AccessDeniedException se nem ra
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		
		//config login form
		http.authorizeRequests().and().formLogin()
				//submit URL login page
				.loginPage("/login")
				.defaultSuccessUrl("/userAccountInfo") //duong dan toi trang dang nhap thanh cong
				.failureUrl("/login?error=true") //duong dan toi trang dang nhap that bai
				.usernameParameter("username")
				.passwordParameter("password")
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
	}
}
