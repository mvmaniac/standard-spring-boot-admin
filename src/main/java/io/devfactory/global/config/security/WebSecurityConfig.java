package io.devfactory.global.config.security;

import io.devfactory.global.config.security.provider.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    return new CustomAuthenticationProvider(passwordEncoder(), userDetailsService);
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    // @formatter:off
    auth
      .authenticationProvider(authenticationProvider())
    ;
    // @formatter:on
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .authorizeRequests()

        .antMatchers("/", "/sign-up/**", "/user")
          .permitAll()

        .antMatchers("/user/mypage")
          .hasRole("USER")

        .antMatchers("/user/message")
          .hasRole("MANAGER")

        .antMatchers("/admin/config")
          .hasRole("ADMIN")

        .anyRequest()
          .authenticated()
      .and()

      .formLogin()
        .loginPage("/sign-in/form")
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/")
        .permitAll()
      .and()

      .logout()
        .logoutUrl("/sign-out")
        .logoutSuccessUrl("/")
      .and()
    ;
    // @formatter:on
  }

}

