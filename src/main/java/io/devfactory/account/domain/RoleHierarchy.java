package io.devfactory.account.domain;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import io.devfactory.global.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Getter
@SequenceGenerator(
    name = "seq_tb_role_hierarchy_gen",
    sequenceName = "seq_tb_role_hierarchy",
    allocationSize = 1
)
@Table(name = "tb_role_hierarchy")
@Entity
public class RoleHierarchy extends BaseEntity {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "seq_tb_role_hierarchy_gen")
  @Column(name = "role_hierarchy_id")
  private Long id;

  @OneToOne(fetch = LAZY, cascade = ALL)
  @JoinColumn(name = "child_id", referencedColumnName = "role_id")
  private Role childRole;

  @OneToOne(fetch = LAZY, cascade = ALL)
  @JoinColumn(name = "parent_id", referencedColumnName = "role_id")
  private Role parentRole;

  public RoleHierarchy(Role childRole, Role parentRole) {
    this.childRole = childRole;
    this.parentRole = parentRole;
  }

}
