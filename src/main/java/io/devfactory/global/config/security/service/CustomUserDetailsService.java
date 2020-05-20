package io.devfactory.global.config.security.service;

import io.devfactory.account.domain.Account;
import io.devfactory.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) {
    final Account findAccount = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("UsernameNotFoundException..."));

    return new AccountContext(findAccount,
        AuthorityUtils.createAuthorityList(findAccount.getRole()));
  }

}
