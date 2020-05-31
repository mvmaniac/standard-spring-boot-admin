package io.devfactory.global.config.security;

import io.devfactory.global.config.security.filter.AjaxLoginProcessingFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import java.util.Objects;

public class AjaxSecurityConfigurer<H extends HttpSecurityBuilder<H>> extends
    AbstractAuthenticationFilterConfigurer<H, AjaxSecurityConfigurer<H>, AjaxLoginProcessingFilter> {

  private AuthenticationSuccessHandler successHandler;
  private AuthenticationFailureHandler failureHandler;
  private AuthenticationManager authenticationManager;

  public AjaxSecurityConfigurer() {
    super(new AjaxLoginProcessingFilter(), null);
  }

  @Override
  protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
    return new AntPathRequestMatcher(loginProcessingUrl, "POST");
  }

  @Override
  public void configure(H http) {
    if (Objects.isNull(authenticationManager)) {
      authenticationManager = http.getSharedObject(AuthenticationManager.class);
    }

    getAuthenticationFilter().setAuthenticationManager(authenticationManager);
    getAuthenticationFilter().setAuthenticationSuccessHandler(successHandler);
    getAuthenticationFilter().setAuthenticationFailureHandler(failureHandler);

    SessionAuthenticationStrategy sessionAuthenticationStrategy = http
        .getSharedObject(SessionAuthenticationStrategy.class);

    if (Objects.nonNull(sessionAuthenticationStrategy)) {
      getAuthenticationFilter().setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
    }

    RememberMeServices rememberMeServices = http
        .getSharedObject(RememberMeServices.class);

    if (Objects.nonNull(rememberMeServices)) {
      getAuthenticationFilter().setRememberMeServices(rememberMeServices);
    }

    http.setSharedObject(AjaxLoginProcessingFilter.class, getAuthenticationFilter());
    http.addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  public AjaxSecurityConfigurer<H> loginPage(String loginPage) {
    return super.loginPage(loginPage);
  }

  public AjaxSecurityConfigurer<H> successHandlerAjax(AuthenticationSuccessHandler successHandler) {
    this.successHandler = successHandler;
    return this;
  }

  public AjaxSecurityConfigurer<H> failureHandlerAjax(
      AuthenticationFailureHandler authenticationFailureHandler) {
    this.failureHandler = authenticationFailureHandler;
    return this;
  }

  public AjaxSecurityConfigurer<H> setAuthenticationManager(
      AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    return this;
  }

}
