package io.devfactory.controller.user;

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

}
