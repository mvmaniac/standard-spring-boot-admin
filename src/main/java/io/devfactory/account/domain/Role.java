package io.devfactory.account.domain;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import io.devfactory.global.common.model.BaseEntity;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  public static Role of(Long id, String name, String description) {
    return new Role(id, name, description);
  }

  public void update(Role role) {
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
