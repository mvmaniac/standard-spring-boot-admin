package io.devfactory.global.config.security;

import io.devfactory.global.config.security.factory.MethodResourcesFactoryBean;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import java.util.Objects;

// prePostEnabled 기본 값인 false로 해도 됨, 다른 곳에서 샘플로 쓴 곳이 있어서 그냥 둠...
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

  private final MethodResourcesFactoryBean methodResourcesFactoryBean;

  @Override
  protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
    return mapBasedMethodSecurityMetadataSource();
  }

  @Bean
  public MapBasedMethodSecurityMetadataSource mapBasedMethodSecurityMetadataSource() {
    return new MapBasedMethodSecurityMetadataSource(
        Objects.requireNonNull(methodResourcesFactoryBean.getObject()));
  }

}
