package io.devfactory.account.service;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.Sort.Direction.ASC;

import io.devfactory.account.domain.Account;
import io.devfactory.account.domain.AccountRole;
import io.devfactory.account.domain.Role;
import io.devfactory.account.repository.AccountRoleRepository;
import io.devfactory.account.repository.UserRepository;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

  private final UserRepository userRepository;
  private final AccountRoleRepository accountRoleRepository;

  private final PasswordEncoder passwordEncoder;

  public Account findUserById(Long userId) {
    final Account findAccount = userRepository.findById(userId)
        .orElseThrow(EntityNotFoundException::new);
    return changeRoles(findAccount);
  }

  public List<Account> findUsers() {
    final List<Account> findAccounts = userRepository.findAll(Sort.by(ASC, "id"));
    return findAccounts.stream().map(this::changeRoles).collect(toList());
  }

  @Transactional
  public void saveUser(Account account) {
    userRepository.save(account.encodePassword(passwordEncoder));
  }

  @Transactional
  public void modifyUser(Account account, List<Role> roles) {
    final Account findAccount = findUserById(account.getId());
    findAccount.changeAccount(account);

    userRepository.save(findAccount);

    // TODO: 다 지우고 다시 등록 하는 거 말고 더 좋은 방법?
    accountRoleRepository.deleteByAccount(findAccount);
    accountRoleRepository.saveAll(AccountRole.ofList(findAccount, roles));
  }

  // FIXME: 더 좋은 방법...
  private Account changeRoles(Account account) {
    final Set<AccountRole> findAccountRoles = accountRoleRepository.findByAccount(account);
    account.changeRoles(findAccountRoles);
    return account;
  }

}
