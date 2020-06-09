package io.devfactory.account.controller;

import io.devfactory.account.dto.request.UserRequestView;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class AopController {

  @PreAuthorize("hasRole('ROLE_USER') and #userRequestView.username == principal.getName()")
  @GetMapping("/preAuthorize")
  public String preAuthorize(UserRequestView userRequestView, Model model, Principal principal) {
    model.addAttribute("method", "Success @PreAuthorize");
    return "views/aop/method";
  }

}
