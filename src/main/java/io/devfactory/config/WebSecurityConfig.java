package io.devfactory.config;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import static org.springframework.security.core.context.SecurityContextHolder.MODE_INHERITABLETHREADLOCAL;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  public static final String LOGIN_VIEW_URL = "/login";

  private final UserDetailsService userDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    final String PASSWORD = "{noop}1234";

    // @formatter:off
    auth
      .inMemoryAuthentication()
        .withUser("user")
        .password(PASSWORD)
        .roles("USER")
      .and()
        .withUser("sys")
        .password(PASSWORD)
        .roles("SYS")
      .and()
        .withUser("amdin")
        .password(PASSWORD)
        .roles("ADMIN")
    ;
    // @formatter:on
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .authorizeRequests()
        .antMatchers("/user").hasRole("USER")
        .antMatchers("/admin/pay").hasRole("ADMIN")
        .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
        .anyRequest()
          .authenticated()
        .and()

      .formLogin()
        //.loginPage("/loginView")
        .defaultSuccessUrl("/")
        .failureUrl(LOGIN_VIEW_URL)
        .usernameParameter("id")
        .passwordParameter("pwd")
        .loginProcessingUrl("/loginProc")
        .successHandler((request, response, authentication) -> {
          final HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
          final SavedRequest savedRequest = requestCache.getRequest(request, response);

          log.debug("[dev] successHandler authentication: {}", authentication.getName());
          log.debug("[dev] successHandler savedRequest: {}", savedRequest.getRedirectUrl());

          response.sendRedirect(savedRequest.getRedirectUrl());
        })
        .failureHandler((request, response, exception) -> {
          log.debug("[dev] failureHandler exception: {}", exception.getMessage());
          response.sendRedirect(LOGIN_VIEW_URL);
        })
        .permitAll()
        .and()

      .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl(LOGIN_VIEW_URL)
        .addLogoutHandler((request, response, authentication) -> {
          log.debug("[dev] addLogoutHandler authentication: {}", authentication.getName());
          final HttpSession session = request.getSession();
          session.invalidate();
        })
        .logoutSuccessHandler((request, response, authentication) -> {
          log.debug("[dev] logoutSuccessHandle authentication: {}", authentication.getName());
          response.sendRedirect(LOGIN_VIEW_URL);
        })
        .deleteCookies("remember-me")
        .and()

      .rememberMe()
        //.rememberMeParameter("remember")
        .tokenValiditySeconds(3600) // 1시간, 기본은 14일
        .userDetailsService(userDetailsService)
        .and()

      .sessionManagement()
        .maximumSessions(1)
          .maxSessionsPreventsLogin(false)
          .and()
        .and()

      .exceptionHandling()
        // 재정의 하는 경우 스프링이 만든 기본 로그인 페이지로 가지 않음...
        /*.authenticationEntryPoint((request, response, authException) -> {
          log.debug("[dev] authenticationEntryPoint authException: {}", authException.getMessage());

          response.sendRedirect(LOGIN_VIEW_URL);
        })*/
        .accessDeniedHandler((request, response, accessDeniedException) -> {
          log.debug("[dev] accessDeniedHandler accessDeniedException: {}", accessDeniedException.getMessage());
          response.sendRedirect("/denied");
        })
    ;
    // @formatter:on

    // 메인 스레드와 자식 스레드 간 ThreadLocal 공유
     SecurityContextHolder.setStrategyName(MODE_INHERITABLETHREADLOCAL);
  }

}
