package io.devfactory.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {

  @GetMapping("/config")
  public String config() {
    return "views/admin/config";
  }

  @GetMapping("/users")
  public String userView() {
    return "views/admin/user/list";
  }

  @GetMapping("/users/form")
  public String userFormView() {
    return "views/admin/user/form";
  }

  @GetMapping("/roles")
  public String roleView() {
    return "views/admin/role/list";
  }

  @GetMapping("/roles/form")
  public String roleFormView() {
    return "views/admin/role/form";
  }

  @GetMapping("/resources")
  public String resourceView() {
    return "views/admin/resource/list";
  }

  @GetMapping("/resources/form")
  public String resourceFormView() {
    return "views/admin/resource/form";
  }

}
