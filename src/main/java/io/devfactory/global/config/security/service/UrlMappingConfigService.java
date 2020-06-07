package io.devfactory.global.config.security.service;

import io.devfactory.account.domain.Resource;
import io.devfactory.account.domain.Role;
import io.devfactory.account.repository.ResourceRepository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UrlMappingConfigService {

  private final ResourceRepository resourceRepository;

  public Map<RequestMatcher, List<ConfigAttribute>> getResources() {
    Map<RequestMatcher, List<ConfigAttribute>> resourcesMap = new LinkedHashMap<>();

    final List<Resource> resources = resourceRepository.findResourcesByType("url");

    resources.forEach(resource -> {
      RequestMatcher requestMatcher = requestMatcherOf(resource);
      List<ConfigAttribute> configAttributes = configAttributeListOf(resource);
      resourcesMap.put(requestMatcher, configAttributes);
    });

    return resourcesMap;
  }

  private RequestMatcher requestMatcherOf(Resource resource) {
    return new AntPathRequestMatcher(resource.getName(), resource.getHttpMethod());
  }

  private List<ConfigAttribute> configAttributeListOf(Resource resource) {
    List<ConfigAttribute> configAttributes = new ArrayList<>();
    configAttributes.add(configAttributeOf(resource.getRole()));

    return configAttributes;
  }

  private ConfigAttribute configAttributeOf(Role role) {
    return new SecurityConfig(role.getName());
  }

}
