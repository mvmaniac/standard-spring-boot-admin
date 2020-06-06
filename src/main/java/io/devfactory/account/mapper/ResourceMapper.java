package io.devfactory.account.mapper;

import io.devfactory.account.domain.Resource;
import io.devfactory.account.dto.request.ResourceRequestView;
import io.devfactory.account.dto.response.ResourceResponseView;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ResourceMapper {

  public static final ResourceMapper INSTANCE = Mappers.getMapper(ResourceMapper.class);

  @Mapping(source = "view.roleId", target = "role.id")
  public abstract Resource toResource(ResourceRequestView view);

  @Mapping(source = "view.roleId", target = "role.id")
  public abstract Resource toResource(Long id, ResourceRequestView view);

  // RoleResponseView 객체로 내줘도 되나, 이런 형식으로 쓸 수 있음
  @Mapping(source = "resource.role.id", target = "roleId")
  @Mapping(source = "resource.role.name", target = "roleName")
  public abstract ResourceResponseView toResourceResponseView(Resource resource);

  public abstract List<ResourceResponseView> toResourceResponseViewList(List<Resource> resources);

}
