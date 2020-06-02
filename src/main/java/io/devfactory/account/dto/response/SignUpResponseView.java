package io.devfactory.account.dto.response;

import static lombok.AccessLevel.PRIVATE;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class SignUpResponseView {

  private Long id;
  private String username;
  private String email;
  private int age;

  @Builder(builderMethodName = "create")
  private SignUpResponseView(Long id, String username, String email, int age) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.age = age;
  }

}
