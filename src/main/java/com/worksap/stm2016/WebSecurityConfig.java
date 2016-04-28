package com.worksap.stm2016;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.worksap.stm2016.service.PersonService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private PersonService personService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/resources/**", "/logerror", "/add", "/addAct", "/", "/index").permitAll().anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.failureUrl("/login?error=er")
			.permitAll()
			.successHandler(loginSuccessHandler())
			.and()
			.logout()
			.logoutSuccessUrl("/").logoutUrl("/logout").permitAll()
			.and()
			.rememberMe()
			.tokenValiditySeconds(1209600);	
			//tokenRepository(tokenRepository());
		
		/*
		 * http.authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .antMatchers("/user/**").access("hasRole('ADMIN') or hasRole('USER')")
                .antMatchers("/index").permitAll()
                .antMatchers("/").permitAll()
            .and()s
                .formLogin()
                .permitAll()
            .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
		 * */
		
			//.and()
			//.rememberMe()
			//.tokenValiditySeconds(1209600)
			//.tokenRepository(tokenRepository());
		//http.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
		// For testing purpose only, turn off csrf to allow http request without
		// protection
//		 http.csrf().disable();

	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(personService).passwordEncoder(new BCryptPasswordEncoder());

	}
	/*
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = (PasswordEncoder) new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Bean
	public JdbcTokenRepositoryImpl tokenRepository(){
		JdbcTokenRepositoryImpl j=new JdbcTokenRepositoryImpl();
		//j.setDataSource(dataSource1);
		return j;
	}
	*/
	
	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new LoginSuccessHandler();
	}
	

	
}
