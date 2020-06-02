package io.devfactory.account.mapper;

import io.devfactory.account.domain.Role;
import io.devfactory.account.dto.response.RoleResponseView;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class RoleMapper {

  public static final RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

  public abstract Role toRole(Long id);
  public abstract List<Role> toRoles(Long[] id);

  public abstract RoleResponseView toRoleResponseView(Role role);
  public abstract List<RoleResponseView> toRoleResponseViewList(List<Role> roles);

}
