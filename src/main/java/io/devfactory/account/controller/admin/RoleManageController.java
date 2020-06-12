package io.devfactory.account.controller.admin;

import io.devfactory.account.domain.Role;
import io.devfactory.account.dto.response.RoleResponseView;
import io.devfactory.account.dto.response.RolesResponseView;
import io.devfactory.account.mapper.RoleMapper;
import io.devfactory.account.service.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/admin/roles")
@Controller
public class RoleManageController {

  private final RoleService roleService;

  @GetMapping()
  public String viewRoleList(Model model) {
    final List<Role> roles = roleService.findRoleAll();
    final List<RoleResponseView> views = RoleMapper.INSTANCE.toRoleResponseViewList(roles);

    model.addAttribute("result", RolesResponseView.of(views));
    return "views/admin/role/list";
  }

  @GetMapping("/form")
  public String viewRoleCreateForm() {
    return "views/admin/role/form";
  }

  @GetMapping("/form/{id}")
  public String viewRoleModifyForm(@PathVariable("id") Long roleId, Model model) {
    final Role role = roleService.findRoleById(roleId);
    final RoleResponseView roleResponseView = RoleMapper.INSTANCE.toRoleResponseView(role);

    model.addAttribute("role", roleResponseView);
    return "views/admin/role/form";
  }

}
