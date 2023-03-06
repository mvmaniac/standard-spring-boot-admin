package io.devfactory.global.config.security;

import io.devfactory.global.config.security.common.FormAuthenticationDetailsSource;
import io.devfactory.global.config.security.filter.PermitAllFilter;
import io.devfactory.global.config.security.provider.CustomAuthenticationProvider;
import io.devfactory.global.config.security.service.ResourceMappingConfigService;
import io.devfactory.global.config.security.voter.IpAddressVoter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Order(2)
@Configuration
public class WebSecurityConfig {

  private final UserDetailsService userDetailsService;
  private final ResourceMappingConfigService resourceMappingConfigService;

  private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;
  private final AuthenticationFailureHandler customAuthenticationFailureHandler;

  private final FilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMedataSource;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public AffirmativeBased affirmativeBased() {
    List<AccessDecisionVoter<?>> voters = new ArrayList<>();

    voters.add(new IpAddressVoter(resourceMappingConfigService));
    voters.add(new RoleHierarchyVoter(roleHierarchy()));

    return new AffirmativeBased(voters);
  }

  @Bean
  public RoleHierarchyImpl roleHierarchy() {
    return new RoleHierarchyImpl();
  }

  // web.ignoring() 으로 할 수 있으나 경고 메세지가 나옴
  // https://github.com/spring-projects/spring-security/issues/10938
  @Order(0)
  @Bean
  public SecurityFilterChain resourceChain(HttpSecurity http) throws Exception {
    // @formatter:off
    return http
      .securityMatcher(StaticResource.getResources())
      .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
      .requestCache(RequestCacheConfigurer::disable)
      .securityContext(AbstractHttpConfigurer::disable)
      .sessionManagement(AbstractHttpConfigurer::disable)
      .build();
    // @formatter:on
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // @formatter:off
    return http
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/", "/sign-up/**", "/user/public")
          .permitAll()

//        .antMatchers("/user/my")
//          .hasRole("USER")
//
//        .antMatchers("/user/message")
//          .hasRole("MANAGER")
//
//        .antMatchers("/admin/config")
//          .hasRole("ADMIN")

        .anyRequest()
          .authenticated())
      .formLogin(form -> form
        .loginPage("/sign-in/form")
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/")
        .authenticationDetailsSource(new FormAuthenticationDetailsSource())
        .successHandler(customAuthenticationSuccessHandler)
        .failureHandler(customAuthenticationFailureHandler)
        .permitAll())
      .logout(logout -> logout
        .logoutUrl("/sign-out")
        .logoutSuccessUrl("/"))

      .authenticationProvider(new CustomAuthenticationProvider(passwordEncoder(), userDetailsService))
      .securityContext(securityContext -> securityContext.requireExplicitSave(false))

      .apply(new CustomWebSecurityConfigurer())
        .setSecurityMetadataSource(urlFilterInvocationSecurityMedataSource)
        .setAccessDecisionManager(affirmativeBased())
      .and()
      .build();
    // @formatter:on
  }

  // Spring Security 버전 업에 따른 커스텀 필터가 아닌 커스텀 DSL 방식으로 변경
  // https://stackoverflow.com/questions/72427751/update-the-spring-security-configuration-class-with-spring-boot-2-7-0
  public static class CustomWebSecurityConfigurer extends AbstractHttpConfigurer<CustomWebSecurityConfigurer, HttpSecurity> {

    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;
    private AccessDecisionManager accessDecisionManager;

    @Override
    public void configure(HttpSecurity http) {
      String[] permitAllRequestMatchers = {"/", "/sign-up/**", "/user/public", "/error"};

      final PermitAllFilter permitAllFilter = new PermitAllFilter(permitAllRequestMatchers);
      permitAllFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
      permitAllFilter.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
      permitAllFilter.setAccessDecisionManager(accessDecisionManager);

      http.addFilterBefore(permitAllFilter, FilterSecurityInterceptor.class);
    }

    public CustomWebSecurityConfigurer setSecurityMetadataSource(
        FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
      this.filterInvocationSecurityMetadataSource = filterInvocationSecurityMetadataSource;
      return this;
    }

    public CustomWebSecurityConfigurer setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
      this.accessDecisionManager = accessDecisionManager;
      return this;
    }

  }

  @Getter
  public static class StaticResource {

    private StaticResource() {
      throw new IllegalStateException("Constructor not supported");
    }

    private static final String[] defaultResources = Arrays.stream(StaticResourceLocation.values())
        .flatMap(StaticResourceLocation::getPatterns)
        .toArray(String[]::new);

    public static String[] getResources() {
      return defaultResources;
    }

    public static String[] getResources(String... antPatterns) {
      final var defaultResources = StaticResource.defaultResources;
      final var resources = Arrays.copyOf(defaultResources, defaultResources.length + antPatterns.length);
      System.arraycopy(antPatterns, 0, resources, defaultResources.length, antPatterns.length);
      return resources;
    }

  }

}

