package io.devfactory.account.controller;

import io.devfactory.account.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

  private final UserService userService;

  @GetMapping("/my")
  public String viewMy() {
    return "views/user/my";
  }

  @GetMapping("/message")
  public String viewMessage() {
    return "views/user/message";
  }

  @GetMapping("/public")
  public String viewPublic() {
    return "views/user/public";
  }

}
