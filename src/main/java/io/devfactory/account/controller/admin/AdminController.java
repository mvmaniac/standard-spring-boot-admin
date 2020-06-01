package io.devfactory.account.controller.admin;

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

}
