package io.devfactory.account.domain;

import io.devfactory.account.model.Password;
import io.devfactory.global.common.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@SequenceGenerator(
    name = "seq_tb_account_gen",
    sequenceName = "seq_tb_account",
    allocationSize = 1
)
@Table(name = "tb_account")
@Entity
public class Account extends BaseEntity {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "seq_tb_account_gen")
  @Column(name = "account_id")
  private Long id;

  @Column(name = "account_username", length = 10, nullable = false)
  private String username;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "account_password"))
  private Password password;

  @Column(name = "account_email", length = 30)
  private String email;

  @Column(name = "account_age")
  private int age;

  @Column(name = "account_role", length = 10)
  private String role;

  @Builder(builderMethodName = "of")
  private Account(String username, String password, String email, int age, String role) {
    this.username = username;
    this.password = new Password(password);
    this.email = email;
    this.age = age;
    this.role = Objects.isNull(role) ? "ROLE_USER" : role;
  }

  public Account encodePassword(PasswordEncoder encoder) {
    this.password.encode(encoder);
    return this;
  }

  public boolean isMatches(PasswordEncoder encoder, String rawPassword) {
    return password.isMatches(encoder, rawPassword);
  }

}
