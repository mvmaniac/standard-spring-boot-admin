package io.devfactory.account.mapper;

import io.devfactory.account.domain.Account;
import io.devfactory.account.dto.request.SignUpRequestView;
import io.devfactory.account.dto.response.LoginResponseView;
import io.devfactory.account.dto.response.UserResponseView;
import io.devfactory.account.dto.response.UsersResponseView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public abstract class AccountMapper {

  public static final AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

  public abstract Account toAccount(SignUpRequestView requestView);

  public abstract LoginResponseView toLoginResponseView(Account account);

  @Mapping(target = "roleName", source = "account")
  public abstract UserResponseView toUserResponseView(Account account);

  public abstract List<UserResponseView> toUsersResponseView(List<Account> accounts);

}
