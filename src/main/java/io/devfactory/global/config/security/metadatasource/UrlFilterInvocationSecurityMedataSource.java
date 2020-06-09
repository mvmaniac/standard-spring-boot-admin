package io.devfactory.global.config.security.metadatasource;

import io.devfactory.global.config.security.service.ResourceMappingConfigService;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UrlFilterInvocationSecurityMedataSource implements
    FilterInvocationSecurityMetadataSource, InitializingBean {

  private final FactoryBean<Map<RequestMatcher, List<ConfigAttribute>>> urlResourcesMapFactoryBean;
  private final ResourceMappingConfigService resourceMappingConfigService;

  private Map<RequestMatcher, List<ConfigAttribute>> requestMap;

  @Override
  public Collection<ConfigAttribute> getAttributes(Object object) {
    final HttpServletRequest request = ((FilterInvocation) object).getRequest();

    if (Objects.nonNull(requestMap)) {
      for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()) {
        final RequestMatcher matcher = entry.getKey();
        if (matcher.matches(request)) {
          return entry.getValue();
        }
      }
    }

    return Collections.emptyList();
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    Set<ConfigAttribute> allAttributes = new HashSet<>();

    for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()) {
      allAttributes.addAll(entry.getValue());
    }

    return allAttributes;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return FilterInvocation.class.isAssignableFrom(clazz);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    requestMap = urlResourcesMapFactoryBean.getObject();
  }

  public void reload() {
    log.debug("[dev] UrlFilterInvocationSecurityMedataSource reload...");

    final Map<RequestMatcher, List<ConfigAttribute>> resources =
        resourceMappingConfigService.getResourcesByUrl();

    requestMap.clear();

    // putAll로 하면 안되는 건가?
    for (Entry<RequestMatcher, List<ConfigAttribute>> entry : resources.entrySet()) {
      requestMap.put(entry.getKey(), entry.getValue());
    }
  }

}
