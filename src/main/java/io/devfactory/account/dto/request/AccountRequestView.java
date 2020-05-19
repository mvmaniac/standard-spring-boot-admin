package io.devfactory.account.dto.request;

import static lombok.AccessLevel.PROTECTED;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class AccountRequestView {

  private String username;
  private String email;
  private String password;
  private int age;
  private String role;

}
