package io.devfactory.account.dto.request;

import static lombok.AccessLevel.PROTECTED;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class LoginRequestView {

  private String username;
  private String password;

}
