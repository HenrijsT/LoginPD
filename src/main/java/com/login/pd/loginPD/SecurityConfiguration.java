package com.login.pd.loginPD;

import com.login.pd.loginPD.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    LoggingService loggingService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/register", "/static/**", "/login", "/save-user", "/default").permitAll()
                .and()
        .authorizeRequests()
                .antMatchers("/user", "/show-users").hasAnyRole("ADMIN", "USER")
                .antMatchers( "/edit-user", "/edit-save-user", "/resources/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
        .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/default")
                .failureUrl("/login?error")
                .permitAll()
                .successHandler(new AuthenticationSuccessHandler() {
                    //Logs successfull login
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        String username = userDetails.getUsername();
                        loggingService.logUserLoggedIn(username);
                        httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
                    }
                })
                .and()
        .logout()
                .permitAll()
                .addLogoutHandler(new LogoutHandler() {
                    //Logs successfull logout
                    @Override
                    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        String username = userDetails.getUsername();
                        try {
                            loggingService.logUserLoggedOut(username);
                            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .and()
        .csrf().disable();
    }


    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
