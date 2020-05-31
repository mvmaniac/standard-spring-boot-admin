package io.devfactory.account.domain;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import io.devfactory.global.common.model.BaseEntity;
import java.util.HashSet;
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
import org.springframework.util.CollectionUtils;

@NoArgsConstructor(access = PROTECTED)
@Getter
@SequenceGenerator(
    name = "seq_tb_resources_gen",
    sequenceName = "seq_tb_resources",
    allocationSize = 1
)
@Table(name = "tb_resources")
@Entity
public class Resources extends BaseEntity {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "seq_tb_resources_gen")
  @Column(name = "resources_id")
  private Long id;

  @Column(name = "resources_name", length = 20, nullable = false)
  private String name;

  @Column(length = 10)
  private String httpMethod;

  @Column(name = "resources_type", length = 20)
  private String type;

  @Column
  private int orderNo;

  @OneToMany(mappedBy = "resources", cascade = ALL)
  private Set<ResourcesRole> roles = new HashSet<>();

  @Builder(builderMethodName = "of")
  private Resources(String name, String httpMethod, String type, int orderNo,
      Set<ResourcesRole> roles) {
    this.name = name;
    this.httpMethod = httpMethod;
    this.type = type;
    this.orderNo = orderNo;
    this.roles = CollectionUtils.isEmpty(roles) ? new HashSet<>() : roles;
  }

  public void addRole(ResourcesRole role) {
    this.roles.add(role);
    role.changeResources(this);
  }

}
