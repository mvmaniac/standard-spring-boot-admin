package io.devfactory.account.mapper;

import io.devfactory.account.dto.request.AccountRequestView;
import io.devfactory.account.dto.response.AccountResponseView;
import io.devfactory.account.domain.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class AccountMapper {

  public static final AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

  public abstract Account toAccount(AccountRequestView requestView);
  public abstract AccountResponseView toAccountResponseView(Account account);

}
