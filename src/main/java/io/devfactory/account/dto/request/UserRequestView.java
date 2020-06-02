package io.devfactory.account.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class UserRequestView {

  private String email;
  private String username;
  private String password;
  private int age;
  private Long[] roles;

}
