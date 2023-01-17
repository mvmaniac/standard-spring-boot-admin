package io.devfactory.account.domain;

import io.devfactory.global.common.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@SequenceGenerator(
    name = "seq_tb_account_role_gen",
    sequenceName = "seq_tb_account_role",
    allocationSize = 1
)
@Table(name = "tb_account_role")
@Entity
public class AccountRole extends BaseEntity {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "seq_tb_account_role_gen")
  @Column(name = "account_role_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "role_id")
  private Role role;

  @Builder(builderMethodName = "create")
  private AccountRole(Account account, Role role) {
    this.account = account;
    this.role = role;
  }

  public static AccountRole of(Account account, Role role) {
    return new AccountRole(account, role);
  }

  public static List<AccountRole> ofList(Account account, List<Role> roles) {
    return roles.stream().map(role -> new AccountRole(account, role)).collect(toList());
  }

  public void changeAccount(Account account) {
    this.account = account;
  }

}
