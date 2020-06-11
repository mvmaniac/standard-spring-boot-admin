package io.devfactory.global.config.security.factory;

import io.devfactory.global.config.security.service.ResourceMappingConfigService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;

@RequiredArgsConstructor
public class MethodResourcesMapFactoryBean implements
    FactoryBean<Map<String, List<ConfigAttribute>>>,
    InitializingBean {

  private final ResourceMappingConfigService resourceMappingConfigService;

  private Map<String, List<ConfigAttribute>> resourcesMap;
  private String resourceType;

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
  }

  @Override
  public Map<String, List<ConfigAttribute>> getObject() {
    return resourcesMap;
  }

  @Override
  public Class<?> getObjectType() {
    return Map.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  @Override
  public void afterPropertiesSet() {
    resourcesMap = resourceMappingConfigService.getResourcesByMethod(resourceType);
  }

}
