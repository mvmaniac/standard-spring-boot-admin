package io.devfactory.account.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class LoginResponseView {

  private Long id;
  private String username;
  private String email;
  private int age;

  @Builder(builderMethodName = "create")
  private LoginResponseView(Long id, String username, String email, int age) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.age = age;
  }

}
