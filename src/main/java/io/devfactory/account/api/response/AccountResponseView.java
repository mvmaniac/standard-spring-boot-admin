package io.devfactory.account.api.response;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class AccountResponseView {

  private Long id;
  private String username;
  private String email;
  private int age;
  private String role;

}
