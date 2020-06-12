package io.devfactory.global.common.runner;

import io.devfactory.account.service.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SecurityRunner implements ApplicationRunner {

  private final RoleHierarchyService roleHierarchyService;
  private final RoleHierarchyImpl roleHierarchy;

  @Override
  public void run(ApplicationArguments args) {
    final String roleHierarchies = roleHierarchyService.findRoleHierarchyAllToString();
    roleHierarchy.setHierarchy(roleHierarchies);
  }

}
