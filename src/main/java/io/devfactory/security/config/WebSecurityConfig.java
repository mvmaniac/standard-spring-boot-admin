package io.devfactory.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
        .roles("MANAGER")
      .and()

      .withUser("admin")
        .password(password)
        .roles("ADMIN")
    ;
    // @formatter:on
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .authorizeRequests()

        .antMatchers("/", "/admin/home")
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

