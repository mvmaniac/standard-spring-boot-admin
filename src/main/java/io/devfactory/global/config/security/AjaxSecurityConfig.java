package io.devfactory.global.config.security;

import io.devfactory.global.config.security.common.AjaxLoginAuthenticationEntryPoint;
import io.devfactory.global.config.security.filter.AjaxLoginProcessingFilter;
import io.devfactory.global.config.security.provider.AjaxAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// TODO: config 를 하나로 통일
@RequiredArgsConstructor
@Order(1)
@Configuration
public class AjaxSecurityConfig {

  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;

  private final AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
  private final AuthenticationFailureHandler ajaxAuthenticationFailureHandler;
  private final AccessDeniedHandler ajaxAccessDeniedHandler;

  @Bean
  public SecurityFilterChain ajaxFilterChain(HttpSecurity http) throws Exception {
    // @formatter:off
    // /api 로 시작하는 url 만 처리
    final var httpSecurity = http
      .securityMatcher("/api/**")
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/api/messages")
          .hasRole("MANAGER")
        .anyRequest()
          .authenticated())
      .exceptionHandling(exception -> exception
        .authenticationEntryPoint(new AjaxLoginAuthenticationEntryPoint())
        .accessDeniedHandler(ajaxAccessDeniedHandler))

      // Spring Security 버전이 올라감에 따라 필터가 아닌 Custom DSL로 하는 방법 못찾음
      // 현재는 아래 Custom Filter 방법은 안됨....
      // Custom Filter 를 만들어서 로그인 처리 하는 방법 1-2
      //.addFilterBefore(ajaxLoginProcessingFilter(http), UsernamePasswordAuthenticationFilter.class)
      .authenticationProvider(new AjaxAuthenticationProvider(passwordEncoder, userDetailsService))
      .securityContext(securityContext -> securityContext.requireExplicitSave(false))
    ;

    // Custom Filter 가 아닌 Custom DSL로 설정 추가
    httpSecurity.apply(new AjaxLoginConfigurer<>())
      .successHandlerAjax(ajaxAuthenticationSuccessHandler)
      .failureHandlerAjax(ajaxAuthenticationFailureHandler)
      .loginProcessingUrl("/api/login");

    return httpSecurity.build();
    // @formatter:on
  }

  // Custom Filter 를 만들어서 로그인 처리 하는 방법 1-1
  // 현재는 bean으로 생성만 하고 사용하지 않음 아래처럼 addfilter를 해줘야 함 (1-2 참고)
  // @Bean
  public AjaxLoginProcessingFilter ajaxLoginProcessingFilter(HttpSecurity http) {
    AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();

    ajaxLoginProcessingFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
    ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
    ajaxLoginProcessingFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);

    return ajaxLoginProcessingFilter;
  }

}
