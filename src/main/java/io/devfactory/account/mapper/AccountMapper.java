package io.devfactory.account.mapper;

import io.devfactory.account.domain.Account;
import io.devfactory.account.dto.request.SignUpRequestView;
import io.devfactory.account.dto.response.LoginResponseView;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class AccountMapper {

  public static final AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

  public abstract Account toAccount(SignUpRequestView requestView);

  public abstract LoginResponseView toLoginResponseView(Account account);

}
