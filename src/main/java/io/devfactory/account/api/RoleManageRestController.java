package io.devfactory.account.api;

import io.devfactory.account.domain.Role;
import io.devfactory.account.dto.request.RoleRequestView;
import io.devfactory.account.dto.response.RoleResponseView;
import io.devfactory.account.mapper.RoleMapper;
import io.devfactory.account.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/admin/roles")
@RestController
public class RoleManageRestController {

  private final RoleService roleService;

  @PostMapping
  public ResponseEntity<RoleResponseView> createRole(@RequestBody RoleRequestView view) {
    final Role role = RoleMapper.INSTANCE.toRole(view);
    final Role savedRole = roleService.saveRole(role);
    return ResponseEntity.ok(RoleMapper.INSTANCE.toRoleResponseView(savedRole));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Boolean> modifyRole(@PathVariable("id") Long roleId,
      @RequestBody RoleRequestView view) {
    final Role role = RoleMapper.INSTANCE.toRole(roleId, view);
    roleService.updateRole(role);

    return ResponseEntity.ok(true);
  }

}
