package io.devfactory.account.dto.request;

import static lombok.AccessLevel.PROTECTED;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class SignUpRequestView {

  private String email;
  private String username;
  private String password;
  private int age;
  private String role;

}
