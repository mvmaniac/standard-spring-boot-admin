package io.devfactory.account.controller;

import static io.devfactory.global.utils.FunctionUtils.REDIRECT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import io.devfactory.account.domain.Account;
import io.devfactory.account.dto.request.SignUpRequestView;
import io.devfactory.account.mapper.AccountMapper;
import io.devfactory.account.service.UserService;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class SignController {

  private final UserService userService;

  @GetMapping("/sign-up/form")
  public String signUpView() {
    return "views/sign/signUp";
  }

  @PostMapping("/sign-up")
  public String signUp(SignUpRequestView requestView) {
    final Account account = AccountMapper.INSTANCE.toAccount(requestView);
    userService.saveUser(account);
    return REDIRECT.apply("/");
  }

  // 로그인 실패 시 핸들러에서 POST로 받기 위해...
  @SuppressWarnings("squid:S3752")
  @RequestMapping(value = "/sign-in/form", method = {GET, POST})
  public String signInView() {
    return "views/sign/signIn";
  }

  // 로그아웃을 get 방식으로 할 경우 수동으로 해줘야 함
  @GetMapping("/sign-out")
  public String signOut(HttpServletRequest request, HttpServletResponse response) {

    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (Objects.nonNull(authentication)) {
      new SecurityContextLogoutHandler().logout(request, response, authentication);
    }

    return REDIRECT.apply("/");
  }

}
