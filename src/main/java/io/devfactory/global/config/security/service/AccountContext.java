package io.devfactory.global.config.security.service;

import io.devfactory.account.domain.Account;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class AccountContext extends User {

  private final Account account;

  public AccountContext(Account account, Collection<? extends GrantedAuthority> authorities) {
    super(account.getEmail(), account.getPassword().getValue(), authorities);
    this.account = account;
  }

}
