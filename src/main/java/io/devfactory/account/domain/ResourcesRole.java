package io.devfactory.account.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import io.devfactory.global.common.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Getter
@SequenceGenerator(
    name = "seq_tb_resources_role_gen",
    sequenceName = "seq_tb_resources_role",
    allocationSize = 1
)
@Table(name = "tb_resources_role")
@Entity
public class ResourcesRole extends BaseEntity {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "seq_tb_resources_role_gen")
  @Column(name = "resources_role_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "resources_id")
  private Resources resources;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "role_id")
  private Role role;

  private ResourcesRole(Resources resources, Role role) {
    this.resources = resources;
    this.role = role;
  }

  public static ResourcesRole of(Resources resources, Role role) {
    return new ResourcesRole(resources , role);
  }

  public void changeResources(Resources resources) {
    this.resources = resources;
  }

}
