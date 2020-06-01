package io.devfactory.account.service;

import io.devfactory.account.domain.Account;
import io.devfactory.account.domain.AccountRole;
import io.devfactory.account.repository.AccountRoleRepository;
import io.devfactory.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

  private final UserRepository userRepository;
  private final AccountRoleRepository accountRoleRepository;

  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void saveUser(Account account) {
    userRepository.save(account.encodePassword(passwordEncoder));
  }

  public List<Account> findUsers() {
    final List<Account> findAccounts = userRepository.findAll();
    return findAccounts.stream().map(this::changeRoles).collect(toList());
  }

  // FIXME: 더 좋은 방법...
  private Account changeRoles(Account account) {
    final Set<AccountRole> findAccountRoles = accountRoleRepository.findByAccount(account);
    return account.changeRoles(findAccountRoles);
  }

}
