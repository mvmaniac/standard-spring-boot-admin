package io.devfactory.account.api;

import io.devfactory.account.domain.Account;
import io.devfactory.account.domain.Role;
import io.devfactory.account.dto.request.UserRequestView;
import io.devfactory.account.mapper.AccountMapper;
import io.devfactory.account.mapper.RoleMapper;
import io.devfactory.account.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
@RestController
public class UserManageRestController {

  private final UserService userService;

  @PutMapping("/{id}")
  public ResponseEntity<Boolean> modifyUser(@PathVariable("id") Long userId,
      @RequestBody UserRequestView view) {
    final Account account = AccountMapper.INSTANCE.toAccount(userId, view);
    final List<Role> roles = RoleMapper.INSTANCE.toRoles(view.getRoles());

    userService.updateUser(account, roles);
    return ResponseEntity.ok(true);
  }

}
