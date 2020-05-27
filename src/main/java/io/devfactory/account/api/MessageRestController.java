package io.devfactory.account.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageRestController {

  @GetMapping("/api/messages")
  public String message() {
    return "message ok...";
  }

}
