package io.devfactory.account.repository;

import io.devfactory.account.domain.Account;
import io.devfactory.account.domain.AccountRole;
import java.util.Set;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {

  @EntityGraph(attributePaths = {"role"})
  Set<AccountRole> findByAccount(Account account);

}
