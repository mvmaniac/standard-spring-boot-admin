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
import org.springframework.util.CollectionUtils;

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

  public List<Account> findUserAll() {
    final List<Account> findAccounts = userRepository.findAll(Sort.by(ASC, "id"));
    return findAccounts.stream().map(this::changeRoles).collect(toList());
  }

  @Transactional
  public void saveUser(Account account) {
    // TODO: 기본 롤 여부를 두어서 가져오기?
    // 기본 롤을 만들어서 저장, 현재 아이디 3인 것이 기본 롤...
    final AccountRole userRole = AccountRole.of(account, Role.create().id(3L).build());

    // AccountRole 를 만들때 account 넘기기 때문에 연관관계 편의 메소드를 사용하지 않음
    account.encodePassword(passwordEncoder);
    account.getRoles().add(userRole);

    userRepository.save(account);
  }

  @Transactional
  public void updateUser(Account account, List<Role> roles) {
    final Account findAccount = userRepository.findById(account.getId())
        .orElseThrow(EntityNotFoundException::new);

    findAccount.updateAccount(account);
    findAccount.getRoles().clear(); // 이렇게 삭제 하는 경우 삭제할 건수 만큼 쿼리가 날라감

    // TODO: 현재는 그냥 다 지우고 다시 등록하는 방법인데 더 좋은 방법?
    if (!CollectionUtils.isEmpty(roles)) {
      accountRoleRepository.saveAll(AccountRole.ofList(findAccount, roles));
    }
  }

  // FIXME: 더 좋은 방법...
  private Account changeRoles(Account account) {
    final Set<AccountRole> findAccountRoles = accountRoleRepository.findByAccount(account);
    account.changeRoles(findAccountRoles);
    return account;
  }

}
