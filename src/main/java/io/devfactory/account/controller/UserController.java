package io.devfactory.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {

  @GetMapping("mypage")
  public String myPage() {
    return "views/user/mypage";
  }

  @GetMapping("/message")
  public String message() {
    return "views/user/message";
  }

}
