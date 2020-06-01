package io.devfactory.account.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/roles")
@Controller
public class RoleManageController {

  @GetMapping()
  public String viewRoles() {
    return "views/admin/role/list";
  }

  @GetMapping("/form")
  public String viewRoleForm() {
    return "views/admin/role/form";
  }

}
