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

  public abstract Resource toResource(ResourceRequestView view);

  public abstract Resource toResource(Long id, ResourceRequestView view);

  // RoleResponseView 객체로 내줘도 되나, 이런 형식으로 쓸 수 있음
  @Mapping(target = "roleId", expression = "java(resource.getRole().getId())")
  @Mapping(target = "roleName", expression = "java(resource.getRole().getName())")
  public abstract ResourceResponseView toResourceResponseView(Resource resource);

  public abstract List<ResourceResponseView> toResourceResponseViewList(List<Resource> resources);

}
