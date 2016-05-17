package com.worksap.stm2016;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.worksap.stm2016.service.CurrentUserDetailsService;
import com.worksap.stm2016.utils.CommonUtils;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CurrentUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/resources/**", "/logerror", "/add", "/addAct", "/register", "/registerAct", "/", "/index").permitAll().anyRequest().authenticated()
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
		auth.userDetailsService(userDetailsService).passwordEncoder(CommonUtils.passwordEncoder());
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
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		return localeChangeInterceptor;
	}

	@Bean(name = "localeResolver")
	public LocaleResolver sessionLocaleResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("en"));
		return localeResolver;
	}
	

	
}
