package io.devfactory.account.service;

import io.devfactory.account.domain.Account;
import io.devfactory.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void saveUser(Account account) {
    userRepository.save(account.encodePassword(passwordEncoder));
  }

}
