package io.devfactory.global.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    final String password = passwordEncoder().encode("1234");

    // @formatter:off
    auth.inMemoryAuthentication()

      .withUser("user")
        .password(password)
        .roles("USER")
      .and()

      .withUser("manager")
        .password(password)
        .roles("MANAGER", "USER")
      .and()

      .withUser("admin")
        .password(password)
        .roles("ADMIN", "USER", "MANAGER")
    ;
    // @formatter:on
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .authorizeRequests()

        .antMatchers("/", "/user")
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
    ;
    // @formatter:on
  }

}

