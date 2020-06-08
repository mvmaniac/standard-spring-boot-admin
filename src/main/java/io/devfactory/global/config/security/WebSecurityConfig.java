package io.devfactory.global.config.security;

import io.devfactory.global.config.security.common.FormAuthenticationDetailsSource;
import io.devfactory.global.config.security.filter.PermitAllFilter;
import io.devfactory.global.config.security.provider.CustomAuthenticationProvider;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
@Order(1)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;

  private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;
  private final AuthenticationFailureHandler customAuthenticationFailureHandler;

  private final FilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMedataSource;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider customAuthenticationProvider() {
    return new CustomAuthenticationProvider(passwordEncoder(), userDetailsService);
  }

  @Bean
  public FilterSecurityInterceptor customFilterSecurityInterceptor() throws Exception {
    String[] permitAllRequestMatchers = {"/", "/sign-up/**", "/user/public"};

    final PermitAllFilter permitAllFilter = new PermitAllFilter(permitAllRequestMatchers);
    permitAllFilter.setSecurityMetadataSource(urlFilterInvocationSecurityMedataSource);
    permitAllFilter.setAuthenticationManager(authenticationManagerBean());
    permitAllFilter.setAccessDecisionManager(affirmativeBased());

    return permitAllFilter;
  }

  // customFilterSecurityInterceptor 를 filterRegistrationBean 으로 등록해서 
  // enabled 를 false 로 해서 securityFilterChain 에서만 등록해서 사용되도록 함
  // 만약 이걸 하지 않으면 web.ignoring 으로 등록된 리소스들이 무시되어 customFilterSecurityInterceptor를 탐
  @Bean
  public FilterRegistrationBean<FilterSecurityInterceptor> filterRegistrationBean()
      throws Exception {
    final FilterRegistrationBean<FilterSecurityInterceptor> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(customFilterSecurityInterceptor());
    filterRegistrationBean.setEnabled(false);
    return filterRegistrationBean;
  }

  @Bean
  public AffirmativeBased affirmativeBased() {
    List<AccessDecisionVoter<? extends Object>> voters = new ArrayList<>();
    voters.add(new RoleHierarchyVoter(roleHierarchy()));

    return new AffirmativeBased(voters);
  }

  @Bean
  public RoleHierarchyImpl roleHierarchy() {
    return new RoleHierarchyImpl();
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    // @formatter:off
    auth
      .authenticationProvider(customAuthenticationProvider())
    ;
    // @formatter:on
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .authorizeRequests()

//        .antMatchers("/", "/sign-up/**", "/user/public")
//          .permitAll()
//
//        .antMatchers("/user/my")
//          .hasRole("USER")
//
//        .antMatchers("/user/message")
//          .hasRole("MANAGER")
//
//        .antMatchers("/admin/config")
//          .hasRole("ADMIN")

        .anyRequest()
          .authenticated()
      .and()

      .formLogin()
        .loginPage("/sign-in/form")
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/")
        .authenticationDetailsSource(new FormAuthenticationDetailsSource())
        .successHandler(customAuthenticationSuccessHandler)
        .failureHandler(customAuthenticationFailureHandler)
        .permitAll()
      .and()

      .logout()
        .logoutUrl("/sign-out")
        .logoutSuccessUrl("/")
      .and()

      // customFilterSecurityInterceptor 로 인해 FilterSecurityInterceptor 은 무시됨 (실행은 되나 이미 필터가 적용된 것은 무시됨)
      .addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class)
    ;
    // @formatter:on
  }

}

