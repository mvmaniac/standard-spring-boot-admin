package io.devfactory.account.mapper;

import io.devfactory.account.domain.Account;
import io.devfactory.account.dto.request.SignUpRequestView;
import io.devfactory.account.dto.request.UserRequestView;
import io.devfactory.account.dto.response.LoginResponseView;
import io.devfactory.account.dto.response.UserResponseView;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = AccountRoleMapper.class)
public abstract class AccountMapper {

  public static final AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

  public abstract Account toAccount(SignUpRequestView requestView);

  public abstract Account toAccount(Long id, UserRequestView requestView);

  public abstract LoginResponseView toLoginResponseView(Account account);

  public abstract UserResponseView toUserResponseView(Account account);

  public abstract List<UserResponseView> toUserResponseViewList(List<Account> accounts);

}
