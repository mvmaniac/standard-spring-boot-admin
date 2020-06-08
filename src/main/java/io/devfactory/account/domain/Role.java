package io.devfactory.account.domain;

import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import io.devfactory.global.common.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Objects;

@NoArgsConstructor(access = PROTECTED)
@Getter
@SequenceGenerator(
    name = "seq_tb_role_gen",
    sequenceName = "seq_tb_role",
    allocationSize = 1
)
@Table(name = "tb_role")
@Entity
public class Role extends BaseEntity {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "seq_tb_role_gen")
  @Column(name = "role_id")
  private Long id;

  @Column(name = "role_name", length = 20, nullable = false)
  private String name;

  @Column(name = "role_description", length = 30)
  private String description;

  @Builder(builderMethodName = "create")
  private Role(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public void changeRole(Role role) {
    this.name = role.getName();
    this.description = role.getDescription();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Role role = (Role) o;
    return Objects.equals(id, role.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
