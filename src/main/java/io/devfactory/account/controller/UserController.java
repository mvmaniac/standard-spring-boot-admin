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

  @GetMapping("mypage")
  public String myPage() {
    return "views/user/mypage";
  }

  @GetMapping("/message")
  public String message() {
    return "views/user/message";
  }

}
