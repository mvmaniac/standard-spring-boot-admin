package io.devfactory.account.controller;

import io.devfactory.account.domain.Account;
import io.devfactory.account.dto.request.AccountRequestView;
import io.devfactory.account.mapper.AccountMapper;
import io.devfactory.account.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static io.devfactory.global.utils.FunctionUtils.REDIRECT;

@RequiredArgsConstructor
@Controller
public class SignController {

  private final UserService userService;

  @GetMapping("/sign-up/form")
  public String signUpView() {
    return "views/sign/signUp";
  }

  @PostMapping("/sign-up")
  public String signUp(AccountRequestView requestView) {
    final Account account = AccountMapper.INSTANCE.toAccount(requestView);
    userService.saveUser(account);
    return REDIRECT.apply("/");
  }

}
