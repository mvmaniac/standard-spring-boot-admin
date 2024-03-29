package io.devfactory.account.domain;

import io.devfactory.global.common.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@SequenceGenerator(
    name = "seq_tb_access_ip_gen",
    sequenceName = "seq_tb_access_ip",
    allocationSize = 1
)
@Table(name = "tb_access_ip")
@Entity
public class AccessIp extends BaseEntity {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "seq_tb_access_ip_gen")
  @Column(name = "access_ip_id")
  private Long id;

  @Column(nullable = false)
  private String ipAddress;

  @Builder(builderMethodName = "create")
  private AccessIp(Long id, String ipAddress) {
    this.id = id;
    this.ipAddress = ipAddress;
  }

  public static AccessIp of(Long id, String ipAddress) {
    return new AccessIp(id, ipAddress);
  }

}
