package io.devfactory.global.common.dialect;

import static java.util.stream.Collectors.joining;

import io.devfactory.account.dto.response.RoleResponseView;
import java.util.List;

public class CustomDialectUtil {

  public String combineRoleNames(List<RoleResponseView> views) {
    return views.stream()
        .map(RoleResponseView::getName)
        .collect(joining(", "))
      ;
  }

  public boolean checkMatchRole(Long roleId, List<RoleResponseView> views) {
    return views.stream().anyMatch(role -> roleId.equals(role.getId()));
  }

}
