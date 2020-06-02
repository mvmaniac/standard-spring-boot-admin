package io.devfactory.account.controller.admin;

import io.devfactory.account.domain.Account;
import io.devfactory.account.domain.Role;
import io.devfactory.account.dto.response.RoleResponseView;
import io.devfactory.account.dto.response.UserResponseView;
import io.devfactory.account.dto.response.UsersResponseView;
import io.devfactory.account.mapper.AccountMapper;
import io.devfactory.account.mapper.RoleMapper;
import io.devfactory.account.service.RoleService;
import io.devfactory.account.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Controller
public class UserManageController {

  private final UserService userService;
  private final RoleService roleService;

  @GetMapping
  public String viewUsers(Model model) {
    final List<Account> accounts = userService.findUsers();
    final List<UserResponseView> views = AccountMapper.INSTANCE.toUserResponseViewList(accounts);

    model.addAttribute("result", UsersResponseView.of(views));
    return "views/admin/user/list";
  }

  @GetMapping("/form/{id}")
  public String viewUserForm(@PathVariable("id") Long userId, Model model) {
    final Account account = userService.findUserById(userId);
    final UserResponseView userResponseView = AccountMapper.INSTANCE.toUserResponseView(account);

    final List<Role> roles = roleService.findRoles();
    final List<RoleResponseView> views = RoleMapper.INSTANCE.toRoleResponseViewList(roles);

    model.addAttribute("user", userResponseView);
    model.addAttribute("roles", views);
    return "views/admin/user/form";
  }

}
