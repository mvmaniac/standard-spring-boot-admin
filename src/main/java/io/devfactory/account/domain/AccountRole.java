package io.devfactory.account.domain;

import static java.util.stream.Collectors.toList;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import io.devfactory.global.common.model.BaseEntity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  public AccountRole(Account account, Role role) {
    this.account = account;
    this.role = role;
  }

  public static List<AccountRole> ofList(Account account, List<Role> roles) {
    return roles.stream().map(role -> new AccountRole(account, role)).collect(toList());
  }

  public void changeAccount(Account account) {
    this.account = account;
  }

}
