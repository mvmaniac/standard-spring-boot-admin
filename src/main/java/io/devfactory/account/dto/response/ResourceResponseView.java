package io.devfactory.account.dto.response;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class ResourceResponseView {

  private Long id;
  private String name;
  private String httpMethod;
  private String type;
  private int orderNo;
  private Long roleId;
  private String roleName;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  @Builder(builderMethodName = "create")
  public ResourceResponseView(Long id, String name, String httpMethod, String type, int orderNo,
      Long roleId, String roleName, LocalDateTime createdDate, LocalDateTime updatedDate) {
    this.id = id;
    this.name = name;
    this.httpMethod = httpMethod;
    this.type = type;
    this.orderNo = orderNo;
    this.roleId = roleId;
    this.roleName = roleName;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }

}
