package com.mockproject.quizweb.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // Non-login required
        http.authorizeRequests().antMatchers("/", "/login", "/logout",
                "/webjars/**", "/css/**", "/js/**", "/img/**").permitAll();

        // AccessDeniedException
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        // Authorized all
        http.authorizeRequests().anyRequest().authenticated();

        // Login Form.
        http.authorizeRequests().and().formLogin()
                // Login summit url.
                .loginProcessingUrl("/login-process")
                .loginPage("/login")
                .defaultSuccessUrl("/student-home-page")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                // Logout Page.
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");
    }
}
