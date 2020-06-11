package io.devfactory.account.controller;

import io.devfactory.account.dto.request.UserRequestView;
import io.devfactory.account.service.AopService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class AopController {

  private final AopService aopService;

  @PreAuthorize("hasRole('ROLE_USER') and #userRequestView.username == principal.username")
  @GetMapping("/annotation")
  public String annotation(UserRequestView userRequestView, Model model, Principal principal) {
    model.addAttribute("annotation", "Success @PreAuthorize");
    return "views/aop/annotation";
  }

  @GetMapping("/method")
  public String method(Model model) {
    aopService.methodSecured();
    model.addAttribute("method", "Success MethodSecured");
    return "views/aop/method";
  }

  @GetMapping("/pointcut")
  public String pointcut(Model model) {
    aopService.notSecured();
    aopService.pointcutSecured();
    model.addAttribute("pointcut", "Success PointcutSecured");
    return "views/aop/pointcut";
  }

}
