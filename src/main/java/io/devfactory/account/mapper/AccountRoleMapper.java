package io.devfactory.account.mapper;

import io.devfactory.account.domain.AccountRole;
import io.devfactory.account.dto.response.RoleResponseView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class AccountRoleMapper {

  public static final AccountRoleMapper INSTANCE = Mappers.getMapper(AccountRoleMapper.class);

  @Mapping(source = "accountRole.role.id", target = "id")
  @Mapping(source = "accountRole.role.name", target = "name")
  @Mapping(source = "accountRole.role.description", target = "description")
  @Mapping(source = "accountRole.role.createdDate", target = "createdDate")
  @Mapping(source = "accountRole.role.updatedDate", target = "updatedDate")
  public abstract RoleResponseView toRoleResponseView(AccountRole accountRole);

}
