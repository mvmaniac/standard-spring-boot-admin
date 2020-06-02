package io.devfactory.account.dto.response;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class RoleResponseView {

  private Long id;
  private String name;
  private String description;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  @Builder(builderMethodName = "create")
  public RoleResponseView(Long id, String name, String description, LocalDateTime createdDate,
      LocalDateTime updatedDate) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }

}
