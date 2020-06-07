package io.devfactory.account.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import io.devfactory.global.common.model.BaseEntity;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Getter
@SequenceGenerator(
    name = "seq_tb_resource_gen",
    sequenceName = "seq_tb_resource",
    allocationSize = 1
)
@Table(name = "tb_resource")
@Entity
public class Resource extends BaseEntity {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "seq_tb_resource_gen")
  @Column(name = "resource_id")
  private Long id;

  @Column(name = "resource_name", length = 20, nullable = false)
  private String name;

  @Column(length = 10)
  private String httpMethod;

  @Column(name = "resource_type", length = 20)
  private String type;

  @Column
  private int orderNo;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "role_id")
  private Role role;

  @Builder(builderMethodName = "create")
  private Resource(Long id, String name, String httpMethod, String type, int orderNo, Role role) {
    this.id = id;
    this.name = name;
    this.httpMethod = httpMethod;
    this.type = type;
    this.orderNo = orderNo;
    this.role = (isEmptyRole(role)) ? null : role;
  }

  public void changeResource(Resource resource) {
    this.name = resource.getName();
    this.httpMethod = resource.getHttpMethod();
    this.type = resource.getType();
    this.orderNo = resource.getOrderNo();
    this.role = (isEmptyRole(resource.getRole())) ? null : resource.getRole();
  }

  private boolean isEmptyRole(Role role) {
    return Objects.isNull(role) || Objects.isNull(role.getId());
  }

}
