package io.devfactory.global.config.security.service;

import io.devfactory.account.domain.Account;
import io.devfactory.account.domain.AccountRole;
import io.devfactory.account.repository.AccountRoleRepository;
import io.devfactory.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  private final AccountRoleRepository accountRoleRepository;

  @Override
  public UserDetails loadUserByUsername(String email) {
    final Account findAccount = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("UsernameNotFoundException..."));

    final Set<AccountRole> findRoles = accountRoleRepository.findByAccount(findAccount);
    findAccount.changeRoles(findRoles);

    final List<SimpleGrantedAuthority> authorities = findRoles.stream()
        .map(accountRole -> accountRole.getRole().getName())
        .map(SimpleGrantedAuthority::new)
        .toList();

    return new AccountContext(findAccount, authorities);
  }

}
