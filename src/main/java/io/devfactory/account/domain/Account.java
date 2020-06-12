package io.devfactory.account.domain;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import io.devfactory.account.model.Password;
import io.devfactory.global.common.model.BaseEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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

  @OneToMany(mappedBy = "account", cascade = ALL, orphanRemoval = true)
  private Set<AccountRole> roles = new HashSet<>();

  @Builder(builderMethodName = "create")
  private Account(Long id, String username, String password, String email, int age) {
    this.id = id;
    this.username = username;
    this.password = new Password(password);
    this.email = email;
    this.age = age;
  }

  public static Account of(Long id, String username, String password, String email, int age) {
    return new Account(id, username, password, email, age);
  }

  public Account encodePassword(PasswordEncoder encoder) {
    this.password.encode(encoder);
    return this;
  }

  public boolean matchPassword(PasswordEncoder encoder, String rawPassword) {
    return password.isMatches(encoder, rawPassword);
  }

  public String getPasswordValue() {
    return password.getValue();
  }

  public void updateAccount(Account changeAccount) {
    this.username = changeAccount.getUsername();
    this.email = changeAccount.getEmail();
    this.age = changeAccount.getAge();

    if (!StringUtils.isEmpty(changeAccount.getPasswordValue())) {
      this.password = changeAccount.getPassword();
    }
  }

  public void changeRoles(Set<AccountRole> roles) {
    this.roles = CollectionUtils.isEmpty(roles) ? new HashSet<>() : roles;
  }

}
