package io.devfactory.global.config.security.service;

import io.devfactory.account.domain.AccessIp;
import io.devfactory.account.domain.Resource;
import io.devfactory.account.domain.Role;
import io.devfactory.account.repository.AccessIpRepository;
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

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ResourceMappingConfigService {

  private final ResourceRepository resourceRepository;
  private final AccessIpRepository accessIpRepository;

  public Map<RequestMatcher, List<ConfigAttribute>> getResourcesByUrl() {
    Map<RequestMatcher, List<ConfigAttribute>> resourcesMap = new LinkedHashMap<>();

    final List<Resource> resources = resourceRepository.findResourcesByType("url");

    resources.forEach(resource -> {
      RequestMatcher requestMatcher = requestMatcherOf(resource);
      List<ConfigAttribute> configAttributes = configAttributeListOf(resource);
      resourcesMap.put(requestMatcher, configAttributes);
    });

    return resourcesMap;
  }

  public Map<String, List<ConfigAttribute>> getResourcesByMethod(String resourceType) {
    Map<String, List<ConfigAttribute>> resourcesMap = new LinkedHashMap<>();

    final List<Resource> resources = resourceRepository.findResourcesByType(resourceType);

    resources.forEach(resource -> {
      List<ConfigAttribute> configAttributes = configAttributeListOf(resource);
      resourcesMap.put(resource.getName(), configAttributes);
    });

    return resourcesMap;
  }

  public List<String> getAccessIpAddress() {
    return accessIpRepository.findAll().stream()
        .map(AccessIp::getIpAddress)
        .collect(toList())
      ;
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
