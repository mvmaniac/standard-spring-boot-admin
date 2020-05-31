package io.devfactory.global.config.security.service;

import static java.util.stream.Collectors.toList;

import io.devfactory.account.domain.Account;
import io.devfactory.account.domain.AccountRole;
import io.devfactory.account.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    // TODO: account_id 로 role 목록 조회 쿼리 작성
    final List<GrantedAuthority> authorities = findAccount.getRoles()
        .stream()
        .map(AccountRole::getRoleName)
        .map(SimpleGrantedAuthority::new)
        .collect(toList())
      ;

    return new AccountContext(findAccount, authorities);
  }

}
