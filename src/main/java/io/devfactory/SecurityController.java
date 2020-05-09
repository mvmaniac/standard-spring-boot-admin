package io.devfactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/loginView")
  public String loginView() {
    return "loginView";
  }

  @GetMapping("/user")
  public String userView() {
    return "userView";
  }

  @GetMapping("/admin")
  public String adminView() {
    return "adminView";
  }

  @GetMapping("/denied")
  public String deniedView() {
    return "deniedView";
  }

}
