package io.devfactory.account.domain;

import io.devfactory.global.common.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;
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

  public AccessIp(Long id, String ipAddress) {
    this.id = id;
    this.ipAddress = ipAddress;
  }

}
