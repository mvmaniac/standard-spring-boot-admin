package io.devfactory.account.dto.response;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class RolesResponseView {

  private int count;
  private List<RoleResponseView> roles;

  private RolesResponseView(List<RoleResponseView> views) {
    this.count = views.size();
    this.roles = views;
  }

  public static RolesResponseView of(List<RoleResponseView> views) {
    return new RolesResponseView(views);
  }

}
