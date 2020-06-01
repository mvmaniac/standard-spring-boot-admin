package io.devfactory.account.controller.admin;

import io.devfactory.account.domain.Account;
import io.devfactory.account.dto.response.UserResponseView;
import io.devfactory.account.dto.response.UsersResponseView;
import io.devfactory.account.mapper.AccountMapper;
import io.devfactory.account.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Controller
public class UserManageController {

  private final UserService userService;

  @GetMapping
  public String viewUsers(Model model) {
    final List<Account> accounts = userService.findUsers();

//    final UsersResponseView responseView = AccountMapper.INSTANCE
//        .toUsersResponseView(accounts);

    final List<UserResponseView> users = AccountMapper.INSTANCE
        .toUsersResponseView(accounts);

    model.addAttribute("users", users);

    return "views/admin/user/list";
  }

  @GetMapping("/form")
  public String viewUserForm() {
    return "views/admin/user/form";
  }

}
