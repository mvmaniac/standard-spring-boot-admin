package io.devfactory.account.domain;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import io.devfactory.account.model.Password;
import io.devfactory.global.common.model.BaseEntity;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
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

  @OneToMany(mappedBy = "role", cascade = ALL)
  private Set<AccountRole> accounts = new HashSet<>();

  @OneToMany(mappedBy = "role", cascade = ALL)
  private Set<ResourcesRole> resources = new HashSet<>();

  private Role(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public static Role of(String name, String description) {
    return new Role(name, description);
  }

}
