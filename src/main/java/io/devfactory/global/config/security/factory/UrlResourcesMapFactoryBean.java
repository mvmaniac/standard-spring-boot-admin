package io.devfactory.global.config.security.factory;

import io.devfactory.global.config.security.service.UrlMappingConfigService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UrlResourcesMapFactoryBean implements
    FactoryBean<Map<RequestMatcher, List<ConfigAttribute>>>, InitializingBean {

  private final UrlMappingConfigService urlMappingConfigService;

  private Map<RequestMatcher, List<ConfigAttribute>> resourcesMap;

  @Override
  public Map<RequestMatcher, List<ConfigAttribute>> getObject() {
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
    resourcesMap = urlMappingConfigService.getResources();
  }

}
