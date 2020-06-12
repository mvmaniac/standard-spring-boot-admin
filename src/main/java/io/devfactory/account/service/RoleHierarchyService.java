package io.devfactory.account.service;

import static java.util.stream.Collectors.toMap;

import io.devfactory.account.domain.Role;
import io.devfactory.account.domain.RoleHierarchy;
import io.devfactory.account.repository.RoleHierarchyRepository;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoleHierarchyService {

  private final RoleHierarchyRepository roleHierarchyRepository;

  public String findRoleHierarchyAllToString() {
    final List<RoleHierarchy> roleHierarchies = roleHierarchyRepository.findRoleHierarchies();

    final RoleHierarchy root = roleHierarchies.get(0);

    // TODO: Exception 정의 필요
    // order by null first 로 루트가 무조건 최상단에 있어야 되므로
    // 부모롤이 null이 아니라면 문제가 있다...
    if (Objects.nonNull(root.getParentRole())) {
      throw new IllegalArgumentException("invalid root role...");
    }

    final Map<Role, Role> roleMap = roleHierarchies.stream()
        .filter(roleHierarchy -> Objects.nonNull(roleHierarchy.getParentRole()))
        .collect(toMap(RoleHierarchy::getParentRole, RoleHierarchy::getChildRole));

    return roleHierarchiesToString(root.getChildRole(), roleMap);
  }

  private String roleHierarchiesToString(Role rootRole, Map<Role, Role> roleMap) {
    final StringBuilder builder = new StringBuilder();
    Role parentRole = rootRole;

    while (Objects.nonNull(roleMap.get(parentRole))) {
      builder.append(parentRole.getName());
      builder.append(" > ");

      Role childRole = roleMap.get(parentRole);

      builder.append(childRole.getName());
      builder.append("\n");

      parentRole = childRole;
    }

    return builder.toString();
  }

}
