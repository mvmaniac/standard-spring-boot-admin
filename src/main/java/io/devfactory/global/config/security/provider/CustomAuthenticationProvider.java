package io.devfactory.global.config.security.provider;

import io.devfactory.account.domain.Account;
import io.devfactory.global.config.security.service.AccountContext;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;

  @Override
  public Authentication authenticate(Authentication authentication) {
    final String email = authentication.getName();
    final String password = String.valueOf(authentication.getCredentials());

    final AccountContext accountContext = (AccountContext) userDetailsService
        .loadUserByUsername(email);

    final Account account = accountContext.getAccount();
    final Collection<GrantedAuthority> authorities = accountContext.getAuthorities();

    if (!account.isMatches(passwordEncoder, password)) {
      throw new BadCredentialsException("BadCredentialsException...");
    }

    return new UsernamePasswordAuthenticationToken(account, null, authorities);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }

}
