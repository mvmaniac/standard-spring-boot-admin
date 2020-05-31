package io.devfactory.global.config.security;

import io.devfactory.global.config.security.common.AjaxLoginAuthenticationEntryPoint;
import io.devfactory.global.config.security.filter.AjaxLoginProcessingFilter;
import io.devfactory.global.config.security.provider.AjaxAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// TODO: config 를 하나로 통일? ajax 일 때도 csrf를 사용하도록?
@RequiredArgsConstructor
@Order(0)
@Configuration
public class AjaxSecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;

  private final AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
  private final AuthenticationFailureHandler ajaxAuthenticationFailureHandler;
  private final AccessDeniedHandler ajaxAccessDeniedHandler;

  @Bean
  public AuthenticationProvider ajaxAuthenticationProvider() {
    return new AjaxAuthenticationProvider(passwordEncoder, userDetailsService);
  }

  // Custom Filter 를 만들어서 로그인 처리 하는 방법 1-1
  // 현재는 bean으로 생성만 하고 사용하지 않음 아래처럼 addfilter를 해줘야 함 (1-2 참고)
  @Bean
  public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
    AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();

    ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean());
    ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
    ajaxLoginProcessingFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);

    return ajaxLoginProcessingFilter;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    // @formatter:off
    auth
      .authenticationProvider(ajaxAuthenticationProvider())
    ;
    // @formatter:on
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    // /api 로 시작하는 url 만 처리
    http
      .antMatcher("/api/**")
        .authorizeRequests()
          .antMatchers("/api/messages")
            .hasRole("MANAGER")
          .anyRequest()
            .authenticated()
      .and()

      .exceptionHandling()
        .authenticationEntryPoint(new AjaxLoginAuthenticationEntryPoint())
        .accessDeniedHandler(ajaxAccessDeniedHandler)
      .and()

      // Custom Filter 를 만들어서 로그인 처리 하는 방법 1-2
      //.addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
    ;

    // Custom Filter 가 아닌 Custom DSL로 설정 추가
    http
      .apply(new AjaxSecurityConfigurer<>())
        .setAuthenticationManager(authenticationManagerBean())
        .successHandlerAjax(ajaxAuthenticationSuccessHandler)
        .failureHandlerAjax(ajaxAuthenticationFailureHandler)
        .loginProcessingUrl("/api/login")
      ;
    // @formatter:on
  }

}
