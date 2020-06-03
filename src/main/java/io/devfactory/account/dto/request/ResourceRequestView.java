package io.devfactory.account.dto.request;

import static lombok.AccessLevel.PROTECTED;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class ResourceRequestView {

  private String name;
  private String httpMethod;
  private String type;
  private int orderNo;
  private Long roleId;

}
