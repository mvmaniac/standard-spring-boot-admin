package io.devfactory.account.mapper;

import io.devfactory.account.domain.Account;
import io.devfactory.account.dto.request.SignUpRequestView;
import io.devfactory.account.dto.request.UserRequestView;
import io.devfactory.account.dto.response.LoginResponseView;
import io.devfactory.account.dto.response.UserResponseView;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class AccountMapper {

  public static final AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

  public abstract Account toAccount(SignUpRequestView requestView);

  @Mapping(target = "roles", ignore = true)
  public abstract Account toAccount(Long id, UserRequestView requestView);

  public abstract LoginResponseView toLoginResponseView(Account account);

  @Mapping(target = "roleName", expression = "java(account.getRoleNames())")
  public abstract UserResponseView toUserResponseView(Account account);
  public abstract List<UserResponseView> toUserResponseViewList(List<Account> accounts);

}
