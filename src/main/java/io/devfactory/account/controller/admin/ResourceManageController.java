package io.devfactory.account.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/resources")
@Controller
public class ResourceManageController {

  @GetMapping()
  public String viewResources() {
    return "views/admin/resource/list";
  }

  @GetMapping("/form")
  public String viewResourceForm() {
    return "views/admin/resource/form";
  }

}
