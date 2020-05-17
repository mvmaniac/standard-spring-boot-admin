package io.devfactory.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller()
public class MessageController {

  @GetMapping("/message")
  public String message() {
    return "views/user/message";
  }

}
