package io.devfactory.account.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Embeddable
public class Password {

  @Column(name = "password", length = 20, nullable = false)
  private String value;

  public Password(String value) {
    this.value = value;
  }

  public void encode(PasswordEncoder encoder) {
    value = encoder.encode(value);
  }

  public boolean isMatches(PasswordEncoder encoder, String rawPassword) {
    return encoder.matches(rawPassword, value);
  }

}
