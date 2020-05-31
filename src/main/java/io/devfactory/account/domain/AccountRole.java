package io.devfactory.account.domain;

import io.devfactory.global.common.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;
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

  private AccountRole(Account account, Role role) {
    this.account = account;
    this.role = role;
  }

  public static AccountRole of(Account account, Role role) {
    return new AccountRole(account , role);
  }

  public String getRoleName() {
    return this.role.getName();
  }

  public void changeAccount(Account account) {
    this.account = account;
  }

}
